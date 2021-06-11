package main.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;


import main.User;
import main.UserHolder;
import main.model.LoginAppModel;


public class LoginController implements Initializable {

    public LoginAppModel loginModel = new LoginAppModel();

    @FXML
    private Button signupButton;

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
    private Button forgetPasswordButton;

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

            if (loginModel.isAdminLogin(this.txtUsername.getText(), this.txtPassword.getText())) {
                adminLogin();
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
            }
            if (loginModel.isEmployeeLogin(this.txtUsername.getText(), this.txtPassword.getText())) {
                employeeLogin();
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
            }
            else {
                this.loginStatus.setText("Wrong Credentials");
            }

    }

    //Open employee dashboard
    public void employeeLogin(){
        try {
            User user = new User(this.txtUsername.getText(), this.txtPassword.getText());
            UserHolder holder = UserHolder.getInstance();
            holder.setUser(user);
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/Employee.fxml").openStream());
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Employee Dashboard");
            userStage.setResizable(true);
            userStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Open admin dashboard
    public void adminLogin(){
        try {
            User user = new User(this.txtUsername.getText(), this.txtPassword.getText());
            UserHolder holder = UserHolder.getInstance();
            holder.setUser(user);
            Stage adminStage = new Stage();
            FXMLLoader adminloader = new FXMLLoader();
            Pane adminroot = (Pane)adminloader.load(getClass().getResource("../ui/Admin.fxml").openStream());
            Scene adminScene = new Scene(adminroot);
            adminStage.setScene(adminScene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(true);
            adminStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* Sign Up Action method
       take user to a new scene to register
    */
    @FXML
    public void SignUp(ActionEvent event){
        try{
            Stage stage = (Stage) this.signupButton.getScene().getWindow();
            stage.close();
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("../ui/Signup.fxml").openStream());
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("SignUp Dashboard");
            userStage.setResizable(true);
            userStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Forget Password Action method
       take user to a new scene to reset their password
    */
    @FXML
    public void ForgetPassword(ActionEvent event){
        try{
            Stage stage = (Stage) this.forgetPasswordButton.getScene().getWindow();
            stage.close();
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("../ui/ResetPassword.fxml").openStream());
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Reset Password Dashboard");
            userStage.setResizable(true);
            userStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }





}