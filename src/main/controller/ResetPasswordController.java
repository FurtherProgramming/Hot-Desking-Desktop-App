package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.SQLConnection;
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


    @FXML
    public void displaySecretQuestion(ActionEvent event){
        secretQuestionButton.setText(loginModel.showSecretQuestion(username.getText()));
    }

    @FXML
    public void ResetPassword(ActionEvent event){
        if(loginModel.isResetPassword(username.getText(),answer.getText()) == true){
            newPwLabel.setText("New Password");
            generateRandomPassword();
            updatePassword();
        }else{
            newPwLabel.setText("Answer to secret question is incorrect!");
        }
    }

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

    public void updatePassword(){
        loginModel.updatePassword(username.getText(), newPassword.getText());
    }



}
