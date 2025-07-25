package ats;

import javafx.beans.property.*;

public class StudentAttendance {
    private final StringProperty studentId;
    private final StringProperty name;
    private final BooleanProperty present;

    public StudentAttendance(String studentId, String name, boolean present) {
        this.studentId = new SimpleStringProperty(studentId);
        this.name = new SimpleStringProperty(name);
        this.present = new SimpleBooleanProperty(present);
    }

    public String getStudentId() {
        return studentId.get();
    }

    public String getName() {
        return name.get();
    }

    public boolean isPresent() {
        return present.get();
    }

    public BooleanProperty presentProperty() {
        return present;
    }
}
