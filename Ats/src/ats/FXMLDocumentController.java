/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package ats;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        whoSelect.setItems(list);
        whoSelect.setValue("Who are you?");
    }    
    
    @FXML
    private void u_ID_pass_Submit(ActionEvent event) {
    }
    
}