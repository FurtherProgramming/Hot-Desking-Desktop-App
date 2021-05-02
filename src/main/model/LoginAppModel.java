package main.model;

import javafx.event.ActionEvent;
import main.SQLConnection;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginAppModel {
    Connection connection;

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


}
