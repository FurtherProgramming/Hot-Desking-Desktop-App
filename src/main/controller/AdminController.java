package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import main.model.LoginAppModel;
import main.utility.BookingData;
import main.utility.EmployeeData;
import main.utility.User;
import main.utility.UserHolder;

import java.util.HashMap;
import java.util.LinkedList;

public class AdminController{

    private LoginAppModel loginModel = new LoginAppModel();
    private ObservableList<EmployeeData> employeeData;
    private ObservableList<BookingData> bookingData;
    private UserHolder holder = UserHolder.getInstance();
    private User user = holder.getUser();
    private String usernameString = user.getUsername();
    private String passwordString = user.getPassword();

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
    private TextField role;


    @FXML
    private Label manageEmployeeMessage;
    @FXML
    private Label manageBookingMessage;
    @FXML
    private TextField bookingNumber;
    @FXML
    private TableView<EmployeeData> employeeDataTableView;
    @FXML
    private TableColumn<EmployeeData,String> idColumn;
    @FXML
    private TableColumn<EmployeeData,String> firstnameColumn;
    @FXML
    private TableColumn<EmployeeData,String> lastnameColumn;
    @FXML
    private TableColumn<EmployeeData,String> usernameColumn;
    @FXML
    private TableColumn<EmployeeData,String> passwordColumn;
    @FXML
    private TableColumn<EmployeeData,String> secretQuestionColumn;
    @FXML
    private TableColumn<EmployeeData,String> answerColumn;
    @FXML
    private TableColumn<EmployeeData,String> roleColumn;
    @FXML
    private TableView<BookingData> bookingDataTableView;
    @FXML
    private TableColumn<BookingData,String> bookingNumberColumn;
    @FXML
    private TableColumn<BookingData,String> employeeIDColumn;
    @FXML
    private TableColumn<BookingData,String> timestampColumn;
    @FXML
    private TableColumn<BookingData,String> bookingDateColumn;
    @FXML
    private TableColumn<BookingData,String> locationColumn;
    @FXML
    private TableColumn<BookingData,String> bookingStatusColumn;
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
    private Label bookingReportStatus;
    @FXML
    private TextField bookingDate;
    @FXML
    private Label reportStatus;

    //Display Employee Table action method
    @FXML
    private void loadEmployeeData(ActionEvent event){
        manageEmployeeMessage.setText("");
        this.employeeData = loginModel.displayLoadEmployeeData();
        updateEmployeeTable();
    }

    //Display All Booking Table action method
    @FXML
    private void loadAllBookingData(ActionEvent event){
        manageBookingMessage.setText("");
        this.bookingData = loginModel.displayBookingData();
        updateBookingTable();
    }

    //Display Pending bookings action method
    @FXML
    private void loadPendingBooking(ActionEvent event){
        manageBookingMessage.setText("");
        this.bookingData = loginModel.displayPendingBookingData();
        updateBookingTable();
    }

    //Approve booking action method
    @FXML
    private void approveBooking(ActionEvent event){
        if(loginModel.approveBooking(bookingNumber.getText()))
        {manageBookingMessage.setText("Booking has been approved.");}
        else{
            manageBookingMessage.setTextFill(Color.RED);
            manageBookingMessage.setText("Booking cannot be approved.");
        }
        clearBookingNumber();
    }

    //Reject booking action method
    @FXML
    private void rejectBooking(ActionEvent event){
        if(loginModel.rejectBooking(bookingNumber.getText()))
        {manageBookingMessage.setText("Booking has been rejected.");}
        else{
            manageBookingMessage.setTextFill(Color.RED);
            manageBookingMessage.setText("Booking cannot be rejected.");
        }
        clearBookingNumber();
    }

    //Add new employee action method
    @FXML
    private void addEmployee(ActionEvent event){
        String employeeId = id.getText();
        String employeeFirstname = firstname.getText();
        String employeeLastname = lastname.getText();
        String employeeUsername = username.getText();
        String employeePassword = password.getText();
        String employeeSecretQ = secretQuestion.getText();
        String employeeAnswer = answer.getText();
        String employeeRole = role.getText();
            if(loginModel.isAddEmployee(employeeId,employeeFirstname,employeeLastname,employeeUsername,employeePassword,employeeSecretQ,employeeAnswer,employeeRole)){
                manageEmployeeMessage.setText("Employee has been added successfully! Refresh by clicking Load Data.");
            }
            else{
                manageEmployeeMessage.setText("Employee ID has been used, try with a new ID.");
            }
    }

    //clear Employee details Textfield action method
    @FXML
    private void clearForm(ActionEvent event){
        manageEmployeeMessage.setText("");
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.username.setText("");
        this.password.setText("");
        this.secretQuestion.setText("");
        this.answer.setText("");
        this.role.setText("");
    }

