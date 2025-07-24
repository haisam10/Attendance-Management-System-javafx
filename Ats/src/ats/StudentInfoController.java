package ats;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentInfoController implements Initializable {

    @FXML 
    private TextField nameField;
    @FXML 
    private TextField studentIdField;
    @FXML 
    private TextField departmentField;
    @FXML 
    private TextField emailField;
    @FXML 
    private TextField phoneField;
    @FXML 
    private Label statusLabel;
    @FXML
    private Button logout_btn;
    @FXML
    private Button std_Info;
    @FXML
    private Button std_update;
    @FXML
    private Pane std_Info_input;
    @FXML
    private Pane std_info_update;
    @FXML
    private Button std_All_table;
    @FXML
    private TextField updateIdField;
    @FXML
    private TextField updateNameField;
    @FXML
    private TextField updateDeptField;
    @FXML
    private TextField updateEmailField;
    @FXML
    private TextField updatePhoneField;
    @FXML
    private Pane std_All_Table;

    // Show id & Role 
    @FXML
    private Label admin_Id;
    @FXML
    private Label admin_Ast;
    public void User_Id(String UserId){
        admin_Id.setText(UserId);
    }
    public void Candidate(String selectedRole){
       admin_Ast.setText(selectedRole);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
    }
    
    // Save student data to database
    @FXML
    private void saveStudentInfo() {
        String name = nameField.getText();
        String studentId = studentIdField.getText();
        String dept = departmentField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || studentId.isEmpty() || dept.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            statusLabel.setText("❌ Please fill all fields.");
            return;
        }

        String sql = "INSERT INTO student_info (name, student_id, department, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getDefaultConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, studentId);
            pstmt.setString(3, dept);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                statusLabel.setText("✅ Student info saved successfully.");
                clearFields();
            }

        } catch (SQLException e) {
            statusLabel.setText("❌ Database error: " + e.getMessage());
        }
    }
    
    // Clear input fields
    private void clearFields() {
        nameField.clear();
        studentIdField.clear();
        departmentField.clear();
        emailField.clear();
        phoneField.clear();
    }
    
    // Go back to admin.fxml
    @FXML
        private void student_panel_back_btn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logout_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

    } catch (IOException e) {
        System.out.println("Logout failed: Couldn't load FXMLDocument.fxml");
    }
    }
        
    // Show "Add Student" Pane
    @FXML
    private void handleStdInfo(ActionEvent event) {
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
    }

    @FXML
    private void handleStdUpd(ActionEvent event) {
        std_Info_input.setVisible(false);
        std_info_update.setVisible(true);
    }

    @FXML
    private void backToForm(ActionEvent event) {
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
    }
    
    // update student value
    @FXML
    private void updateStudentInfo(ActionEvent event) {
        String name = nameField.getText();
        String studentId = studentIdField.getText();
        String dept = departmentField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || studentId.isEmpty() || dept.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            statusLabel.setText("❌ Please fill all fields.");
            return;
        }

        String sql = "UPDATE student_info SET name = ?, department = ?, email = ?, phone = ? WHERE student_id = ?";

        try (Connection conn = DatabaseConnection.getDefaultConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, dept);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, studentId);  // student_id condition

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                statusLabel.setText("✅ Student info updated successfully.");
                clearFields();
            } else {
                statusLabel.setText("❌ No student found with that ID.");
            }

        } catch (SQLException e) {
            statusLabel.setText("❌ Database error: " + e.getMessage());
        }
    }
    
    // lo
}
