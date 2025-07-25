package ats;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentAttendanceCheckController implements Initializable {

    @FXML
    private TextField studentIdField;
    @FXML
    private Label resultLabel;
    @FXML
    private Button checkButton;
    @FXML
    private Button logout_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultLabel.setText("");
    }

    @FXML
    private void checkAttendance() {
        String studentId = studentIdField.getText().trim();

        if (studentId.isEmpty()) {
            resultLabel.setText("Please enter a student ID.");
            return;
        }

        try (Connection conn = DatabaseConnection.getDefaultConnection()) {

            String totalQuery = "SELECT COUNT(*) AS total FROM attendance WHERE student_id = ?";
            String presentQuery = "SELECT COUNT(*) AS present FROM attendance WHERE student_id = ? AND status = 1";

            PreparedStatement totalStmt = conn.prepareStatement(totalQuery);
            PreparedStatement presentStmt = conn.prepareStatement(presentQuery);

            totalStmt.setString(1, studentId);
            presentStmt.setString(1, studentId);

            ResultSet totalRs = totalStmt.executeQuery();
            ResultSet presentRs = presentStmt.executeQuery();

            if (totalRs.next() && presentRs.next()) {
                int total = totalRs.getInt("total");
                int present = presentRs.getInt("present");

                if (total == 0) {
                    resultLabel.setText("No attendance record found.");
                } else {
                    double percentage = (present * 100.0) / total;
                    resultLabel.setText("Present: " + present + "/" + total +
                            "\nPercentage: " + String.format("%.2f", percentage) + "%");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultLabel.setText("Error retrieving attendance data.");
        }
    }
    @FXML
    private void student_panel_back_btn() {
//        std_Info_input.setVisible(false);
//        std_info_update.setVisible(false);
//        std_All_Table.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Student.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) logout_btn.getScene().getWindow(); // event ছাড়া
        stage.setScene(new Scene(root));
        stage.setTitle("Student");
        stage.show();

        } catch (IOException e) {
            System.out.println("Failed to load Student.fxml: " + e.getMessage());
        }
    }
}
