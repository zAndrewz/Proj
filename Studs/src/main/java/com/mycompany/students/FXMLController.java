package com.mycompany.students;

import com.mycompany.students.Entity.Student;
import com.mycompany.students.Entity.StudentService;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {
    
    StudentService stdserv = new StudentService();
    private ObservableList<Student> usersData = FXCollections.observableArrayList();
    
    int TOP_ID = 0;
    
    @FXML
    private TableView tableStudents;
    
    @FXML
    private Button insertButton; 
    
    @FXML
    private Button deleteButton; 
    
    @FXML
    private TextField idToDel;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private TableColumn idColumn;
    
    @FXML
    private TableColumn nameColumn;
    
    @FXML
    private TableColumn surnameColumn;
    
    @FXML
    private TableColumn facultyColumn;
    
    @FXML
    private TableColumn receiptDateCol;
    
    @FXML
    private TextArea nameText;
    
    @FXML
    private TextArea surnameText;
    
    @FXML
    private TextArea facultyText;
    
    @FXML
    private DatePicker receiptDDate;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();
        buttons();
        
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("StudentName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("StudentSurname"));
        facultyColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("Faculty"));
        receiptDateCol.setCellValueFactory(new PropertyValueFactory<Student, Date>("ReceiptDate"));
        
    }    
    
    private void initData() {
        List<Student> list = stdserv.findAll();
        
        if(list == null)
            return;
        for(Student s : list)
        {
            if(Integer.parseInt(s.getId()) > TOP_ID)
            TOP_ID = Integer.parseInt(s.getId());
        }
        
        for(Student s : list)
        {
            String id = s.getId();
            String name = s.getStudentName();
            String surname = s.getStudentSurname();
            String facult = s.getFaculty();
            Date dd = s.getReceiptDate();
            System.out.println("-----------------" + id + " " + name + " " + surname + " " + facult + " " + dd);
            usersData.add(new Student(id,name,surname,facult,dd));
        }
        
        usersData.sorted();
        tableStudents.setItems(usersData);
    }
    
    private void buttons() {
        insertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameText.getText();
                String surname = surnameText.getText();
                String faculty = facultyText.getText();
                Date date = java.sql.Date.valueOf(receiptDDate.getValue());
                String id = TOP_ID+1 + "";
                
                Student stud = new Student(id,name,surname,faculty, date);
                stdserv.persist(stud);
                usersData.add(stud);
                tableStudents.setItems(usersData);
            } 
        });
        
        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usersData.clear();
                List <Student> studs = stdserv.findAll();
                List tabData = tableStudents.getItems();
                
                for (int i = 0; i < tabData.size(); i++) {
                    tableStudents.getItems().remove(i);
                }
                
                usersData.addAll(studs);
                tableStudents.setItems(usersData);
            } 
        });
        
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = idToDel.getText();
                List <Student> list = stdserv.findAll();
                
                Pattern pat = Pattern.compile("[^0-9\\+]");
                Matcher m = pat.matcher(s);
                if(m.matches())
                return; 
                
                if(!m.matches()) {
                int [] ids = new int[list.size()];
                String check;
                for (int i = 0; i < ids.length; i++) {
                    check = list.get(i).getId();
                    if(check.equals(s)){ 
                    ids[i] = Integer.parseInt(check);
                    stdserv.delete(ids[i] + "");
                    break;
                    }
                }
                
                int selectedIndex = tableStudents.getSelectionModel().getSelectedIndex();
                if(selectedIndex > 0) {
                tableStudents.getItems().remove(selectedIndex);
                stdserv.delete(selectedIndex + ""); }
            } }
        });
}
}
