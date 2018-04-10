/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.students.Entity;

import java.util.List;

/**
 *
 * @author lean
 */
public class StudentService {
    
    private static StudentDao stdDAO;
    
    public StudentService() {
        stdDAO = new StudentDao();
    }
    
    public void persist(Student entity) {
        stdDAO.openCurrentSessionwithTransaction();
        stdDAO.persist(entity);
        stdDAO.closeCurrentSessionwithTransaction();
    }
 
    public void update(Student entity) {
        stdDAO.openCurrentSessionwithTransaction();
        stdDAO.update(entity);
        stdDAO.closeCurrentSessionwithTransaction();
    }
    
    public Student findById(String id) {
        stdDAO.openCurrentSession();
        Student student = stdDAO.findById(id);
        stdDAO.closeCurrentSession();
        return student;
    }
 
    public void delete(String id) {
        stdDAO.openCurrentSessionwithTransaction();
        Student student = stdDAO.findById(id);
        stdDAO.delete(student);
        stdDAO.closeCurrentSessionwithTransaction();
    }
 
    public List<Student> findAll() {
        stdDAO.openCurrentSession();
        List<Student> students = stdDAO.findAll();
//        System.out.println("FOUND" + students.size());//----------------------------
        stdDAO.closeCurrentSession();
        return students;
    }
 
    public void deleteAll() {
        stdDAO.openCurrentSessionwithTransaction();
        stdDAO.deleteAll();
        stdDAO.closeCurrentSessionwithTransaction();
    }
    
    public StudentDao studDao() {
        return stdDAO;
    }

}
