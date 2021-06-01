package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import main.*;
import javafx.event.ActionEvent;
import main.model.LoginAppModel;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public LoginAppModel loginModel = new LoginAppModel();

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
    private Label errorMessage;
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


    private SQLConnection dc;
    private ObservableList<EmployeeData> employeeData;
    private ObservableList<BookingData> bookingData;
    private UserHolder holder = UserHolder.getInstance();
    private User user = holder.getUser();
    private String usernameString = user.getUsername();
    private String passwordString = user.getPassword();



    public void initialize (URL url, ResourceBundle rb){
        this.dc = new SQLConnection();
    }

    @FXML
    private void loadEmployeeData(ActionEvent event){
        errorMessage.setText("");
        this.employeeData = loginModel.displayLoadEmployeeData();
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

    @FXML
    private void loadAllBookingData(ActionEvent event){
        this.bookingData = loginModel.displayBookingData();
        this.bookingNumberColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingNumber"));
        this.employeeIDColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("employeeId"));
        this.timestampColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("timestamp"));
        this.bookingDateColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingDate"));
        this.locationColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("deskNumber"));
        this.bookingStatusColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingStatus"));
        this.bookingDataTableView.setItems(null);
        this.bookingDataTableView.setItems(this.bookingData);
    }

    @FXML
    private void loadPendingBooking(ActionEvent event){
        this.bookingData = loginModel.displayPendingBookingData();
        this.bookingNumberColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingNumber"));
        this.employeeIDColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("employeeId"));
        this.timestampColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("timestamp"));
        this.bookingDateColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingDate"));
        this.locationColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("deskNumber"));
        this.bookingStatusColumn.setCellValueFactory(new PropertyValueFactory<BookingData, String>("bookingStatus"));
        this.bookingDataTableView.setItems(null);
        this.bookingDataTableView.setItems(this.bookingData);
    }

    @FXML
    private void approveBooking(ActionEvent event){
        loginModel.approveBooking(bookingNumber.getText());
    }


    @FXML
    private void rejectBooking(ActionEvent event){
        loginModel.rejectBooking(bookingNumber.getText());
    }

    @FXML
    private void addEmployee(ActionEvent event) throws Exception{
            String id = this.id.getText();
            String fname = this.firstname.getText();
            String lname = this.lastname.getText();
            String uname = this.username.getText();
            String pass = this.password.getText();
            String sq = this.secretQuestion.getText();
            String asq = this.answer.getText();
            String role = this.role.getText();
            if(loginModel.isAddEmployee(id,fname,lname,uname,pass,sq,asq,role)){
                errorMessage.setText("Employee has been added successfully! Refresh by clicking Load Data.");
            }
            else{
                errorMessage.setText("Employee ID has been used, try with a new ID.");
            }
    }

    @FXML
    private void clearForm(ActionEvent event){
        errorMessage.setText("");
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.username.setText("");
        this.password.setText("");
        this.secretQuestion.setText("");
        this.answer.setText("");
        this.role.setText("");
    }

    @FXML
    private void updateEmployee(ActionEvent event){
        errorMessage.setText("");
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
                }if(!employeeLastname.isEmpty()){
                    loginModel.updateLastname(employeeId,employeeLastname);
                }if(!employeeUsername.isEmpty()){
                    loginModel.updateUsername(employeeId,employeeUsername);
                }if(!employeePassword.isEmpty()){
                    loginModel.updatePasswordByID(employeeId,employeePassword);
                }if(!employeeSecretQ.isEmpty()){
                    loginModel.updateSecretQuestion(employeeId,employeeSecretQ);
                }if(!employeeAnswer.isEmpty()){
                    loginModel.updateAnswer(employeeId,employeeAnswer);
                }if(!employeeRole.isEmpty()){
                    loginModel.updateRole(employeeId,employeeRole);
                }
            }else{
                errorMessage.setText("Make sure you enter the right employee ID");
            }
        } else{
            errorMessage.setText("Make sure you enter an employee ID");
        }
    }

    @FXML
    public void deleteEmployee(ActionEvent event){
        errorMessage.setText("");
        String employeeId = id.getText();
        if(!employeeId.isEmpty() && loginModel.isIDexist(employeeId)){
            loginModel.deleteEmployee(employeeId);
        }else{
            errorMessage.setText("Make sure you enter the right employee ID");
        }
    }

    @FXML
    public void chooseDate(ActionEvent event){
            HashMap<Button, Rectangle> desks = new HashMap<Button,Rectangle>();
            desks.put(desk1A, square_1A);
            desks.put(desk1B, square_1B);
            desks.put(desk1C, square_1C);
            desks.put(desk1D, square_1D);
            desks.put(desk1E, square_1E);
            desks.put(desk1F, square_1F);
        ViewBookingController viewBookingController = new ViewBookingController();
        viewBookingController.chooseDate(desks, date);
    }

    @FXML
    public void lockdownAll(){
        String dateString = date.getValue().toString();
        LinkedList<Button> deskButton = new LinkedList<Button>();
        deskButton.add(desk1A);
        deskButton.add(desk1B);
        deskButton.add(desk1C);
        deskButton.add(desk1D);
        deskButton.add(desk1E);
        deskButton.add(desk1F);
        for(int i = 0; i < deskButton.size(); i++){
            loginModel.adminLockdownTables(dateString,deskButton.get(i).getText(),usernameString,passwordString);
        }
    }


    @FXML
    private Label reportStatus;
    @FXML
    public void generateEmployeeReport(ActionEvent event){
        loginModel.exportEmployeeDatabase();
        reportStatus.setText("Report has been generated, please exit the application to see it.");
    }


    @FXML
    private Label bookingReportStatus;
    @FXML
    private TextField bookingDate;
    @FXML
    public void generateBookingReport(ActionEvent event){
        //if bookingDate textfield is not empty, then generate report for a given date
        if(!bookingDate.getText().isEmpty()){
            if(loginModel.exportBookingFromDate(bookingDate.getText())){
                bookingReportStatus.setText("Report for that booking date has been generated, please exit the application to see it.");
            }else{
                bookingReportStatus.setText("Make sure date is in correct format (yyyy-mm-dd)");
            }
        }else if(bookingDate.getText().isEmpty()){
            loginModel.exportBookingDatabase();
            bookingReportStatus.setText("Report has been generated, please exit the application to see it.");
        }
    }
    @FXML
    private void lock1A(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1A.getText(),usernameString,passwordString);
    }
    @FXML
    private void lock1B(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1B.getText(),usernameString,passwordString);
    }
    @FXML
    private void lock1C(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1C.getText(),usernameString,passwordString);
    }
    @FXML
    private void lock1D(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1D.getText(),usernameString,passwordString);
    }
    @FXML
    private void lock1E(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1E.getText(),usernameString,passwordString);
    }
    @FXML
    private void lock1F(ActionEvent event){
        loginModel.adminLockdownTables(date.getValue().toString(),desk1F.getText(),usernameString,passwordString);
    }



}
