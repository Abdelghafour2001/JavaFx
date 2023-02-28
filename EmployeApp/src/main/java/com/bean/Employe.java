package com.bean;

import com.bean.Sexe;

import java.util.Date;

public class Employe {
    private int matricule;
    private String nom;
    private String prenom;
    private Specs specs;
    private Date dateEmb;
    Sexe sexe;
    public int getMatricule() {
        return matricule;
    }
    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }



    public String getSexe() {
        return String.valueOf(sexe);
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }
    public Employe(){}
    public  Employe(int m,String n, String p,Specs s,Date d,Sexe se){
        this.matricule=m;
        this.nom=n;
        this.prenom=p;
        this.specs=s;
        this.setDateEmb(d);
        this.sexe=se;
    }

    public String  toString(){
        return "Matricule de l'employe est = "+this.matricule;
    }

    public Specs getSpecs() {
        return specs;
    }

    public void setSpecs(Specs specs) {
        this.specs = specs;
    }

    public Date getDateEmb() {
        return dateEmb;
    }

    public void setDateEmb(Date dateEmb) {
        this.dateEmb = dateEmb;
    }
}
