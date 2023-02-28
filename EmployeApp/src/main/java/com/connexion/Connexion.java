package com.connexion;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Connexion {
    private Connection connect = null;

    private Statement statement = null;

    public Connexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/employetp";
            this.connect = (Connection) DriverManager.getConnection(dbURL,"root","");
            this.statement = this.connect.createStatement();
            System.out.println("DB connexion OK");

        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error "+e);
        }
    }
    public Connection getConnect() {
        return connect;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    protected PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement ps = (PreparedStatement) this.connect.prepareStatement(sql);
        return ps;
    }
}
