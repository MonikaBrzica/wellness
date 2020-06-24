/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.model;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MK
 */
public class Konekcija {
    private String host;
    private String username;
    private String password;
    private String db;

    protected java.sql.Connection connection;

    public Konekcija () {
        this.host = "localhost";
        this.username = "root";
        this.password = "";
        this.db = "wellness_puj";
        this.connect();
    }

    public Konekcija (String host, String username, String password, String db) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.db = db;
        this.connect();
    }

    public void connect () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://"+this.host+"/"+this.db+"?"
                    + "user="+this.username+"&password="+this.password);
        } catch (ClassNotFoundException e) {
            System.out.println ("Class for connection to MYSQL can not be found.");
        } catch (SQLException e) {
            System.out.println ("Couldn't connect to database");
        }
    }

    public void disconnect () {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println ("Connection couldn't be disconnected.");
        }
    }

}
