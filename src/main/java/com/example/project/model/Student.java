package com.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="student")
public class Student {
    @Id
    private int idstudent; // 主键，对应数据库中的idstudent字段
    private String studentNumber; // 学号，对应数据库中的学号字段
    private String password; // 密码，对应数据库中的密码字段

    // 无参构造函数
    public Student() {
    }

    // 全参构造函数
    public Student(int idstudent, String studentNumber, String password) {
        this.idstudent = idstudent;
        this.studentNumber = studentNumber;
        this.password = password;
    }

    // getter 和 setter 方法
    public int getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(int idstudent) {
        this.idstudent = idstudent;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Student{" +
                "idstudent=" + idstudent +
                ", studentNumber='" + studentNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
