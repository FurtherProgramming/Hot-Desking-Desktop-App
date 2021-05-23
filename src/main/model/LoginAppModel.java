package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.EmployeeData;
import main.SQLConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class LoginAppModel {

    Connection connection;
    private ObservableList<EmployeeData> data;


    public LoginAppModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }


    public Boolean isAdminLogin(String user, String pass) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where role = 'admin' and username = ? and password = ?";
        try {

            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }


    public Boolean isEmployeeLogin(String user, String pass) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where role = 'employee' and username = ? and password = ?";
        try {

            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }


    public Boolean isAddEmployee(String id, String firstname, String lastname, String username, String password, String secret_question, String answer_to_secret_question, String role) throws Exception{
        String sqlInsert = "INSERT INTO Employee(id,firstname,lastname,username,password,secret_question,answer_to_secret_question,role) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            st = this.connection.prepareStatement(sqlInsert);
            st.setString(1, id);
            st.setString(2, firstname);
            st.setString(3, lastname);
            st.setString(4, username);
            st.setString(5, password);
            st.setString(6, secret_question);
            st.setString(7, answer_to_secret_question);
            st.setString(8, role);
            st.execute();
            st.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //return employee id from its username and password
    public String getEmployeeID(String username, String password){
        String sql = "Select id from Employee where username = ? and password = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("id");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //insert new booking to booking table
    public  Boolean isBooking(String booking_date, String desk_number, String username, String password){
        String sql = "INSERT INTO Booking VALUES(NULL, ?,?,?,?,?)";
        System.out.println(previousDesk(username, password));
        PreparedStatement preparedStatement = null;
        try{
            if(checkPrevDeskBeforeBooking(desk_number,username,password)){
                preparedStatement = this.connection.prepareStatement(sql);
                preparedStatement.setString(1, getEmployeeID(username,password));
                LocalDateTime timestamp = LocalDateTime.now();
                preparedStatement.setString(2, timestamp.toString());
                preparedStatement.setString(3, booking_date);
                preparedStatement.setString(4, desk_number);
                preparedStatement.setString(5, "Pending");

                int rowsUpdated = preparedStatement.executeUpdate();
                if( rowsUpdated > 0 ){
                    preparedStatement.close();
                    return true;
                } else {
                    preparedStatement.close();
                    return false;
                }
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //check prevDesk before booking
    public boolean checkPrevDeskBeforeBooking(String desk, String username, String password){
        String prevDesk = previousDesk(username, password);
        if(!prevDesk.equals(desk)){
            return true;
        }else {
            return false;
        }
    }

    //check if booking exist or not,
    // if booking status is pending, return true,
    // user cant make new booking
    public  Boolean isBookingExist( String username, String password ){
        String sql = "Select * from Booking where booking_status = 'Pending' and employee_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username, password));
            resultSet  = preparedStatement.executeQuery();
            if( resultSet.next()){
                preparedStatement.close();
                resultSet.close();
                return true;
            } else {
                preparedStatement.close();
                resultSet.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    //check if the answer to the secret question is correct or not
    public Boolean isResetPassword(String username, String answer){
        String query = "select * from employee where username = ? and answer_to_secret_question = ?";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(1, answer);
            preparedStatement.execute();

            if(preparedStatement.execute() == false){
                return false;
            }
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //return secret question from employee table
    public String showSecretQuestion(String username){
        String query = "select secret_question from employee where username = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result =  resultSet.getString("secret_question");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
           e.printStackTrace();
        }
        return result;
    }

    //update new random password to employee table
    public void updatePassword(String username, String newPassword){
        String sqlInsert = "UPDATE Employee SET password = ?"
                + "WHERE username = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //if booking status is pending or approved, return true
    public Boolean displayAvailableTable(String bookingDate, String desk_number){
        String sql = "Select * from Booking where (booking_status = 'Pending' or booking_status = 'Approved') and booking_date = ? and desk_number = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingDate);
            preparedStatement.setString(2, desk_number);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                preparedStatement.close();
                resultSet.close();
               return true;
            }
            else {
                preparedStatement.close();
                resultSet.close();
                return false;
            }

        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public ObservableList<EmployeeData> displayLoadEmployeeData(){
        String sql = "SELECT * from Employee";
        try{
            this.data = FXCollections.observableArrayList();
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while(rs.next()){
                this.data.add(new EmployeeData(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }
        return data;
    }

    public String[] displayCurrentBooking(String username, String password){
        String sql = "Select booking_date, desk_number, booking_status from Booking where employee_id = ? and "
                    + "booking_date = (Select MAX(booking_date) FROM Booking where employee_id = ? )";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String[] result = new String[3];
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username,password));
            preparedStatement.setString(2, getEmployeeID(username,password));
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result[0] =  resultSet.getString("booking_date");
                result[1] =  resultSet.getString("desk_number");
                result[2] =  resultSet.getString("booking_status");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // if booking status is approved, return true, which means employee can check in
    public  Boolean isBookingApproved(String username, String password){
        String sql = "Select * from Booking where booking_status = 'Approved' and employee_id = ? and "
                    + "booking_date = (Select MAX(booking_date) FROM Booking  where employee_id = ?)";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username,password));
            preparedStatement.setString(2, getEmployeeID(username,password));
            resultSet  = preparedStatement.executeQuery();
            if( resultSet.next() ){
                preparedStatement.close();
                resultSet.close();
                return true;
            } else {
                preparedStatement.close();
                resultSet.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] currentAccountDetails(String username, String password){
        String sql = "Select firstname, lastname, secret_question, answer_to_secret_question from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String[] result = new String[6];
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username,password));
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result[0] =  resultSet.getString("firstname");
                result[1] =  resultSet.getString("lastname");
                result[2] =  resultSet.getString("secret_question");
                result[3] =  resultSet.getString("answer_to_secret_question");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //update new firstname to employee table
    public void updateFirstName(String username, String password, String firstname){
        String sqlInsert = "UPDATE Employee SET firstname = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, getEmployeeID(username,password));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new lastname to employee table
    public void updateLastname(String username, String password, String lastname){
        String sqlInsert = "UPDATE Employee SET lastname = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, getEmployeeID(username,password));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new username to employee table
    public void updateUsername(String id, String newUsername){
        String sqlInsert = "UPDATE Employee SET username = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new password to employee table
    public void updatePasswordByID(String id, String newPassword){
        String sqlInsert = "UPDATE Employee SET password = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new secret question to employee table
    public void updateSecretQuestion(String username, String password, String secretQuestion){
        String sqlInsert = "UPDATE Employee SET secret_question = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, secretQuestion);
            preparedStatement.setString(2, getEmployeeID(username,password));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new answer to secret question to employee table
    public void updateAnswer(String username, String password, String answer){
        String sqlInsert = "UPDATE Employee SET answer_to_secret_question = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, answer);
            preparedStatement.setString(2, getEmployeeID(username,password));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //check previous desk number
    public String previousDesk(String username, String password){
        String result = "...";
        String sql = "Select desk_number from Booking where employee_id = ? and "
                + "booking_date = (Select MAX(booking_date) FROM Booking  where employee_id = ?)";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username,password));
            preparedStatement.setString(2, getEmployeeID(username,password));
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result =  resultSet.getString("desk_number");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //update booking status in Booking table when employee check in or cancel
    public void updateBookingStatus(String oldBookingStatus, String newBookingStatus, String username, String password){
        String sqlInsert = "UPDATE Booking SET booking_status = ?"
                + "WHERE booking_status = ?  and employee_id = ?";
        PreparedStatement preparedStatement = null;
        String id = getEmployeeID(username,password);
        try{

            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, newBookingStatus);
            preparedStatement.setString(2, oldBookingStatus);
            preparedStatement.setString(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