    //Update employee details action method based on which textfield is not empty
    @FXML
    private void updateEmployee(ActionEvent event){
        manageEmployeeMessage.setText("");
        String employeeId = id.getText();
        String employeeFirstname = firstname.getText();
        String employeeLastname = lastname.getText();
        String employeeUsername = username.getText();
        String employeePassword = password.getText();
        String employeeSecretQ = secretQuestion.getText();
        String employeeAnswer = answer.getText();
        String employeeRole = role.getText();
        if(!employeeId.isEmpty()){
            if(loginModel.isIDexist(employeeId)){
                if(!employeeFirstname.isEmpty()){
                    loginModel.updateFirstName(employeeId,employeeFirstname);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
                if(!employeeLastname.isEmpty()){
                    loginModel.updateLastname(employeeId,employeeLastname);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
                if(!employeeUsername.isEmpty()){
                    loginModel.updateUsername(employeeId,employeeUsername);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
                if(!employeePassword.isEmpty()){
                    loginModel.updatePasswordByID(employeeId,employeePassword);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
                if(!employeeSecretQ.isEmpty()){
                    loginModel.updateSecretQuestion(employeeId,employeeSecretQ);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
                if(!employeeAnswer.isEmpty()){
                    loginModel.updateAnswer(employeeId,employeeAnswer);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
                if(!employeeRole.isEmpty()){
                    loginModel.updateRole(employeeId,employeeRole);
                    manageEmployeeMessage.setText("Updated successfully, please load data to see changes");
                }
            }else{
                manageEmployeeMessage.setText("Make sure you enter the right employee ID");
            }
        } else{
            manageEmployeeMessage.setText("Make sure you enter an employee ID");
        }
    }

    //Delete employee action method
    @FXML
    private void deleteEmployee(ActionEvent event){
        manageEmployeeMessage.setText("");
        String employeeId = id.getText();
        if(!employeeId.isEmpty() && loginModel.isIDexist(employeeId)){
            loginModel.deleteEmployee(employeeId);
            manageEmployeeMessage.setText("Employee has been deleted. Please load data to refresh.");
        }else{
            manageEmployeeMessage.setText("Make sure you enter the right employee ID");
        }
    }

    //visual representation of the booking
    //admin can look at past date
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

    //Locking all seats action method
    @FXML
    private void lockdownAll(){
        String dateString = date.getValue().toString();
        LinkedList<Button> deskLL = getDeskLL();
        for(int i = 0; i < deskLL.size(); i++){
            if(loginModel.isBookingExist(dateString,deskLL.get(i).getText())){
                loginModel.lockBooking(dateString,deskLL.get(i).getText());
            }
                loginModel.adminLockdownTables(dateString,deskLL.get(i).getText(),usernameString,passwordString);

        }
    }

    //Generate employee table csv report
    @FXML
    private void generateEmployeeReport(ActionEvent event){
        loginModel.exportEmployeeTable();
        reportStatus.setText("Report has been generated, please exit the application to see it.");
    }

    //Generate booking table csv report
    //if bookingDate textfield is not empty, then generate report for a given date
    @FXML
    private void generateBookingReport(ActionEvent event){
        if(!bookingDate.getText().isEmpty()){
            if(loginModel.exportBookingFromDate(bookingDate.getText())){
                bookingReportStatus.setText("Report for that booking date has been generated, please exit the application to see it.");
            }
            else{
                bookingReportStatus.setText("Make sure date is in correct format (yyyy-mm-dd)");
            }
        }else if(bookingDate.getText().isEmpty()){
            loginModel.exportBookingTable();
            bookingReportStatus.setText("Report has been generated, please exit the application to see it.");
        }
    }

    //Lock desk 1A action method
    @FXML
    private void lock1A(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1A.getText(),usernameString,passwordString);
    }

    //Lock desk 1B action method
    @FXML
    private void lock1B(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1B.getText(),usernameString,passwordString);
    }

    //Lock desk 1C action method
    @FXML
    private void lock1C(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1C.getText(),usernameString,passwordString);
    }

    //Lock desk 1D action method
    @FXML
    private void lock1D(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1D.getText(),usernameString,passwordString);
    }

    //Lock desk 1E action method
    @FXML
    private void lock1E(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1E.getText(),usernameString,passwordString);
    }

    //Lock desk 1F action method
    @FXML
    private void lock1F(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1F.getText(),usernameString,passwordString);
    }

    //helper method to clear bookingNumber textfield
    private void clearBookingNumber(){
        bookingNumber.setText("");
    }

    //helper method to return list of all desks
    private LinkedList<Button> getDeskLL(){
        LinkedList<Button> deskLL = new LinkedList<Button>();
        deskLL.add(desk1A);
        deskLL.add(desk1B);
        deskLL.add(desk1C);
        deskLL.add(desk1D);
        deskLL.add(desk1E);
        deskLL.add(desk1F);
        return  deskLL;
    }

    //helper method to update employee table
    private void updateEmployeeTable(){
        this.idColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("ID"));
        this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("firstName"));
        this.lastnameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("lastName"));
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("userName"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("password"));
        this.secretQuestionColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("secretQuestion"));
        this.answerColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("answer"));
        this.roleColumn .setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("role"));
        this.employeeDataTableView.setItems(null);
        this.employeeDataTableView.setItems(this.employeeData);
    }

    //helper method to update booking table
    private void updateBookingTable(){
        this.bookingNumberColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingNumber"));
        this.employeeIDColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("employeeId"));
        this.timestampColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("timestamp"));
        this.bookingDateColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingDate"));
        this.locationColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("deskNumber"));
        this.bookingStatusColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingStatus"));
        this.bookingDataTableView.setItems(null);
        this.bookingDataTableView.setItems(this.bookingData);
    }

}
