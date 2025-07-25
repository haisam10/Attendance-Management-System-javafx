package ats;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StudentInfoController {

    @FXML
    private TextField nameField, studentIdField, emailField, phoneField;
    @FXML
    private ChoiceBox<String> departmentField, sectionField;
    @FXML
    private Label statusLabel;

    @FXML
    private TextField updateIdField, updateNameField, updateEmailField, updatePhoneField;
    @FXML
    private ChoiceBox<String> updateDeptField, updateSectionField;

    @FXML
    private Pane std_Info_input, std_info_update, std_All_Table;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> colName, colId, colDept, colEmail, colPhone, colsection;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    private final String url = "jdbc:mysql://localhost:3306/ats_data";
    private final String user = "root";
    private final String password = "";
    @FXML
    private Button logout_btn;
    @FXML
    private Button std_Info;
    @FXML
    private Button std_update;
    @FXML
    private Button std_All_table;
    @FXML
    private Label admin_Id;
    @FXML
    private Label admin_Ast;
    @FXML
    private Text section;
    @FXML
    private Button upd_info;

    public void initialize() {
        departmentField.setItems(FXCollections.observableArrayList("CSE", "EEE", "BBA"));
        sectionField.setItems(FXCollections.observableArrayList("A", "B", "C"));
        updateDeptField.setItems(FXCollections.observableArrayList("CSE", "EEE", "BBA"));
        updateSectionField.setItems(FXCollections.observableArrayList("A", "B", "C"));

        colName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudentId()));
        colDept.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDepartment()));
        colEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        colPhone.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPhone()));
        colsection.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSection()));

        studentTable.setItems(studentData);
    }

    @FXML
    private void saveStudentInfo() {
        String name = nameField.getText();
        String id = studentIdField.getText();
        String dept = departmentField.getValue();
        String section = sectionField.getValue();
        String email = emailField.getText();
        String phone = phoneField.getText();

        String query = "INSERT INTO student_info (name, student_id, department, section, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.setString(3, dept);
            stmt.setString(4, section);
            stmt.setString(5, email);
            stmt.setString(6, phone);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                statusLabel.setText("Student added successfully.");
                show_Std_Table();
            }

        } catch (SQLException e) {
            statusLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void upd_info_Btn() {
        String id = updateIdField.getText();
        String name = updateNameField.getText();
        String dept = updateDeptField.getValue();
        String section = updateSectionField.getValue();
        String email = updateEmailField.getText();
        String phone = updatePhoneField.getText();

        String query = "UPDATE student_info SET name=?, department=?, section=?, email=?, phone=? WHERE student_id=?";

        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, dept);
            stmt.setString(3, section);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, id);

            stmt.executeUpdate();
            show_Std_Table();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void show_Std_Table() {
        studentData.clear();

        String query = "SELECT * FROM student_info";

        try (Connection con = DriverManager.getConnection(url, user, password); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                studentData.add(new Student(
                        rs.getString("name"),
                        rs.getString("student_id"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("section")
                ));
            }

            std_Info_input.setVisible(false);
            std_info_update.setVisible(false);
            std_All_Table.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleStdInfo() {
        std_Info_input.setVisible(true);
        std_info_update.setVisible(false);
        std_All_Table.setVisible(false);
    }

    @FXML
    private void handleStdUpd() {
        std_Info_input.setVisible(false);
        std_info_update.setVisible(true);
        std_All_Table.setVisible(false);
    }

    @FXML
    private void student_panel_back_btn() {
//        std_Info_input.setVisible(false);
//        std_info_update.setVisible(false);
//        std_All_Table.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) logout_btn.getScene().getWindow(); // event ছাড়া
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.show();

        } catch (IOException e) {
            System.out.println("Failed to load dashboard.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void backToForm() {
        handleStdInfo();
    }
}
