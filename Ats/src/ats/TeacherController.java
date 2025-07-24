/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ats;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mdhhd
 */
public class TeacherController implements Initializable {


    @FXML
    private Label tec_Ast;
    @FXML
    private Button logout_btn;
    @FXML
    private Label tec_Id;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void Candidate(String selectedRole){
       tec_Ast.setText(selectedRole);
    }
    public void User_Id(String UserId) {
        tec_Ast.setText(UserId);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) logout_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();

    } catch (IOException e) {
        System.out.println("Logout failed: Couldn't load FXMLDocument.fxml");
    }
    }
}