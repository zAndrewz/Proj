/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.students.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
/**
 *
 * @author lean
 */
@Entity
@Table(name = "STUDENT")
public class Student {
   
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
 //   int id;
    @Column(name = "ID")
    private String id;
    @Column(name = "STUDENTNAME")
    private String studentName;
    @Column(name = "STUDENTSURNAME")
    private String studentSurname;
    @Column(name = "FACULTY")
    private String faculty;
    @Column(name = "RECEIPTDATE")
    private Date ReceiptDate;

    public Student() {
    }

    public Student(String id, String studentName, String studentSurname, String faculty, Date ReceiptDate) {
        this.id = id;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.faculty = faculty;
        this.ReceiptDate = ReceiptDate;
    }

    public Student(String studentName, String studentSurname, String faculty, Date ReceiptDate) {
        this.studentName = studentName;
        this.studentSurname = studentSurname;
        this.faculty = faculty;
        this.ReceiptDate = ReceiptDate;
    }
    
     public String getId() {
        return id;
    }

    public void setId(String ids) {
        this.id = id;
    }
     
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSurname() {
        return studentSurname;
    }

    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Date getReceiptDate() {
        return ReceiptDate;
    }

    public void setReceiptDate(Date ReceiptDate) {
        this.ReceiptDate = ReceiptDate;
    }
     
}
