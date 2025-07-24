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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mdhhd
 */
public class AdminController implements Initializable {

    @FXML
    private Label admin_Id;
    @FXML
    private Label admin_Ast;
    @FXML
    private AnchorPane candidate_ast;
    @FXML
    private Button logout_btn;
    @FXML
    private Button std_Info;
    @FXML
    private Pane admin_All_button;
    
    public void User_Id(String UserId){
        admin_Id.setText(UserId);
    }
    public void Candidate(String selectedRole){
       admin_Ast.setText(selectedRole);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     // Student Info
    @FXML
    private void handleStdInfo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentInfo.fxml"));
            Parent studentInfoRoot = loader.load();

            candidate_ast.getChildren().clear();
            candidate_ast.getChildren().add(studentInfoRoot);

            AnchorPane.setTopAnchor(studentInfoRoot, 0.0);
            AnchorPane.setBottomAnchor(studentInfoRoot, 0.0);
            AnchorPane.setLeftAnchor(studentInfoRoot, 0.0);
            AnchorPane.setRightAnchor(studentInfoRoot, 0.0);

        } catch (IOException e) {
            System.out.println("Failed to load StudentInfo.fxml: " + e.getMessage());
        }
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
