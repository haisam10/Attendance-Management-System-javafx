package ats;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField userID;
    @FXML
    private TextField userPass;
    @FXML
    private ChoiceBox<String> whoSelect;
    @FXML
    private Label alart_wrong_user_pass;
    @FXML
    private Button u_ID_pass_Submit;

    ObservableList<String> list = FXCollections.observableArrayList("Student", "Teacher", "Admin");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        whoSelect.setItems(list);
        whoSelect.setValue("Student"); // Set a default value
    }

    @FXML
    private void u_ID_pass_Submit(ActionEvent event) {
        String UserId = userID.getText();
        String UserPassword = userPass.getText();
        String selectedRole = whoSelect.getValue();

        if (UserId.isEmpty() || UserPassword.isEmpty() || selectedRole == null) {
            alart_wrong_user_pass.setText("Please fill all fields.");
            return;
        }

        System.out.println("User ID: " + UserId);
        System.out.println("User Password: " + UserPassword);
        System.out.println("Selected Role: " + selectedRole);

        boolean isValid = checkCredentialsFromDB(UserId, UserPassword, selectedRole);
        String fxmlFile = null;

        switch (selectedRole) {
            case "Admin" -> fxmlFile = "admin.fxml";
            case "Student" -> fxmlFile = "student.fxml";
            case "Teacher" -> fxmlFile = "teacher.fxml";
        }

        if (isValid && fxmlFile != null) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        switch (selectedRole) {
            case "Admin" -> {
                AdminController admin_ID = loader.getController();
                admin_ID.User_Id(UserId);
                admin_ID.Candidate(selectedRole);
            }
            case "Teacher" -> {
                TeacherController teacher_ID = loader.getController();
                teacher_ID.User_Id(UserId);
                teacher_ID.Candidate(selectedRole);
            }
            case "Student" -> {
                StudentController student_ID = loader.getController();
                student_ID.User_Id(UserId);
                student_ID.Candidate(selectedRole);
            }
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(selectedRole + " Panel");
        stage.show();

        // Close login window
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

    } catch (IOException e) {
        System.err.println("❌ Failed to load " + fxmlFile + ": " + e.getMessage());
    }
}
 else {
            System.out.println("Invalid ID or Password for " + selectedRole);
            alart_wrong_user_pass.setText("Wrong ID or Password!");
        }
    }

    private boolean checkCredentialsFromDB(String id, String pass, String role) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT * FROM users WHERE user_id = ? AND password = ? AND role = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pass);
            preparedStatement.setString(3, role);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Match found

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
