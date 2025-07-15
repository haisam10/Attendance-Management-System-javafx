/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ats;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author mdhhd
 */
public class TeacherController implements Initializable {

    @FXML
    private Label tec_Id;
    @FXML
    private TreeView<String> treeview;
    @FXML
    private TextField textFild;
    private TreeItem<String> rootItem;
    @FXML
    private Label tec_Ast;
    @FXML
    private Button addButton;

    public void User_Id(String UserId) {
        tec_Ast.setText(UserId);
    }
    Image icon = new Image(getClass().getResourceAsStream("/image/folder-image.png"));
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
        rootItem = new TreeItem<>("Root", new ImageView(icon));
        treeview.setRoot(rootItem);
        treeview.setShowRoot(true);
    }
    public void addition() {
    String text = textFild.getText().trim();
    if (!text.isEmpty()) {
        TreeItem<String> selectedItem = treeview.getSelectionModel().getSelectedItem();
        TreeItem<String> newItem = new TreeItem<>(text, new ImageView(icon));

        if (selectedItem != null) {
            selectedItem.getChildren().add(newItem); // Add to selected node
            selectedItem.setExpanded(true); // Auto-expand to show the new child
        } else {
            rootItem.getChildren().add(newItem); // If nothing selected, add to root
        }

        textFild.clear();
    }
    }
public void Candidate(String selectedRole){
       tec_Ast.setText(selectedRole);
    }
    @FXML
    private void addButton(ActionEvent event) {
    }

}