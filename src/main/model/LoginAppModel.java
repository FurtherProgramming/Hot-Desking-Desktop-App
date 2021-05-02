package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.EmployeeData;
import main.SQLConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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
        Connection c = null;
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            c = SQLConnection.connect();
            st = c.prepareStatement(sqlInsert);
            st.setString(1, id);
            st.setString(2, firstname);
            st.setString(3, lastname);
            st.setString(4, username);
            st.setString(5, password);
            st.setString(6, secret_question);
            st.setString(7, answer_to_secret_question);
            st.setString(8, role);
            st.execute();
            c.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public  Boolean isEmployeeBooking(String username, String password, String booking_date, String booking_status, String desk_number){
        String sqlInsert = "UPDATE Employee SET desk_number = ? ,"
                            + "booking_date = ? , "
                            + "booking_status = ?"
                            + "WHERE username = ?"
                            + "AND password = ?";
        Connection c = null;
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            c = SQLConnection.connect();
            st = c.prepareStatement(sqlInsert);
            st.setString(1, desk_number);
            st.setString(2, booking_date);
            st.setString(3, booking_status);
            st.setString(4, username);
            st.setString(5, password);
            st.execute();
            c.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
