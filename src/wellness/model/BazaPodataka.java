/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
/**
 *
 * @author MK
 */
public class BazaPodataka extends Konekcija {
   private Statement upit;
    private PreparedStatement execUpit;
    
    public static final BazaPodataka DB = new BazaPodataka();

    public BazaPodataka () {
        super();
        try {
            this.upit = this.connection.createStatement();
            this.upit.execute("SET NAMES utf8");
        } catch (SQLException ex) {
            System.out.println ("Nastala je greška priliko izvršavanja upita " + ex.getMessage());
        }
    }

    public ResultSet select (String sql) {
        try {
            this.upit = this.connection.createStatement();
            return this.upit.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println ("Nastala je greška priliko izvršavanja upita " + e.getMessage());
            return null;
        }
    }

    public PreparedStatement exec (String sql) {
        try {
            
            this.execUpit = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return this.execUpit;
        } catch (SQLException e) {
            System.out.println("Nisam uspio izvršiti upit " + e.getMessage());
        }
        return null;
    } 
    
     public PreparedStatement prepare (String sql) {
        try {
            return this.connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("Nastala je greška prilikom izvršavanja upit");
        }
        return null;
    }
}
