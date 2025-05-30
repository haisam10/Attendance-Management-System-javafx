/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package ats;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author mdhhd
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private TextField userID;
    @FXML
    private TextField userPass;
    @FXML
    private ChoiceBox<String> whoSelect;
    
    ObservableList<String> list = FXCollections.observableArrayList("Student","Teacher","Admin");
    @FXML
    private Button u_ID_pass_Submit;
    
    // ADMIN User Password Credentials
    private final String[][] adminCredentials ={
        {"admin","admin"},
        {"admn1","admin1"},
        {"223071114","Haisam1234"}
    };
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        whoSelect.setItems(list);
        whoSelect.setValue("Who are you?");
        
    }    
    
    @FXML
    private void u_ID_pass_Submit(ActionEvent event) {
        // get text
        String UserId = userID.getText();
        String UserPassword = userPass.getText();
        String selectedRole  = whoSelect.getValue();
        
        // print text
        System.out.println("User ID: " + UserId);
        System.out.println("User Password: " + UserPassword);
        System.out.println("AST Select: " + selectedRole);
        
        // match user id & password
        boolean isValid = false;
        
        switch(selectedRole){
            case "Admin":
                isValid = checkCredentials(UserId,UserPassword,adminCredentials);
                break;
        }
        if (isValid) {
            System.out.println("Login successful for " + selectedRole + ": " + UserId);
            
            try {
                Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Admin Panel");
                stage.setScene(new Scene(root));
                stage.show();

                // Close login window
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } catch (IOException e) {
                System.err.println("Failed to load admin.fxml: " + e.getMessage());
            }
            
        } else {
            System.out.println("Invalid ID or Password for " + selectedRole);
        }
    }
        private boolean checkCredentials(String id, String pass, String[][] credentials) {
        for (String[] pair : credentials) {
            if (pair[0].equals(id) && pair[1].equals(pass)) {     
                return true;
            }
        }
        return false;
    }
}