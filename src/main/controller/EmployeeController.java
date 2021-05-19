package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
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

    private SQLConnection dc;
    public boolean isBookingConfirmed = false;

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
    private Button delete_checkin_button;

    @FXML
    private Button signOutButton;

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
            Pane root = (Pane)loader.load(getClass().getResource("../ui/login.fxml").openStream());
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

    private void employeeBooking(String desk){
        UserHolder holder = UserHolder.getInstance();
        User user = holder.getUser();
        String username = user.getUsername();
        String password = user.getPassword();
        String dateString = date.getValue().toString();
        if(!loginModel.isBookingExist(dateString))
        {
            loginModel.isBooking(dateString,desk,username,password);
            bookingConfirmation();
        }
        else{ noMoreBooking();}
    }




}
