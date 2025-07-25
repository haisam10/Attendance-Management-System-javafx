/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ats;

/**
 *
 * @author mdhhd
 */
public class Student {
    private String name;
    private String studentId;
    private String department;
    private String email;
    private String phone;
    private String section;

    public Student(String name, String studentId, String department, String email, String phone, String section) {
        this.name = name;
        this.studentId = studentId;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.section = section;
    }

    public String getName() { return name; }
    public String getStudentId() { return studentId; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getSection() { return section; }
}
