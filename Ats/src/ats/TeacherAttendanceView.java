package ats;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

public class TeacherAttendanceView implements Initializable {

    @FXML private DatePicker attendanceDatePicker;
    @FXML private ComboBox<String> sectionComboBox;
    @FXML private TableView<AttendanceRecord> attendanceTable;
    @FXML private TableColumn<AttendanceRecord, String> studentIdCol;
    @FXML private TableColumn<AttendanceRecord, String> studentNameCol;
    @FXML private TableColumn<AttendanceRecord, Boolean> statusCol;
    @FXML private Button tec_View_Attendance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sectionComboBox.setItems(FXCollections.observableArrayList("A", "B", "C", "D"));

        studentIdCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudentId()));
        studentNameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudentName()));

        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusCol.setCellFactory(CheckBoxTableCell.forTableColumn(statusCol));
    }

    @FXML
    private void tec_View_Attendance_Btn(ActionEvent event) {
        LocalDate selectedDate = attendanceDatePicker.getValue();
        String selectedSection = sectionComboBox.getValue();

        if (selectedDate == null || selectedSection == null) {
            new Alert(Alert.AlertType.WARNING, "Please select both date and section.").show();
            return;
        }

        ObservableList<AttendanceRecord> attendanceList = FXCollections.observableArrayList();

        try {
            Connection conn = DBConnector.getConnection();
            if (conn == null) {
                new Alert(Alert.AlertType.ERROR, "Database connection failed!").show();
                return;
            }

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
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load attendance!").show();
        }
    }

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
