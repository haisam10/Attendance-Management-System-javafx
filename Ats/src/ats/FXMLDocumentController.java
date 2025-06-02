package ats;

import java.io.IOException;
import java.net.URL;
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

/**
 *
 * @author mdhhd
 */
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

    // Admin Credentials
    private final String[][] adminCredentials = {
        {"admin", "admin"},
        {"adm","adm"},
        {"1234", "demo"},
        {"223071114", "Haisam"}
    };
    // student Credentials
    private final String[][] studentCredentials = {
        {"student", "student"},
        {"1234","pass1234"},
        {"stu", "stu"},
        {"223071114", "Haisam"}
    };
    // Teacher Credentials
    private final String[][] teacherCredentials = {
        {"teacher","teacher"},
        {"tec", "tec"},
        {"teacher", "pass2"},
        {"223071114", "Haisam"}
    };
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        whoSelect.setItems(list);
        whoSelect.setValue("Who are you?");
    }

    @FXML
    private void u_ID_pass_Submit(ActionEvent event) {
        String UserId = userID.getText();
        String UserPassword = userPass.getText();
        String selectedRole = whoSelect.getValue();

        System.out.println("User ID: " + UserId);
        System.out.println("User Password: " + UserPassword);
        System.out.println("AST Select: " + selectedRole);

        boolean isValid = false;
        String fxmlFile = null;
        
        // all file calling
        switch (selectedRole) {
            case "Admin" -> {
                isValid = checkCredentials(UserId, UserPassword, adminCredentials);
                fxmlFile = "admin.fxml";
            }
            case "Student" -> {
                isValid = checkCredentials(UserId, UserPassword, studentCredentials);
                fxmlFile = "student.fxml";
            }
            case "Teacher" -> {
                isValid = checkCredentials(UserId, UserPassword, teacherCredentials);
                fxmlFile = "teacher.fxml";
            }
        }

        if (isValid && fxmlFile != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Cast to correct controller based on role
                if (selectedRole.equals("Admin")) {
                    AdminController admin_ID = loader.getController();
                    admin_ID.User_Id(UserId);
                    admin_ID.Candidate(selectedRole);
                } 
                else if (selectedRole.equals("Student")) {
                    StudentController student_ID = loader.getController();
                    student_ID.User_Id(UserId);
                    student_ID.Candidate(selectedRole);
                }
                else if (selectedRole.equals("Teacher")) {
                    TeacherController teacher_ID = loader.getController();
                    teacher_ID.User_Id(UserId);
                    teacher_ID.Candidate(selectedRole);
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(selectedRole + " Panel");
                stage.show();

                // Close login window
                ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
            } catch (IOException e) {
                System.err.println("Failed to load " + fxmlFile + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid ID or Password for " + selectedRole);
            alart_wrong_user_pass.setText("Wrong ID or Password!");
        }
    }

    private boolean checkCredentials(String id, String pass, String[][] credentials) {
        for (String[] pair : credentials) {
            if (pair[0].equals(id) && pair[1].equals(pass)) {
                return true;
            }
        }
        return false;
    }
}
