package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import main.utility.SQLConnection;
import main.model.LoginAppModel;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    private SQLConnection dc;
    public LoginAppModel loginModel = new LoginAppModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dc = new SQLConnection();
    }

    @FXML
    private Label newPwLabel;

    @FXML
    private Label newPassword;

    @FXML
    private TextField answer;

    @FXML
    private TextField username;

    @FXML
    private Button secretQuestionButton;

    //Display secret question action method
    @FXML
    public void displaySecretQuestion(ActionEvent event){
        secretQuestionButton.setText(loginModel.showSecretQuestion(username.getText()));
    }

    //Reset password action method
    @FXML
    public void ResetPassword(ActionEvent event){
        if(!username.getText().isEmpty()){
            if(loginModel.isResetPassword(username.getText(),answer.getText()) == true){
                newPwLabel.setTextFill(Color.BLACK);
                newPwLabel.setText("New Password");
                generateRandomPassword();
                updatePassword();
                clearTextField();
            }else{
                newPwLabel.setText("Answer to secret question is incorrect!");
            }
        }else{
            newPassword.setText("");
            newPwLabel.setTextFill(Color.RED);
            newPwLabel.setText("Please enter your username.");
        }
    }

    public void clearTextField(){
        username.setText("");
        answer.setText("");
        secretQuestionButton.setText("-Select-");
    }

    //Method to generate random password which is a string with length of 10
    public void generateRandomPassword(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String newRandomPassword = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        newPassword.setText(newRandomPassword);
    }

    //Update password helper method
    public void updatePassword(){
        loginModel.updatePassword(username.getText(), newPassword.getText());
    }



}
