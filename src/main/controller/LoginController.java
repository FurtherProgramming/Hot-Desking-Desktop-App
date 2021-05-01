package main.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import main.option;
import main.model.LoginAppModel;


public class LoginController implements Initializable {

    public LoginAppModel loginModel = new LoginAppModel();

    @FXML
    private Label isConnected;
    @FXML
    private Label loginStatus;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button loginButton;
    @FXML
    private ComboBox<option> combobox;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }


    }

    /* login Action method
       check if user input is the same as database.
     */
    @FXML
    public void Login(ActionEvent event) {

        try {
            if (loginModel.isAdminLogin(this.txtUsername.getText(), this.txtPassword.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                adminLogin();
            }
            if (loginModel.isEmployeeLogin(this.txtUsername.getText(), this.txtPassword.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                employeeLogin();
            } else {
                this.loginStatus.setText("Wrong Credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void employeeLogin() {
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("DummyEmployee.fxml").openStream());
//            EmployeeController employeeController = (EmployeeController)loader.getController();
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Employee Dashboard");
            userStage.setResizable(false);
            userStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminloader = new FXMLLoader();
            Pane adminroot = (Pane) adminloader.load(getClass().getResource("abc.fxml").openStream());
//            Pane adminroot = (Pane)adminloader.load(getClass().getResource("ui/Admin.fxml").openStream());
//            AdminController adminController = (AdminController)adminloader.getController();
            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}