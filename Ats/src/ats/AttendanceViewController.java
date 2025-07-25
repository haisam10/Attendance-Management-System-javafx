package ats;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date; // ✅ Required
import java.time.LocalDate;

public class AttendanceViewController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> deptCombo;
    @FXML
    private ComboBox<String> sectionCombo;
    @FXML
    private VBox studentListVBox;
    @FXML
    private Button loadStudentsBtn;
    @FXML
    private Button submitBtn;

    private List<CheckBox> studentCheckboxes = new ArrayList<>();

    private Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ats_data", "root", "");
        } catch (SQLException e) {
            showError("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deptCombo.getItems().addAll("CSE", "EEE", "BBA");
        sectionCombo.getItems().addAll("A", "B", "C");
    }

    @FXML
    private void loadStudents() {
        studentListVBox.getChildren().clear();
        studentCheckboxes.clear();

        String department = deptCombo.getValue();
        String section = sectionCombo.getValue();
        LocalDate date = datePicker.getValue();

        if (department == null || section == null || date == null) {
            showError("Please select department, section and date.");
            return;
        }

        try (Connection conn = connect()) {
            String sql = "SELECT student_id, name FROM student_info WHERE department = ? AND section = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, department);
            stmt.setString(2, section);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("student_id");
                String name = rs.getString("name");

                CheckBox cb = new CheckBox(id + " - " + name);
                cb.setUserData(id);
                studentListVBox.getChildren().add(cb);
                studentCheckboxes.add(cb);
            }

            if (studentCheckboxes.isEmpty()) {
                showInfo("No students found for selected department and section.");
            }

        } catch (SQLException e) {
            showError("Error loading students: " + e.getMessage());
        }
    }

    @FXML
    private void submitAttendance() {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            showError("Please select a date.");
            return;
        }

        try (Connection conn = connect()) {
            String insertSQL = "INSERT INTO attendance (student_id, date, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSQL);

            for (CheckBox cb : studentCheckboxes) {
                String studentId = cb.getUserData().toString();
                boolean present = cb.isSelected();

                stmt.setString(1, studentId);
                stmt.setDate(2, Date.valueOf(date));
                stmt.setBoolean(3, present);
                stmt.addBatch();
            }

            stmt.executeBatch();
            showInfo("Attendance submitted successfully!");

        } catch (SQLException e) {
            showError("Error submitting attendance: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
