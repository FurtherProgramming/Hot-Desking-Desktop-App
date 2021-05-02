package main.controller;
import com.oracle.tools.packager.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.EmployeeData;
import main.SQLConnection;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.Connection;

public class SignupController implements Initializable{


    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField secretQuestion;
    @FXML
    private TextField answer;

    private SQLConnection dc;

    public void initialize (URL url, ResourceBundle rb){
        this.dc = new SQLConnection();
    }

    @FXML
    private void Register(ActionEvent event){
        String sqlInsert = "INSERT INTO 'Employee'('id','firstname','lastname','username','password','secret_question','answer_to_secret_question','role') VALUES (?,?,?,?,?,?,?,'employee')";

        try{
            Connection conn = SQLConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            stmt.setString(1,this.id.getText());
            stmt.setString(2,this.firstname.getText());
            stmt.setString(3,this.lastname.getText());
            stmt.setString(4,this.username.getText());
            stmt.setString(5,this.password.getText());
            stmt.setString(6,this.secretQuestion.getText());
            stmt.setString(7,this.answer.getText());


            stmt.execute();
            conn.close();
            final Node source = (Node)event.getSource();
            final Stage stage = (Stage)source.getScene().getWindow();
            stage.close();
            LoginController loginController = new LoginController();
            loginController.employeeLogin();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    // Create EXIT BUTTON so user start again from opening the app, then use their just registered username to login


}
