package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.LoginAppModel;

public class Main extends Application {

    public LoginAppModel loginModel = new LoginAppModel();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
        loginModel.AutomaticCancelBooking();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 