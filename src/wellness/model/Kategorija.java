/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author MK
 */
public class Kategorija {
    SimpleIntegerProperty id_kategorije = new SimpleIntegerProperty();
    SimpleStringProperty naziv = new SimpleStringProperty();
    SimpleBooleanProperty aktivnost = new SimpleBooleanProperty();
    
    public Kategorija(int id_kategorije, String naziv, boolean aktivnost){
        this.id_kategorije = new SimpleIntegerProperty(id_kategorije);
        this.naziv = new SimpleStringProperty(naziv);
        this.aktivnost = new SimpleBooleanProperty(aktivnost);
    }
    public int getId_Kategorije(){ return id_kategorije.get();}
    public String getNaziv(){ return naziv.get();}
    public boolean isAktivnost() { return aktivnost.get();}

    public void setId_kategorije(SimpleIntegerProperty id_kategorije) {
        this.id_kategorije = id_kategorije;
    }

    public void setNaziv(SimpleStringProperty naziv) {
        this.naziv = naziv;
    }

    public void setAktivnost(SimpleBooleanProperty aktivnost) {
        this.aktivnost = aktivnost;
    }
    
    public static ObservableList<Kategorija> listaKategorija() {
        ObservableList<Kategorija> list = FXCollections.observableArrayList();
        
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("SELECT * FROM kategorija WHERE aktivnost=true");
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Kategorija(rs.getInt("id_kategorije"), rs.getString("naziv"), rs.getBoolean("aktivnost")));
            }
        } catch (SQLException ex) {
            System.out.println("Greška pri iteriranju: " + ex.getMessage());
        }
        return list;
    }
    
    public static boolean dodajKategoriju(String nazivKategorije){
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("INSERT INTO kategorija (naziv, aktivnost) VALUES (?, 1)");
        
        try {
            ps.setString(1, nazivKategorije);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public static boolean urediKategoriju(Kategorija kategorija){
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("UPDATE kategorija SET naziv = ? WHERE id_kategorije = ?");
        
            try {
            ps.setString(1, kategorija.getNaziv());
            ps.setInt(2, kategorija.getId_Kategorije());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    
    }
    
    public static boolean obrisiKategoriju(int idKategorije){
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("DELETE FROM kategorija WHERE id_kategorije = ?");
        
        try {
            ps.setInt(1, idKategorije);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
