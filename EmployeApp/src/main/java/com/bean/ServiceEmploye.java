package com.bean;

import com.mysql.jdbc.PreparedStatement;
import com.connexion.Connexion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServiceEmploye extends Connexion {
    private Statement statement = null;
    private ResultSet resultset = null;
    public boolean create(Employe e) throws SQLException {
        String sql = "INSERT INTO emp (matricule, nom, prenom, dateemb,spec,sexe) VALUES (?, ?, ?, ?,?,?)";
        PreparedStatement statement = this.prepareStatement(sql);
        statement.setInt(1, e.getMatricule());
        statement.setString(2, e.getNom());
        statement.setString(3, e.getPrenom());
        java.sql.Date sqlDate = new java.sql.Date( e.getDateEmb().getTime());
        statement.setDate(4,sqlDate);
        statement.setString(5,String.valueOf(e.getSpecs()) );
        statement.setString(6, e.getSexe());
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new Employee was inserted successfully!");
            return true;
        }
        return false;
    }
    public Employe findOne(int mat) throws SQLException {
        ArrayList<Employe> emps = new ArrayList<Employe>();
        emps=this.findAll();
        for (Employe emp : emps) {
                if (emp.getMatricule()==mat) {
                    return emp;
                }
            }
        return null;
    }
    public boolean delete(int m) throws SQLException {
        String sql = "DELETE FROM emp WHERE matricule=?";
        PreparedStatement statement = this.prepareStatement(sql);
        statement.setInt(1, m);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
            return true;
        }
        return false;
    }
    public boolean modify(Employe e) throws SQLException {
        String sql = "UPDATE emp SET spec=?,nom=?,prenom=? where matricule = ?";
        PreparedStatement statement = this.prepareStatement(sql);
        statement.setString(1, String.valueOf(e.getSpecs()));
        statement.setString(2, e.getNom());
        statement.setString(3, e.getPrenom());
        statement.setInt(4, e.getMatricule());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
            return  true;
        }
        return false;
    }
    public ArrayList<Employe> findAll() throws SQLException {
        ArrayList<Employe> emps = new ArrayList<Employe>();
        String sql = "SELECT * FROM emp";
        statement = this.getStatement();
        System.out.println(sql);
        this.resultset = statement.executeQuery(sql);
        int count = 0;
        while(resultset.next()){
            Employe employe = new Employe();
            employe.setMatricule(resultset.getInt("Matricule"));
            employe.setNom(resultset.getString("Nom"));
            employe.setPrenom(resultset.getString("Prenom"));
            employe.setSexe(Sexe.valueOf(resultset.getString("Sexe")));
            employe.setSpecs(Specs.valueOf(resultset.getString("spec")));
            employe.setDateEmb(resultset.getDate("dateemb"));
            String output = "User #%d: %s - %s ";
            System.out.println(String.format(output, ++count, employe.getNom(), employe.getPrenom()));
            emps.add(employe);
        }
        return emps;
    }
    public ArrayList<Employe> findByCritere(int mat, Specs spec, String nom) throws SQLException {
        ArrayList<Employe> emps= new ArrayList<Employe>();
        emps = this.findAll();
        ArrayList<Employe> newL= new ArrayList<Employe>();
        for (Employe employe : emps) {
            if ((spec == null || employe.getSpecs() == spec)
                    && (nom == null || employe.getNom().equals(nom))
                    && (mat == -1 || employe.getMatricule() == mat)) {
                newL.add(employe);
            }
        }
        return newL;
    }
    public ArrayList<Employe> findBetweenDate(Date d1, Date d2) throws SQLException {
        ArrayList<Employe> emps= new ArrayList<Employe>();
        emps = this.findAll();
        ArrayList<Employe> newL= new ArrayList<Employe>();
        for (Employe emp: emps) {
            if (emp.getDateEmb().after(d1) && emp.getDateEmb().before(d2)) {
                newL.add(emp);
            }
        }
        return newL;
    }
}
