package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.SQLConnection;
import main.User;
import main.UserHolder;
import main.model.LoginAppModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    public LoginAppModel loginModel = new LoginAppModel();

    private SQLConnection dc;
    public boolean isBookingConfirmed = false;

    @FXML
    private DatePicker date;
    @FXML
    private Button desk1A;

    @FXML
    private Rectangle square_1A;

    @FXML
    private Button delete_checkin_button;

    @FXML
    private Button signOutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dc = new SQLConnection();
    }

    private static int bookingCounter = 0;

    @FXML
    private void bookTable1A(ActionEvent event) {
        booking();
    }
    @FXML
    private void bookTable1B(ActionEvent event){
        booking();

    }
    @FXML
    private void bookTable1C(ActionEvent event){
        booking();
    }
    @FXML
    private void bookTable1D(ActionEvent event){
        booking();
    }
    @FXML
    private void bookTable1E(ActionEvent event){
        booking();
    }
    @FXML
    private void bookTable1F(ActionEvent event){
        booking();
    }

    @FXML
    private void signOut(ActionEvent event) {
        try {
            Stage new_stage = (Stage) this.signOutButton.getScene().getWindow();
            new_stage.close();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/login.fxml").openStream());
            Scene scene = new Scene (root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void booking(){
        if (bookingCounter == 0){
            employeeBooking();
            bookingCounter++;
        }
        else{
            noMoreBooking();
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

    public void bookingConfirmed(){
        isBookingConfirmed = true;
        delete_checkin_button.setText("Check-in");
    }

    @FXML
    private void employeeBooking(){
        UserHolder holder = UserHolder.getInstance();
        User user = holder.getUser();
        String username = user.getUsername();
        String password = user.getPassword();
        String dateString = date.getValue().toString();
        String desk = desk1A.getText();
        String status = "pending";
        if(loginModel.isEmployeeBooking(username, password, dateString, status, desk) == true){
            bookingConfirmation();
        }
        else{ noMoreBooking();}
    }




}
