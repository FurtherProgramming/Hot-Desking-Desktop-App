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

    public  Boolean isBooking(String booking_date, String desk_number, String username, String password){
        String sql = "INSERT INTO Booking VALUES(NULL, ?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username,password));
            LocalDateTime timestamp = LocalDateTime.now();
            preparedStatement.setString(2, timestamp.toString());
            preparedStatement.setString(3, booking_date);
            preparedStatement.setString(4, desk_number);
            preparedStatement.setString(5, "pending");

            int rowsUpdated = preparedStatement.executeUpdate();
            if( rowsUpdated > 0){
                preparedStatement.close();
                return true;
            } else {
                preparedStatement.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public  Boolean isBookingExist(String booking_date){
        String sql = "Select * from Booking where booking_status = 'pending' and booking_date = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, booking_date);
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

    public  Boolean isBookingApproved(String username, String password){
        String sql = "Select * from Booking where booking_status = 'approved' and employee_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, getEmployeeID(username,password));
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

    public Boolean displayAvailableTable(String bookingDate, String desk_number){

        String sql = "Select * from Booking where (booking_status = 'pending' or booking_status = 'approved') and booking_date = ? and desk_number = ? ";
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




}
