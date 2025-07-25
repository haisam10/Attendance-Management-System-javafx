package ats;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StudentInfoController implements Initializable {

    @FXML private TextField nameField, studentIdField, departmentField, emailField, phoneField;
    @FXML private TextField updateIdField, updateNameField, updateDeptField, updateEmailField, updatePhoneField;
    @FXML private Label statusLabel, admin_Id, admin_Ast;
    @FXML private Button logout_btn, std_Info, std_update, std_All_table;
    @FXML private Pane std_Info_input, std_info_update, std_All_Table;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> colName, colId, colDept, colEmail, colPhone;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    public void User_Id(String UserId) {
        admin_Id.setText(UserId);
    }

    public void Candidate(String selectedRole) {
        admin_Ast.setText(selectedRole);
    }
    // Back to admin page
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
            System.out.println("Logout failed: Couldn't load admin.fxml");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
        std_All_Table.setVisible(false);

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colDept.setCellValueFactory(new PropertyValueFactory<>("department"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

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

    private void clearFields() {
        nameField.clear();
        studentIdField.clear();        
        departmentField.clear();
        emailField.clear();
        phoneField.clear();
    }
    private void updateStudentInfo(ActionEvent event) {
        System.out.println("Update button clicked");
}


    @FXML
    private void handleStdInfo(ActionEvent event) {
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
        std_All_Table.setVisible(false);
    }

    @FXML
    private void handleStdUpd(ActionEvent event) {
        std_info_update.setVisible(true);
        std_Info_input.setVisible(false);
        std_All_Table.setVisible(false);
    }

    @FXML
    private void show_Std_Table(ActionEvent event) {
        std_All_Table.setVisible(true);
        std_Info_input.setVisible(false);
        std_info_update.setVisible(false);
        loadStudentData();
    }

    @FXML
    private void backToForm(ActionEvent event) {
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
        std_All_Table.setVisible(false);
    }

    private void loadStudentData() {
        studentData.clear();
        String sql = "SELECT name, student_id, department, email, phone FROM student_info";

        try (Connection conn = DatabaseConnection.getDefaultConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             var rs = pstmt.executeQuery()) {

            while (rs.next()) {
                studentData.add(new Student(
                        rs.getString("name"),
                        rs.getString("student_id"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }

            studentTable.setItems(studentData);

        } catch (SQLException e) {
            System.out.println("Student data load করতে সমস্যা: " + e.getMessage());
        }
    }
}