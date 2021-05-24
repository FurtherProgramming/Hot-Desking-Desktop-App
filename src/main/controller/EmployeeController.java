package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.SQLConnection;
import main.User;
import main.UserHolder;
import main.model.LoginAppModel;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


public class EmployeeController implements Initializable {

    public LoginAppModel loginModel = new LoginAppModel();

    private UserHolder holder = UserHolder.getInstance();
    private User user = holder.getUser();
    private String usernameString = user.getUsername();
    private String passwordString = user.getPassword();
    private String ID = loginModel.getEmployeeID(usernameString,passwordString);

    private SQLConnection dc;

    @FXML
    private DatePicker date;
    @FXML
    private Button desk1A;
    @FXML
    private Button desk1B;
    @FXML
    private Button desk1C;
    @FXML
    private Button desk1D;
    @FXML
    private Button desk1E;
    @FXML
    private Button desk1F;
    @FXML
    private Button refresh;
    @FXML
    private Rectangle square_1A;
    @FXML
    private Rectangle square_1B;
    @FXML
    private Rectangle square_1C;
    @FXML
    private Rectangle square_1D;
    @FXML
    private Rectangle square_1E;
    @FXML
    private Rectangle square_1F;

    @FXML
    private Button dateColumn;
    @FXML
    private Button locationColumn;
    @FXML
    private Button statusColumn;

    @FXML
    private Button cancel_checkin_button;

    @FXML
    private Button signOutButton;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField password;
    @FXML
    private TextField secretQuestion;
    @FXML
    private TextField answer;

    @FXML
    private TextField username;

    @FXML
    private Label updateConfirmation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dc = new SQLConnection();
    }



    @FXML
    private void bookTable1A(ActionEvent event) {
        employeeBooking(desk1A.getText());
    }
    @FXML
    private void bookTable1B(ActionEvent event){
        employeeBooking(desk1B.getText());
    }
    @FXML
    private void bookTable1C(ActionEvent event){
        employeeBooking(desk1C.getText());
    }
    @FXML
    private void bookTable1D(ActionEvent event){
        employeeBooking(desk1D.getText());
    }
    @FXML
    private void bookTable1E(ActionEvent event){
        employeeBooking(desk1E.getText());
    }
    @FXML
    private void bookTable1F(ActionEvent event){
        employeeBooking(desk1F.getText());
    }

    @FXML
    private void signOut(ActionEvent event) {
        try {
            Stage new_stage = (Stage) this.signOutButton.getScene().getWindow();
            new_stage.close();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/Login.fxml").openStream());
            Scene scene = new Scene (root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void chooseDate(ActionEvent event){
        if(loginModel.isValidDate(date.getValue().toString())){
            HashMap<Button, Rectangle> desks = new HashMap<Button,Rectangle>();
            desks.put(desk1A, square_1A);
            desks.put(desk1B, square_1B);
            desks.put(desk1C, square_1C);
            desks.put(desk1D, square_1D);
            desks.put(desk1E, square_1E);
            desks.put(desk1F, square_1F);
            for(Button desk : desks.keySet()){
                if(loginModel.displayAvailableTable(date.getValue().toString(), desk.getText())){
                    desks.get(desk).setFill(Color.RED);
                }else{
                    desks.get(desk).setFill(Color.GREEN);
                }
            }
        }else {
            invalidDate();
        }

    }



    public void bookingConfirmation(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/BookingConfirmation.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void noMoreBooking(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/NoMoreBooking.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void noSameDeskBooking(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/NoSameDeskBooking.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void invalidDate(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/InvalidDate.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    private void employeeBooking(String desk){
        String dateString = date.getValue().toString();
        if(!loginModel.isBookingExist(usernameString, passwordString))
        {
            if(loginModel.isBooking(dateString,desk,usernameString,passwordString)){bookingConfirmation();}
            else{ noSameDeskBooking();}
        }
        else{ noMoreBooking();}
    }

    @FXML
    private void manageBooking(ActionEvent event){
        String [] result = loginModel.displayCurrentBooking(usernameString, passwordString);
        dateColumn.setText(result[0]);
        locationColumn.setText(result[1]);
        statusColumn.setText(result[2]);
        if(loginModel.isBookingApproved(usernameString, passwordString)){
            cancel_checkin_button.setText("Check-in");
        }
    }

    @FXML
    private void cancelOrCheckIn(ActionEvent event){
            loginModel.updateBookingStatus(statusColumn.getText(),cancel_checkin_button.getText(),usernameString, passwordString);
    }

    @FXML
    private void accountSetting(ActionEvent event){
        this.username.setPromptText(usernameString);
        this.password.setPromptText(passwordString);
        String[] result = loginModel.currentAccountDetails(usernameString,passwordString);
        this.firstName.setPromptText(result[0]);
        this.lastName.setPromptText(result[1]);
        this.secretQuestion.setPromptText(result[2]);
        this.answer.setPromptText(result[3]);
    }

    @FXML
    private void updateFirstname(ActionEvent event){
        if(!firstName.getText().isEmpty()){
            loginModel.updateFirstName(ID, firstName.getText());
            updateConfirmation.setText(firstName.getText() +" is updated successfully.");
        }
    }

    @FXML
    private void updateLastname(ActionEvent event){
        if(!lastName.getText().isEmpty()){
            loginModel.updateLastname(ID, lastName.getText());
            updateConfirmation.setText(lastName.getText() + " is updated successfully.");
        }
    }
    @FXML
    private void updateUsername(ActionEvent event){
        if(!username.getText().isEmpty()){
            loginModel.updateUsername(ID, username.getText());
            updateConfirmation.setText(username.getText() +" is updated successfully.");
        }
    }
    @FXML
    private void editPassword(ActionEvent event){
        if(!password.getText().isEmpty()){
            loginModel.updatePasswordByID(ID, password.getText());
            updateConfirmation.setText(password.getText() + " is updated successfully.");
        }
    }
    @FXML
    private void updateSecretQuestion(ActionEvent event){
        if(!secretQuestion.getText().isEmpty()){
            loginModel.updateSecretQuestion(ID, secretQuestion.getText());
            updateConfirmation.setText(secretQuestion.getText() + " is updated successfully.");
        }
    }
    @FXML
    private void updateAnswer(ActionEvent event){
        if(!answer.getText().isEmpty()){
            loginModel.updateAnswer(ID, answer.getText());
            updateConfirmation.setText(answer.getText() + " is updated successfully.");
        }
    }


}
