/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author MK
 */
public class Rezervacije {
    SimpleIntegerProperty id_rezervacije = new SimpleIntegerProperty();
    SimpleStringProperty datum = new SimpleStringProperty();
    SimpleFloatProperty cijena = new SimpleFloatProperty();
    SimpleStringProperty id_usluge = new SimpleStringProperty();
    SimpleStringProperty id_korisnika = new SimpleStringProperty();
    SimpleStringProperty vrijeme = new SimpleStringProperty();
    SimpleStringProperty id_kategorije = new SimpleStringProperty();
    
    public Rezervacije(int id_rezervacije, Date datum, float cijena, String id_usluge,  String id_korisnika, String vrijeme, String id_kategorije){
        this.id_rezervacije = new SimpleIntegerProperty(id_rezervacije);
        this.datum = new SimpleStringProperty(String.valueOf(datum));
        this.cijena = new SimpleFloatProperty(cijena);
        this.id_usluge = new SimpleStringProperty(id_usluge); 
        this.id_korisnika = new SimpleStringProperty(id_korisnika); 
        this.vrijeme = new SimpleStringProperty(vrijeme);
        this.id_kategorije = new SimpleStringProperty(id_kategorije); 
    }
    
    public int getId_Rezervacije(){ return id_rezervacije.get();}
    public String getDatum(){ return datum.get();}
    public float getCijena(){ return cijena.get();}
    public String getId_usluge(){ return id_usluge.get();}
    public String getKorisnika(){ return id_korisnika.get();}
    public String getVrijeme(){ return vrijeme.get();}
    public String getKategorija(){ return id_kategorije.get();}
    
    public void setDatum(String datum) {
        this.datum = new SimpleStringProperty(datum);
    }
    public void setCijena(float cijena) {
        this.cijena = new SimpleFloatProperty(cijena);
    }
    public void setId_usluge(String id_usluge) {
        this.id_usluge = new SimpleStringProperty(id_usluge);
    }
    public void setId_Korisnika(String id_korisnika) {
        this.id_korisnika = new SimpleStringProperty(id_korisnika);
    }
    
    public static ObservableList<Rezervacije> rezervacijeLista () {
        ObservableList<Rezervacije> list = FXCollections.observableArrayList();
         BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("SELECT rezervacije.id_rezervacije, usluga.datum, rezervacije.cijena, usluga.naziv, korisnik.e_mail, usluga.vrijeme, kategorija.naziv FROM rezervacije JOIN usluga ON usluga.id_usluge = rezervacije.id_usluge JOIN korisnik ON korisnik.id_korisnika = rezervacije.id_korisnika JOIN kategorija ON kategorija.id_kategorije = usluga.id_kategorije");
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Rezervacije(rs.getInt(1), rs.getDate(2), rs.getFloat(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7))); 
            }
        } catch (SQLException ex) {
            System.out.println("Greška pri iteriranju: " + ex.getMessage());
        }
        return list;
    }
   public static ObservableList<Rezervacije> mojeRezervacijeLista () {
        ObservableList<Rezervacije> list = FXCollections.observableArrayList();
         BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("SELECT rezervacije.id_rezervacije, usluga.datum, rezervacije.cijena, usluga.naziv, korisnik.e_mail, usluga.vrijeme, kategorija.naziv FROM rezervacije JOIN usluga ON usluga.id_usluge = rezervacije.id_usluge JOIN korisnik ON korisnik.id_korisnika = usluga.id_korisnika JOIN kategorija ON kategorija.id_kategorije = usluga.id_kategorije WHERE rezervacije.id_korisnika = ?");
        try {
            ps.setInt(1, PrijavljeniKorisnik.id_korisnika);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Rezervacije(rs.getInt(1), rs.getDate(2), rs.getFloat(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7))); 
            }
        } catch (SQLException ex) {
            System.out.println("Greška pri iteriranju: " + ex.getMessage());
        }
        return list;
    }
   
   public static boolean otkaziRezervaciju(int idRezervacije){
         BazaPodataka db = new BazaPodataka();
         
         PreparedStatement ps = db.exec("SELECT id_usluge FROM rezervacije WHERE id_rezervacije =?");
         try {
            ps.setInt(1, idRezervacije);
            ps.execute();
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int idUsluge = rs.getInt(1);
                
                ps = db.exec("DELETE FROM rezervacije WHERE id_rezervacije = ?");
         
                try {
                    ps.setInt(1, idRezervacije);
                    ps.execute();
                    ps = db.exec("UPDATE usluga SET br_termina = br_termina + 1 WHERE id_usluge = ?");
                    
                    try {
                    ps.setInt(1, idUsluge);
                    ps.execute();
                    return true;
                    } catch (SQLException ex) {
                        Logger.getLogger(Rezervacije.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    } 

                } catch (SQLException ex) {
                    Logger.getLogger(Rezervacije.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }  
            } 
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Rezervacije.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
   }

public static boolean zavrsiRezervaciju(int id){
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("DELETE FROM rezervacije WHERE id_rezervacije = ?");
        
        try {
            ps.setInt(1, id);
            System.out.println(ps);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Termini.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}