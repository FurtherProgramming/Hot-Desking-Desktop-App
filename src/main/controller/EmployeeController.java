package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.*;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dc = new SQLConnection();
    }


    @FXML
    private void bookTable1A(ActionEvent event) {
        String desk = desk1A.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        }
        else{ noMoreBooking();}
    }
    @FXML
    private void bookTable1B(ActionEvent event){
        String desk = desk1B.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }
    @FXML
    private void bookTable1C(ActionEvent event){
        String desk = desk1C.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }
    @FXML
    private void bookTable1D(ActionEvent event){
        String desk = desk1D.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }
    @FXML
    private void bookTable1E(ActionEvent event){
        String desk = desk1E.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
    }
    @FXML
    private void bookTable1F(ActionEvent event){
        String desk = desk1F.getText();
        String dateString = this.date.getValue().toString();
        if(!loginModel.isBookingExist(dateString,desk)){
            employeeBooking(desk);
        } else{ noMoreBooking();}
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



    public void bookingConfirmation(){
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

    public void noMoreBooking(){
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

    public void noSameDeskBooking(){
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



    @FXML
    private TextField bookingNumber;
    @FXML
    private Label errorMessage;


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

    private ObservableList<EmployeeBookingHistory> employeeBookingHistory;
    @FXML
    private TableView<EmployeeBookingHistory> employeeBookingHistoryTableView;
    @FXML
    private TableColumn<EmployeeBookingHistory, String> bookingNumberCol;
    @FXML
    private TableColumn<EmployeeBookingHistory,String> bookingDateCol;
    @FXML
    private TableColumn<EmployeeBookingHistory,String> locationCol;
    @FXML
    private TableColumn<EmployeeBookingHistory,String> bookingStatusCol;

    @FXML
    public void viewBookingHistory(ActionEvent event){
        errorMessage.setText("");
        this.employeeBookingHistory = loginModel.displayEmployeeBookingHistory(ID);
        this.bookingNumberCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingNumber"));
        this.bookingDateCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingDate"));
        this.locationCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("deskNumber"));
        this.bookingStatusCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingStatus"));
        this.employeeBookingHistoryTableView.setItems(null);
        this.employeeBookingHistoryTableView.setItems(this.employeeBookingHistory);
    }

    @FXML
    public void viewPendingBooking(ActionEvent event){
        errorMessage.setText("");
        this.employeeBookingHistory = loginModel.displayEmployeePendingBooking(ID);
        this.bookingNumberCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingNumber"));
        this.bookingDateCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingDate"));
        this.locationCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("deskNumber"));
        this.bookingStatusCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingStatus"));
        this.employeeBookingHistoryTableView.setItems(null);
        this.employeeBookingHistoryTableView.setItems(this.employeeBookingHistory);
    }

    @FXML
    public void viewApprovedBooking(ActionEvent event){
        errorMessage.setText("");
        this.employeeBookingHistory = loginModel.displayEmployeeApprovedBooking(ID);
        this.bookingNumberCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingNumber"));
        this.bookingDateCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingDate"));
        this.locationCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("deskNumber"));
        this.bookingStatusCol.setCellValueFactory(new PropertyValueFactory<EmployeeBookingHistory,String>("bookingStatus"));
        this.employeeBookingHistoryTableView.setItems(null);
        this.employeeBookingHistoryTableView.setItems(this.employeeBookingHistory);
    }




}
