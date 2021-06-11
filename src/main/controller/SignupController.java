package main.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
    @FXML
    private Label errorMessage;

    private SQLConnection dc;

    public void initialize (URL url, ResourceBundle rb){
        this.dc = new SQLConnection();
    }

    public LoginAppModel loginModel = new LoginAppModel();

    /*Register as new employee action method
    By default, only employee role will be created
    Admin can only be created by an existing admin*/
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
        if(id.isEmpty() || fname.isEmpty() || lname.isEmpty() || uname.isEmpty()
                || pass.isEmpty() || sq.isEmpty() || asq.isEmpty()){
            errorMessage.setTextFill(Color.RED);
            errorMessage.setText("Please enter all the fields.");
        }else{
            if(loginModel.isAddEmployee(id,fname,lname,uname,pass,sq,asq,role)){
                errorMessage.setTextFill(Color.GREEN);
                errorMessage.setText("You have been added. \nPlease exit and login to use the app.");
            }
            else{
                errorMessage.setTextFill(Color.RED);
                errorMessage.setText("Please enter a valid employee ID.");
            }
        }

    }

}
