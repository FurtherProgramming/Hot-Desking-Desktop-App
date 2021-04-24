package main.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.SQLConnection;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.Connection;

public class AdminController implements Initializable {

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
    private void addEmployee(ActionEvent event){
        String sqlInsert = "INSERT INTO Employee(id,firstname,lastname,username,password,secret_question,answer_to_secret_question,role) VALUES (?,?,?,?,?,?,?,?)";

        try{
            Connection conn = SQLConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);

            stmt.setString(1,this.id.getText());
            stmt.setString(2,this.firstname.getText());
            stmt.setString(3,this.lastname.getText());
            stmt.setString(4,this.username.getText());
            stmt.setString(5,this.password.getText());
            stmt.setString(6,this.secretQuestion.getText());
            stmt.setString(7,this.answer.getText());
            stmt.setString(8,this.role.getText());

            stmt.execute();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void clearForm(ActionEvent event){
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
