package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.SQLConnection;
import main.model.LoginAppModel;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewBookingController implements Initializable {

    private SQLConnection dc;

    public LoginAppModel loginModel = new LoginAppModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dc = new SQLConnection();
    }

    public void chooseDate(HashMap<Button,Rectangle> desks, DatePicker date){
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

    public void invalidDate(){
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource("../ui/InvalidDate.fxml").openStream());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Booking Confirmation");
            stage.setResizable(false);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
