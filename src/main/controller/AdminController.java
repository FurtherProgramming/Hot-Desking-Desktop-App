package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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
    private Label employeeAddedStatus;
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



    public void initialize (URL url, ResourceBundle rb){
        this.dc = new SQLConnection();
    }

    @FXML
    private void loadEmployeeData(ActionEvent event){
        employeeAddedStatus.setText("");
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
                employeeAddedStatus.setText("Employee has been added successfully! Refresh by clicking Load Data.");
            }
            else{
                employeeAddedStatus.setText("Employee ID has been used, try with a new ID.");
            }
    }

    @FXML
    private void clearForm(ActionEvent event){
        employeeAddedStatus.setText("");
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
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/UpdateEmployeeDetailsByAdmin.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Update Employee Dashboard");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private UserHolder holder = UserHolder.getInstance();
    private User user = holder.getUser();
    private String usernameString = user.getUsername();
    private String passwordString = user.getPassword();

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



}
