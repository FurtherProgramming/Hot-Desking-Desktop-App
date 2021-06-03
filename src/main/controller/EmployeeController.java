package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.*;
import main.model.LoginAppModel;
import java.util.HashMap;

public class EmployeeController{

    private LoginAppModel loginModel = new LoginAppModel();
    private UserHolder holder = UserHolder.getInstance();
    private User user = holder.getUser();
    private String usernameString = user.getUsername();
    private String passwordString = user.getPassword();
    private String ID = loginModel.getEmployeeID(usernameString,passwordString);
    private ObservableList<EmployeeBookingHistory> employeeBookingHistory;

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

    @FXML
    private TextField bookingNumber;
    @FXML
    private Label errorMessage;

    @FXML
    private TableView<EmployeeBookingHistory> employeeBookingHistoryTableView;
    @FXML
    private TableColumn<EmployeeBookingHistory, String> bookingNumberCol;
    @FXML
    private TableColumn<EmployeeBookingHistory, String> bookingCreationDateCol;
    @FXML
    private TableColumn<EmployeeBookingHistory,String> bookingDateCol;
    @FXML
    private TableColumn<EmployeeBookingHistory,String> locationCol;
    @FXML
    private TableColumn<EmployeeBookingHistory,String> bookingStatusCol;

    //Book desk 1A action method if there is no booking for that desk on a chosen date
    @FXML
    private void bookTable1A(ActionEvent event) {
        String desk = desk1A.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        }
        else{ noMoreBooking();}
    }

    //Book desk 1B action method if there is no booking for that desk on a chosen date
    @FXML
    private void bookTable1B(ActionEvent event){
        String desk = desk1B.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }

    //Book desk 1C action method if there is no booking for that desk on a chosen date
    @FXML
    private void bookTable1C(ActionEvent event){
        String desk = desk1C.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }

    //Book desk 1D action method if there is no booking for that desk on a chosen date
    @FXML
    private void bookTable1D(ActionEvent event){
        String desk = desk1D.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }

    //Book desk 1E action method if there is no booking for that desk on a chosen date
    @FXML
    private void bookTable1E(ActionEvent event){
        String desk = desk1E.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }

    //Book desk 1F action method if there is no booking for that desk on a chosen date
    @FXML
    private void bookTable1F(ActionEvent event){
        String desk = desk1F.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }

    //visual representation of desks that can be booked by employee
    //employee can only select a valid date (i.e. future date only)
    @FXML
    private void chooseDate(ActionEvent event){
        HashMap<Button, Rectangle> desks = new HashMap<Button,Rectangle>();
        desks.put(desk1A, square_1A);
        desks.put(desk1B, square_1B);
        desks.put(desk1C, square_1C);
        desks.put(desk1D, square_1D);
        desks.put(desk1E, square_1E);
        desks.put(desk1F, square_1F);
        if(loginModel.isValidDate(date.getValue().toString())){
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

    //Display error message when invalid date is chosen
    private void invalidDate(){
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/InvalidDate.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(true);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Display message confirming the booking made
    private void bookingConfirmation(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/BookingConfirmation.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(true);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Display error message when trying to make an invalid booking
    private void noMoreBooking(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/NoMoreBooking.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(true);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Display error message when trying to make booking on the same desk as previously
    private void noSameDeskBooking(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/NoSameDeskBooking.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(true);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Cancel booking action method
    @FXML
    private void cancelBooking(ActionEvent event){

        String bookingNumber = this.bookingNumber.getText();
        if(loginModel.cancelBooking(bookingNumber)){
            errorMessage.setText("Booking has been canceled.");
            this.bookingNumber.setText("");
        }
        else{
            errorMessage.setTextFill(Color.RED);
            errorMessage.setText("You cannot cancel booking in less than 48hrs.");
            this.bookingNumber.setText("");
        }
    }

    //Check-in booking action method
    @FXML
    private void checkInBooking(ActionEvent event){

        String bookingNumber = this.bookingNumber.getText();
        if(loginModel.checkInBooking(bookingNumber)){
            errorMessage.setText("You have checked in.");
            this.bookingNumber.setText("");
        }else{
            errorMessage.setTextFill(Color.RED);
            errorMessage.setText("You cannot check-in. You can only check-in  when booking is approved by admin and on booking day.");
            this.bookingNumber.setText("");
        }
    }

    //Display employee's current details action method
    @FXML
    private void accountSetting(ActionEvent event){
        this.username.setPromptText(loginModel.getUsername(ID));
        this.password.setPromptText(loginModel.getPassword(ID));
        this.firstName.setPromptText(loginModel.getFirstname(ID));
        this.lastName.setPromptText(loginModel.getLastname(ID));
        this.secretQuestion.setPromptText(loginModel.getSecretQuestion(ID));
        this.answer.setPromptText(loginModel.getAnswerSecretQuestion(ID));
        updateConfirmation.setText("");
    }

    //Update employee's firstname action method
    @FXML
    private void updateFirstname(ActionEvent event){
        if(!firstName.getText().isEmpty()){
            loginModel.updateFirstName(ID, firstName.getText());
            firstName.setText("");
            updateConfirmation.setText("Firstname has been updated successfully.");
            clearPromptText();
        }
    }

    //Update employee's lastname action method
    @FXML
    private void updateLastname(ActionEvent event){
        if(!lastName.getText().isEmpty()){
            loginModel.updateLastname(ID, lastName.getText());
            lastName.setText("");
            updateConfirmation.setText("Lastname has been updated successfully.");
            clearPromptText();
        }
    }

    //Update employee's username action method
    @FXML
    private void updateUsername(ActionEvent event){
        if(!username.getText().isEmpty()){
            loginModel.updateUsername(ID, username.getText());
            username.setText("");
            updateConfirmation.setText("Username has been updated successfully.");
            clearPromptText();
        }
    }

    //Update employee's password action method
    @FXML
    private void editPassword(ActionEvent event){
        if(!password.getText().isEmpty()){
            loginModel.updatePasswordByID(ID, password.getText());
            password.setText("");
            updateConfirmation.setText("Password has been updated successfully.");
            clearPromptText();
        }
    }

    //Update employee's secret question action method
    @FXML
    private void updateSecretQuestion(ActionEvent event){
        if(!secretQuestion.getText().isEmpty()){
            loginModel.updateSecretQuestion(ID, secretQuestion.getText());
            secretQuestion.setText("");
            updateConfirmation.setText("Secret question is updated successfully.");
            clearPromptText();
        }
    }

    //Update employee's answer to secret question action method
    @FXML
    private void updateAnswer(ActionEvent event){
        if(!answer.getText().isEmpty()){
            loginModel.updateAnswer(ID, answer.getText());
            answer.setText("");
            updateConfirmation.setText("Answer to secret question has been updated successfully.");
            clearPromptText();
        }
    }

    //Display employee's booking history
    @FXML
    private void viewBookingHistory(ActionEvent event){
        clearErrorMessage();
        this.employeeBookingHistory = loginModel.displayEmployeeBookingHistory(ID);
        updateBookingTable();
    }

    //Display employee's pending booking
    @FXML
    private void viewPendingBooking(ActionEvent event){
        clearErrorMessage();
        this.employeeBookingHistory = loginModel.displayEmployeePendingBooking(ID);
        updateBookingTable();
    }

    //Display employee's approved booking
    @FXML
    private void viewApprovedBooking(ActionEvent event){
        clearErrorMessage();
        this.employeeBookingHistory = loginModel.displayEmployeeApprovedBooking(ID);
        updateBookingTable();
    }


    //Helper method to create booking
    //if there is no booking exist for the employee, booking is processed
    private void employeeBooking(String desk){
        if(!loginModel.isBookingExistForEmployee(usernameString, passwordString))
        {
            String dateString = this.date.getValue().toString();
            if(loginModel.isBooking(dateString,desk,usernameString,passwordString))
            {bookingConfirmation();}
            else{ noSameDeskBooking();}
        }
        else{ noMoreBooking();}
    }

    //Helper method to clear promp text in employee details
    private void clearPromptText(){
        this.username.setPromptText("");
        this.password.setPromptText("");
        this.firstName.setPromptText("");
        this.lastName.setPromptText("");
        this.secretQuestion.setPromptText("");
        this.answer.setPromptText("");
    }

    //Helper method to update employee's booking history table
    private void updateBookingTable(){
        this.bookingNumberCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingNumber"));
        this.bookingCreationDateCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingCreationDate"));
        this.bookingDateCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingDate"));
        this.locationCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("deskNumber"));
        this.bookingStatusCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingStatus"));
        this.employeeBookingHistoryTableView.setItems(null);
        this.employeeBookingHistoryTableView.setItems(this.employeeBookingHistory);
    }

    //Helper method to clear errorMessage
    private void clearErrorMessage(){
        errorMessage.setText("");
    }




}
