package ats;

import ats.DatabaseConnection.DBConnector;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeacherAttendanceViewController implements Initializable {

    @FXML private DatePicker attendanceDatePicker;
    @FXML private ComboBox<String> sectionComboBox;
    @FXML private TableView<AttendanceRecord> attendanceTable;
    @FXML private TableColumn<AttendanceRecord, String> studentIdCol;
    @FXML private TableColumn<AttendanceRecord, String> studentNameCol;
    @FXML private TableColumn<AttendanceRecord, Boolean> statusCol;
    @FXML private Button tec_View_Attendance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Section values
        sectionComboBox.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));

        // Table columns mapping
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusCol.setCellFactory(CheckBoxTableCell.forTableColumn(statusCol));
    }

    @FXML
    private void tec_View_Attendance_Btn(ActionEvent event) {
        LocalDate selectedDate = attendanceDatePicker.getValue();
        String selectedSection = sectionComboBox.getValue();

        if (selectedDate == null || selectedSection == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select both date and section.");
            alert.show();
            return;
        }

        ObservableList<AttendanceRecord> attendanceList = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnector.getConnection();

            String sql = "SELECT student_id, student_name, status FROM attendance WHERE date = ? AND section = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(selectedDate));
            ps.setString(2, selectedSection);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attendanceList.add(new AttendanceRecord(
                        rs.getString("student_id"),
                        rs.getString("student_name"),
                        rs.getBoolean("status")
                ));
            }

            attendanceTable.setItems(attendanceList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Model class for AttendanceRecord
    public static class AttendanceRecord {
        private final SimpleStringProperty studentId;
        private final SimpleStringProperty studentName;
        private final SimpleBooleanProperty status;

        public AttendanceRecord(String studentId, String studentName, boolean status) {
            this.studentId = new SimpleStringProperty(studentId);
            this.studentName = new SimpleStringProperty(studentName);
            this.status = new SimpleBooleanProperty(status);
        }

        public String getStudentId() { return studentId.get(); }
        public String getStudentName() { return studentName.get(); }
        public boolean getStatus() { return status.get(); }

        public void setStatus(boolean status) { this.status.set(status); }

        public BooleanProperty statusProperty() { return status; }
    }
}
