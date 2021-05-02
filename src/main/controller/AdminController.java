package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.EmployeeData;
import main.SQLConnection;
import javafx.event.ActionEvent;
import main.model.LoginAppModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.Connection;

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

    private SQLConnection dc;
    private ObservableList<EmployeeData> data;
    private String sql = "SELECT * from Employee";


    public void initialize (URL url, ResourceBundle rb){
        this.dc = new SQLConnection();
    }

    @FXML
    private void loadEmployeeData(ActionEvent event) throws SQLException{
        try{
            employeeAddedStatus.setText("");
            Connection conn = SQLConnection.connect();
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                this.data.add(new EmployeeData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
            }
        } catch (SQLException e){
            System.err.println("Error" + e);
        }

        this.idColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("ID"));
        this.firstnameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("firstName"));
        this.lastnameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("lastName"));
        this.usernameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("userName"));
        this.passwordColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("password"));
        this.secretQuestionColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("secretQuestion"));
        this.answerColumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("answer"));
        this.roleColumn .setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("role"));

        this.employeeDataTableView.setItems(null);
        this.employeeDataTableView.setItems(this.data);
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



}
