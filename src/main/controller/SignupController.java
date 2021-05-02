package main.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.SQLConnection;
import javafx.event.ActionEvent;
import main.model.LoginAppModel;

import java.net.URL;
import java.util.ResourceBundle;

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

    public LoginAppModel loginModel = new LoginAppModel();

    @FXML
    private void Register(ActionEvent event) throws Exception{
        String id = this.id.getText();
        String fname = this.firstname.getText();
        String lname = this.lastname.getText();
        String uname = this.username.getText();
        String pass = this.password.getText();
        String sq = this.secretQuestion.getText();
        String asq = this.answer.getText();
        String role = "employee";
        loginModel.isAddEmployee(id,fname,lname,uname,pass,sq,asq,role);
    }



    // Create EXIT BUTTON so user start again from opening the app, then use their just registered username to login


}
