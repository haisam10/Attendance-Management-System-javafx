/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ats;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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
}
