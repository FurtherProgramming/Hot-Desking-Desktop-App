package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.BookingData;
import main.EmployeeBookingHistory;
import main.EmployeeData;
import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoginAppModel {

    Connection connection;
    private ObservableList<EmployeeData> employeeData;
    private ObservableList<BookingData> bookingData;
    private ObservableList<EmployeeBookingHistory> employeeBookingHistory;

    private String approvedStatus = "Approved";
    private String pendingStatus = "Pending";
    private String rejectStatus = "Rejected";
    private String lockedStatus = "Locked";
    private String cancelStatus = "Cancel";
    private String checkinStatus = "Check-in";

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

    //Check if user is admin or not from employee table
    public Boolean isAdminLogin(String user, String pass){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where role = 'admin' and username = ? and password = ?";
        try {

            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                return true;
            }
            else{
                preparedStatement.close();
                resultSet.close();
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }

    //Check if user is employee or not from employee table
    public Boolean isEmployeeLogin(String user, String pass){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where role = 'employee' and username = ? and password = ?";
        try {

            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                return true;
            }
            else{
                preparedStatement.close();
                resultSet.close();
                return false;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }

    //return true if an employee has been added to employee table successfully
    public Boolean isAddEmployee(String id, String firstname, String lastname, String username, String password, String secret_question, String answer_to_secret_question, String role){
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


    //return employee id from given username and password
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

    //return firstname from its id
    public String getFirstname(String id){
        String sql = "Select firstname from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("firstname");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //return lastname from its id
    public String getLastname(String id){
        String sql = "Select lastname from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("lastname");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //return secret question from its id
    public String getSecretQuestion(String id){
        String sql = "Select secret_question from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("secret_question");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //return answer to secret question from its id
    public String getAnswerSecretQuestion(String id){
        String sql = "Select answer_to_secret_question from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("answer_to_secret_question");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //return username  from its id
    public String getUsername(String id){
        String sql = "Select username from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("username");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //return password from its id
    public String getPassword(String id){
        String sql = "Select password from Employee where id = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("password");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }




    //check if booking_date is a valid date (not a past date), true if booking date is after today (no same day booking)
    public Boolean isValidDate(String booking_date){
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bookingDate = LocalDate.parse(booking_date,formatter);
        if(bookingDate.isAfter(todayDate)){
            return  true;
        }
        else {
            return false;
        }
    }

    //insert new booking to booking table, return true when insertion succeed
    public  Boolean isBooking(String booking_date, String desk_number, String username, String password){
        String sql = "INSERT INTO Booking VALUES(NULL, ?,datetime('now','localtime'),?,?,?)";
        PreparedStatement preparedStatement = null;
        try{
            if(checkPrevDeskBeforeBooking(desk_number,username,password)){
                preparedStatement = this.connection.prepareStatement(sql);
                preparedStatement.setString(1, getEmployeeID(username,password));
                preparedStatement.setString(2, booking_date);
                preparedStatement.setString(3, desk_number);
                preparedStatement.setString(4, pendingStatus);

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

    //insert a booking with lockdown status to booking table, return true when insertion succeed
    public  Boolean adminLockdownTables(String booking_date, String desk_number, String username, String password){
        String sql = "INSERT INTO Booking VALUES(NULL, ?,datetime('now','localtime'),?,?,?)";
        PreparedStatement preparedStatement = null;
        try{
                preparedStatement = this.connection.prepareStatement(sql);
                preparedStatement.setString(1, getEmployeeID(username,password));
                preparedStatement.setString(2, booking_date);
                preparedStatement.setString(3, desk_number);
                preparedStatement.setString(4, lockedStatus);

                int rowsUpdated = preparedStatement.executeUpdate();
                if( rowsUpdated > 0 ){
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

    //check prevDesk before booking
    public boolean checkPrevDeskBeforeBooking(String desk, String username, String password){
        String prevDesk = previousDesk(username, password);
        if(!prevDesk.equals(desk)){
            return true;
        }else {
            return false;
        }
    }

    //check if booking exist or not for a given employee,
    // if booking status is pending or approved, return true,
    // user cant make new booking
    public  Boolean isBookingExistForEmployee( String username, String password ){
        String sql = "Select * from Booking where (booking_status = ? or booking_status = ?) and employee_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pendingStatus);
            preparedStatement.setString(2, approvedStatus);
            preparedStatement.setString(3, getEmployeeID(username, password));
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

    //check if booking exist or not for a given date and deskNumber,
    // if booking status is pending or locked on that date, return true,
    // user cant make new booking
    public  Boolean isBookingExist(String bookingDate, String deskNumber ){
        String sql = "Select * from Booking where (booking_status = ?  or booking_status = ? )and booking_date = ? and desk_number = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pendingStatus);
            preparedStatement.setString(2, lockedStatus);
            preparedStatement.setString(3, bookingDate);
            preparedStatement.setString(4, deskNumber);
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

    //check if the answer to the secret question is correct or not, return true if it is correct
    public Boolean isResetPassword(String username, String answer){
        String query = "select * from employee where username = ? and answer_to_secret_question = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, answer);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                preparedStatement.close();
                resultSet.close();
                return true;
            }else {
                preparedStatement.close();
                resultSet.close();
                return false;
            }
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

    //return true if booking status is pending/approved/locked/check-in
    public Boolean displayAvailableTable(String bookingDate, String desk_number){
        String sql = "Select * from Booking where (booking_status = ? or booking_status = ? or  booking_status = ? or  booking_status = ? ) and booking_date = ? and desk_number = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pendingStatus);
            preparedStatement.setString(2, approvedStatus);
            preparedStatement.setString(3, lockedStatus);
            preparedStatement.setString(4, checkinStatus);
            preparedStatement.setString(5, bookingDate);
            preparedStatement.setString(6, desk_number);
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

    //return employee table read only
    public ObservableList<EmployeeData> displayLoadEmployeeData(){
        String sql = "SELECT * from Employee";
        try{
            this.employeeData = FXCollections.observableArrayList();
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while(rs.next()){
                this.employeeData.add(new EmployeeData(rs.getString(1),
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
        return employeeData;
    }

    //return booking table read only
    public ObservableList<BookingData> displayBookingData(){
        String sql = "SELECT * from Booking";
        try{
            this.bookingData = FXCollections.observableArrayList();
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while(rs.next()){
                this.bookingData.add(new BookingData(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }
        return bookingData;
    }

    //return booking table to employee, read only
    public ObservableList<EmployeeBookingHistory> displayEmployeeBookingHistory(String employeeId){
        String sql = "SELECT booking_number, timestamp, booking_date, desk_number,booking_status from Booking where employee_id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            this.employeeBookingHistory = FXCollections.observableArrayList();
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                this.employeeBookingHistory.add(new EmployeeBookingHistory(rs.getString("booking_number"),
                        rs.getString("timestamp"),
                        rs.getString("booking_date"),
                        rs.getString("desk_number"),
                        rs.getString("booking_status")));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }
        return employeeBookingHistory;
    }

    //display employee's pending Booking
    public ObservableList<EmployeeBookingHistory> displayEmployeePendingBooking(String employeeId){
        String sql = "SELECT booking_number, timestamp , booking_date, desk_number,booking_status from Booking where employee_id = ? and booking_status = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            this.employeeBookingHistory = FXCollections.observableArrayList();
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);
            preparedStatement.setString(2, pendingStatus);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                this.employeeBookingHistory.add(new EmployeeBookingHistory(rs.getString("booking_number"),
                        rs.getString("timestamp"),
                        rs.getString("booking_date"),
                        rs.getString("desk_number"),
                        rs.getString("booking_status")));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }
        return employeeBookingHistory;
    }

    //display employee's Approved Booking
    public ObservableList<EmployeeBookingHistory> displayEmployeeApprovedBooking(String employeeId){
        String sql = "SELECT booking_number, timestamp , desk_number,booking_status from Booking where employee_id = ? and booking_status = ?";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            this.employeeBookingHistory = FXCollections.observableArrayList();
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, employeeId);
            preparedStatement.setString(2, approvedStatus);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                this.employeeBookingHistory.add(new EmployeeBookingHistory(rs.getString("booking_number"),
                        rs.getString("timestamp"),
                        rs.getString("booking_date"),
                        rs.getString("desk_number"),
                        rs.getString("booking_status")));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }
        return employeeBookingHistory;
    }

    //display all pending Booking to admin
    public ObservableList<BookingData> displayPendingBookingData(){
        String sql = "SELECT * from Booking WHERE booking_status = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try{
            this.bookingData = FXCollections.observableArrayList();
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, pendingStatus);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                this.bookingData.add(new BookingData(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }
        return bookingData;
    }

    //update new firstname to employee table
    public void updateFirstName(String id, String firstname){
        String sqlInsert = "UPDATE Employee SET firstname = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new lastname to employee table
    public void updateLastname(String id, String lastname){
        String sqlInsert = "UPDATE Employee SET lastname = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, id);
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
    public void updateSecretQuestion(String id, String secretQuestion){
        String sqlInsert = "UPDATE Employee SET secret_question = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, secretQuestion);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new answer to secret question to employee table
    public void updateAnswer(String id, String answer){
        String sqlInsert = "UPDATE Employee SET answer_to_secret_question = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, answer);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update new role to employee table
    public void updateRole(String id, String role){
        String sqlInsert = "UPDATE Employee SET role = ?"
                + "WHERE id = ?  ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //return true if an ID exist in employee table
    public Boolean isIDexist(String id){
        String sql = "Select id from Employee where id = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
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

    //Delete employee from employee table from its ID
    public void deleteEmployee(String id){
        String sql = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //return most recent previous desk number
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


    //cancel booking in Booking table by employee
    public Boolean cancelBooking(String bookingNumber){
        String booking_date = getBookingDate(bookingNumber);

        String sqlInsert = "UPDATE Booking SET booking_status = ?"
                + "WHERE (booking_number = ? and booking_date >= date('now','+2 days'))";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        boolean result = false;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, cancelStatus);
            preparedStatement.setString(2, bookingNumber);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0){
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    result =  true;
            }
                //else return false, cannot cancel booking
            else {
                    preparedStatement.close();
                    result = false;
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //return booking date for a given booking number
    public String getBookingDate(String bookingNumber){
        String sql = "Select booking_date from Booking where booking_number = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingNumber);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("booking_date");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //checkIn booking in Booking table by employee
    //employee is only be able to check in on the booking date
    public Boolean checkInBooking(String bookingNumber){
        String bookingStatus = getBookingStatus(bookingNumber);
        String sqlInsert = "UPDATE Booking SET booking_status = ?"
                + "WHERE (booking_number = ? and booking_date = date('now'))";
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try{
            preparedStatement = this.connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, checkinStatus);
            preparedStatement.setString(2, bookingNumber);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0){
                preparedStatement.close();
                result =  true;
            }
            //else return false, cannot checkIn booking
            else {
                preparedStatement.close();
                result = false;
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //return booking status for a given booking number
    public String getBookingStatus(String bookingNumber){
        String sql = "Select booking_status from Booking where booking_number = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingNumber);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next() ){
                result =  resultSet.getString("booking_status");
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //approve booking status by admin (only able to approve pending booking)
    public Boolean approveBooking(String bookingNumber){
        String sqlUpdate = "UPDATE Booking SET booking_status = ?"
                + "WHERE booking_number = ?  and booking_status = ?";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, approvedStatus);
            preparedStatement.setString(2, bookingNumber);
            preparedStatement.setString(3, pendingStatus);
            int rowUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            if(rowUpdated > 0){
                return true;
            }else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //reject booking status by admin (only able to reject pending booking)
    public Boolean rejectBooking(String bookingNumber){
        String sqlUpdate = "UPDATE Booking SET booking_status = ?"
                + "WHERE booking_number = ?  and booking_status = ? ";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = this.connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, rejectStatus);
            preparedStatement.setString(2, bookingNumber);
            preparedStatement.setString(3, pendingStatus);
            int rowUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            if(rowUpdated > 0){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //export csv of employee table
    public void exportEmployeeTable(){
        String sql = "Select * from Employee";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String csvFilePath = "EmployeeReport.csv";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            // write header line containing column names
            fileWriter.write("id,firstname,lastname,username,password,secret_question,answer_to_secret_question,role");
            while(resultSet.next() ){
                String Employee_id = resultSet.getString("id");
                String Firstname = resultSet.getString("firstname");
                String Lastname = resultSet.getString("lastname");
                String Username = resultSet.getString("username");
                String Password = resultSet.getString("password");
                String Secret_question = resultSet.getString("secret_question");
                String Answer = resultSet.getString("answer_to_secret_question");
                String Role = resultSet.getString("role");
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                                            Employee_id, Firstname,Lastname,Username,
                                            Password,Secret_question,Answer,Role);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            preparedStatement.close();
            resultSet.close();
            fileWriter.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //export csv of booking table
    public void exportBookingTable(){
        String sql = "Select * from Booking";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String csvFilePath = "BookingReport.csv";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            // write header line containing column names
            fileWriter.write("booking_number,employee_id,timestamp,booking_date,desk_number,booking_status");
            while(resultSet.next() ){
                String BookingNumber = resultSet.getString("booking_number");
                String Employee_id = resultSet.getString("employee_id");
                String Timestamp = resultSet.getString("timestamp");
                String BookingDate = resultSet.getString("booking_date");
                String DeskNumber = resultSet.getString("desk_number");
                String BookingStatus = resultSet.getString("booking_status");
                String line = String.format("%s,%s,%s,%s,%s,%s", BookingNumber, Employee_id,Timestamp,BookingDate, DeskNumber,BookingStatus);
                fileWriter.newLine();
                fileWriter.write(line);
            }
            preparedStatement.close();
            resultSet.close();
            fileWriter.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    //export csv of booking table for a given date
    public Boolean exportBookingFromDate(String bookingDate){
        String sql = "Select * from Booking where booking_date = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String csvFilePath = "BookingReportForSingleDate.csv";
        try{
            preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingDate);
            resultSet = preparedStatement.executeQuery();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            // write header line containing column names
            fileWriter.write("booking_number,employee_id,timestamp,booking_date,desk_number,booking_status");
            if(resultSet.next()){
                while(resultSet.next() ){
                    String BookingNumber = resultSet.getString("booking_number");
                    String Employee_id = resultSet.getString("employee_id");
                    String Timestamp = resultSet.getString("timestamp");
                    String BookingDate = resultSet.getString("booking_date");
                    String DeskNumber = resultSet.getString("desk_number");
                    String BookingStatus = resultSet.getString("booking_status");
                    String line = String.format("%s,%s,%s,%s,%s,%s", BookingNumber, Employee_id,Timestamp,BookingDate, DeskNumber,BookingStatus);
                    fileWriter.newLine();
                    fileWriter.write(line);
                }
                preparedStatement.close();
                resultSet.close();
                fileWriter.close();
                return true;
            }
            else{
                preparedStatement.close();
                resultSet.close();
                return false;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Automatic booking cancellation when not approved on time
    public Boolean AutomaticCancelBooking(){
        String sqlUpdate = "UPDATE Booking SET booking_status = ?"
                + "WHERE (booking_date <= date('now','localtime'))";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        boolean result = false;
        try{
            preparedStatement = this.connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, cancelStatus);
            int rowsUpdated = preparedStatement.executeUpdate();
            if(rowsUpdated > 0){
                preparedStatement.executeUpdate();
                preparedStatement.close();
                result =  true;
            }
            //else return false, cannot cancel booking
            else {
                preparedStatement.close();
                result = false;
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





}
