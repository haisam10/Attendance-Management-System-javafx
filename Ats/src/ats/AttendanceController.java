/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ats;

import com.sun.jdi.connect.spi.Connection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AttendanceController implements Initializable{


    @FXML
    private Label lblUserId;
    @FXML
    private Label lblRole;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String> statusBox;

    private String userId, role;

    public void User_Id(String id) {
        this.userId = id;
        lblUserId.setText("User ID: " + id);
    }

    public void Candidate(String role) {
        this.role = role;
        lblRole.setText("Role: " + role);
    }

    @FXML
    public void initialize() {
        statusBox.setItems(FXCollections.observableArrayList("Present", "Absent", "Late"));
    }

    @FXML
    private void markAttendance(ActionEvent event) {
        LocalDate date = datePicker.getValue();
        String status = statusBox.getValue();

        if (date == null || status == null) {
            System.out.println("Select date and status");
            return;
        }

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = (Connection) connectNow.getConnection();

        String query = "INSERT INTO attendance (user_id, date, time, status, role) VALUES (?, ?, CURTIME(), ?, ?)";

        try {
            PreparedStatement ps = connectDB.prepareStatement(query);
            ps.setString(1, userId);
            ps.setDate(2, java.sql.Date.valueOf(date));
            ps.setString(3, status);
            ps.setString(4, role);
            ps.executeUpdate();
            System.out.println("✅ Attendance Marked");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

