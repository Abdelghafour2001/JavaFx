package com.ui.employeapp;

import com.bean.Employe;
import com.bean.ServiceEmploye;
import com.bean.Sexe;
import com.bean.Specs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    ServiceEmploye service = new ServiceEmploye();
    ArrayList<Employe> allEmps = new ArrayList<Employe>();
    @FXML
    private TitledPane gestionemp;
    @FXML
    private TableView<Employe> gestTableView;
    @FXML
    private TableView<Employe> searchTableView;
    @FXML
    private TextField nom;
    @FXML
    private CheckBox checkMat;
    @FXML
    private CheckBox checkSpec;
    @FXML
    private CheckBox checkNom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField searchNom;
    @FXML
    private TextField searchMat;
    @FXML
    private TextField matricule;
    @FXML
    private DatePicker dateEmb;
    @FXML
    private DatePicker date1;
    @FXML
    private DatePicker date2;
    @FXML
    private TitledPane searchdate;
    @FXML
    private RadioButton sexeH;
    @FXML
    private ToggleGroup sexe;
 @FXML
 private MenuItem nemp;
    @FXML
    private MenuItem closeAll;
    @FXML
    private ComboBox specs;
    @FXML
    private ComboBox searchspecs;
    @FXML
    void gestEmpShowAction(ActionEvent event) {
        gestionemp.setVisible(true);
        searchdate.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchdate.setVisible(false);
        gestionemp.setVisible(false);

        specs.getItems().addAll(Specs.values());
        searchspecs.getItems().addAll(Specs.values());
        try {
            ArrayList<Employe> emps = service.findAll();
            populateTableView(emps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAction(ActionEvent actionEvent) throws SQLException {
try{
        Employe e = new Employe();
        e.setNom(nom.getText());
        e.setSexe(getGender());
        e.setPrenom(prenom.getText());
        int mat = Integer.parseInt(matricule.getText());
        e.setMatricule(mat);
        LocalDate ld = dateEmb.getValue();
        Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        e.setDateEmb(date);
        e.setSpecs(getSpec());

        if(service.create(e)){
            showAlertWithHeaderText("Ajout Employe","Ajouté avec succes.");

            ArrayList<Employe> emps = service.findAll();
            populateTableView(emps);

        }else{
            showAlertWithHeaderText("Ajout Employe","Some Error Occured, Check all your inputs.");
        }}catch (Exception e){
    showAlertWithHeaderText("Ajout Employe",e.getMessage());
        }
    }
    private void showAlertWithHeaderText(String title,String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Results:"+ msg);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    public void deleteAction(ActionEvent actionEvent) {
        try {
            int mat = Integer.parseInt(matricule.getText());
            if(service.delete(mat)){
                showAlertWithHeaderText("Delete Employe","Supprimé avec succes.");

                ArrayList<Employe> emps = service.findAll();
                populateTableView(emps);

            }else{
                showAlertWithHeaderText("Delete Employe","Some Error Occured, Check all your inputs.");
            }
        }catch (Exception e){
            showAlertWithHeaderText("Delete Employe",e.getMessage());
        }

    }

    public void modifyAction(ActionEvent actionEvent) {
        try{
            Employe e = new Employe();
            e.setNom(nom.getText());
            e.setSexe(getGender());
            e.setPrenom(prenom.getText());
            int mat = Integer.parseInt(matricule.getText());
            e.setMatricule(mat);
            LocalDate ld = dateEmb.getValue();
            Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            e.setDateEmb(date);
            e.setSpecs(getSpec());
            if(service.modify(e)){
                showAlertWithHeaderText("Update Employe","Modifié avec succes.");
                ArrayList<Employe> emps = service.findAll();
                populateTableView(emps);
            }else{
                showAlertWithHeaderText("Update Employe","Some Error Occured, Check all your inputs.");
            }}catch (Exception e){
            showAlertWithHeaderText("Update Employe",e.getMessage());
        }

    }
    public Sexe getGender(){
        Sexe gen;
        if(sexeH.isSelected()){
            gen= Sexe.M;
        }else{
            gen=Sexe.F;
        }
        return gen;
    }
    public Specs getSpec(){
        return (Specs) specs.getValue();
    }
    public void populateTableView(ArrayList<Employe> emps) throws SQLException {
        try{
            ObservableList<Employe> data = FXCollections.<Employe>observableArrayList();
            data.addAll(emps);
            gestTableView.setItems(data);
        }catch (Exception e){
            showAlertWithHeaderText("All Employees",e.getMessage());
        }

    }

    public void rechercherAction(ActionEvent actionEvent) {
        try {
            ArrayList<Employe> empss = new ArrayList<Employe>();
            if(checkMat.isSelected() && checkNom.isSelected() && checkSpec.isSelected()){
                empss = service.findByCritere(Integer.parseInt(searchMat.getText()), (Specs) searchspecs.getValue(),searchNom.getText());
            }else if(!checkMat.isSelected() && checkNom.isSelected() && checkSpec.isSelected()){
                empss = service.findByCritere(-1, (Specs) searchspecs.getValue(),searchNom.getText());
            }
            else if (!checkMat.isSelected() && !checkNom.isSelected() && checkSpec.isSelected()) {
                empss = service.findByCritere(-1, (Specs) searchspecs.getValue(),null);
            } else if (!checkMat.isSelected() && checkNom.isSelected() && !checkSpec.isSelected()) {
                empss = service.findByCritere(-1,null,searchNom.getText());
            }else if (checkMat.isSelected() && !checkNom.isSelected() && !checkSpec.isSelected()) {
                empss = service.findByCritere(Integer.parseInt(searchMat.getText()),null,null);
            }
            else {
                empss = service.findAll();
            }
            populateTableView(empss);

        }catch (Exception e){
            showAlertWithHeaderText("Employees by Criteria",e.getMessage());

        }

    }

    public void closeAll(ActionEvent e) {
        System.exit(0);
    }

    public void searchBydateAction(ActionEvent actionEvent) {
        gestionemp.setVisible(false);
        searchdate.setVisible(true);
    }
    public void populatesearchTableView(ArrayList<Employe> emps) throws SQLException {
        try{
            ObservableList<Employe> data = FXCollections.<Employe>observableArrayList();
            data.addAll(emps);
            searchTableView.setItems(data);
        }catch (Exception e){
            showAlertWithHeaderText("All Employees",e.getMessage());
        }

    }

    public void searchDateAction(ActionEvent actionEvent) throws SQLException {
        try {
            LocalDate ld = date1.getValue();
            Instant instant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
            Date date1 = Date.from(instant);
            LocalDate ld2 = date2.getValue();
            Instant instant2 = Instant.from(ld2.atStartOfDay(ZoneId.systemDefault()));
            Date date2 = Date.from(instant2);
            ArrayList<Employe> empss = new ArrayList<Employe>();
            empss= service.findBetweenDate(date1,date2);
            populatesearchTableView(empss);
        }catch (Exception e){
            showAlertWithHeaderText("Employees by Date",e.getMessage());
        }
    }
}