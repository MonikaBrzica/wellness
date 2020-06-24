/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Termini {
    SimpleIntegerProperty id_usluge = new SimpleIntegerProperty();
    SimpleStringProperty naziv = new SimpleStringProperty();
    SimpleStringProperty datum = new SimpleStringProperty();
    SimpleStringProperty vrijeme = new SimpleStringProperty();
    SimpleIntegerProperty br_termina = new SimpleIntegerProperty();
    SimpleFloatProperty cijena = new SimpleFloatProperty();
    SimpleStringProperty id_kategorije = new SimpleStringProperty();
    SimpleIntegerProperty id_korisnika = new SimpleIntegerProperty();
    private boolean aktivnost;
    
   
    public Termini(int id_usluge, String naziv, String datum, String vrijeme, int br_termina, float cijena, String id_kategorije, boolean aktivnost, int id_korisnika) {
        this.id_usluge = new SimpleIntegerProperty (id_usluge);
        this.naziv = new SimpleStringProperty (naziv);
        this.datum = new SimpleStringProperty (datum);
        this.vrijeme= new SimpleStringProperty(vrijeme);
        this.br_termina = new SimpleIntegerProperty (br_termina);
        this.cijena= new SimpleFloatProperty(cijena);
        this.id_kategorije = new SimpleStringProperty(id_kategorije);
        this.aktivnost = aktivnost;
        this.id_korisnika = new SimpleIntegerProperty(id_korisnika);
        }
     
   public String getNaziv () {
        return naziv.get();
    }
    public void setNaziv(String naziv) {
        this.naziv = new SimpleStringProperty(naziv);
    }
    public String getDatum () {
        return datum.get();
    }
    public void setDatum(String datum) {
        this.datum = new SimpleStringProperty(datum);
    }
    public String getVrijeme () {
        return vrijeme.get();
    }
    public void setVrijeme(String vrijeme) {
        this.vrijeme = new SimpleStringProperty(vrijeme);
    }
    public Integer getBr_termina () {
        return br_termina.get();
    }
    public void setBr_termina(Integer br_termina) {
        this.br_termina = new SimpleIntegerProperty(br_termina);
    }
    public Float getCijena () {
        return cijena.get();
    }
    public void setCijena(Float cijena) {
        this.cijena = new SimpleFloatProperty(cijena);
    }
    public String getId_kategorije() { 
        return id_kategorije.get();}
    
    public void setId_kategorije(String id_kategorije) {
        this.id_kategorije = new SimpleStringProperty(id_kategorije);
    }  
    public boolean isAktivnost(){ 
        return aktivnost;
    }
    public void setAktivnost(boolean aktivnost){
        this.aktivnost = aktivnost;
    }
    public int getId () {
        return id_usluge.get();
    }
        
    public static ObservableList<Termini> listaTermina() {
       ObservableList<Termini> list = FXCollections.observableArrayList();
       BazaPodataka db = new BazaPodataka();
       PreparedStatement ps = db.exec("SELECT usluga.id_usluge, usluga.naziv, usluga.datum, usluga.vrijeme, usluga.br_termina, usluga.cijena, kategorija.naziv AS naziv_kategorije, usluga.aktivnost, usluga.id_korisnika FROM usluga JOIN kategorija ON kategorija.id_kategorije = usluga.id_kategorije WHERE usluga.aktivnost=true AND br_termina > 0");
       try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Termini(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getFloat(6), rs.getString(7), rs.getBoolean(8), rs.getInt(9)));
            }
        } catch (SQLException ex) {
            System.out.println("Greška pri iteriranju: " + ex.getMessage());
        }
        return list; 
    }
    
    
    //choicebox kategorija, datepicker, potvrdi, odjavi se
    
    public static boolean dodajTermin (Termini dtermini){
	BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("INSERT INTO usluga (naziv, datum, vrijeme, br_termina, cijena, id_kategorije, aktivnost, id_korisnika) VALUES(?,?,?,?,?,(SELECT id_kategorije FROM kategorija WHERE naziv = ?),?, ?)");
       
        try{
                        ps.setString(1, dtermini.getNaziv());
                        ps.setString(2, dtermini.getDatum());
                        ps.setString(3, dtermini.getVrijeme());
                        ps.setInt(4, dtermini.getBr_termina());
                        ps.setFloat(5, dtermini.getCijena());
                        ps.setString(6, dtermini.getId_kategorije());
                        ps.setBoolean(7, true);
                        ps.setInt(8, PrijavljeniKorisnik.id_korisnika);
                        System.out.println(ps);
                        ps.executeUpdate();
			System.out.println("Uspješno dodano u bazu.");
			return true;

		} catch (SQLException ex) {
           return false; 
        }
    }
    
    public static boolean urediTermin(Termini termini){
         BazaPodataka db = new BazaPodataka();
       PreparedStatement ps = db.exec("UPDATE usluga SET naziv = ?, datum = ?, vrijeme = ?, br_termina = ?, cijena = ?, id_kategorije=(SELECT id_kategorije FROM kategorija WHERE naziv=?), aktivnost=? WHERE id_usluge = ?");
       
       try {
           
            ps.setString(1, termini.getNaziv());
            ps.setString(2, termini.getDatum());
            ps.setString(3, termini.getVrijeme());
            ps.setInt(4, termini.getBr_termina());
            ps.setFloat(5, termini.getCijena());
            ps.setString(6, termini.getId_kategorije()); 
            ps.setBoolean(7, true);
            ps.setInt(8, termini.getId());
            System.out.println(ps);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
           return false; 
        }
    }
    
    public static boolean rezervirajTermin(int idTermina){
         BazaPodataka db = new BazaPodataka();
         
         PreparedStatement ps = db.exec("SELECT * FROM rezervacije WHERE id_korisnika = ? AND id_usluge = ?");
         
          try {
            ps.setInt(1, PrijavljeniKorisnik.id_korisnika);
            ps.setInt(2, idTermina);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
            else{
                ps = db.exec("INSERT INTO rezervacije (cijena, id_usluge, id_korisnika) VALUES ((SELECT cijena FROM usluga WHERE id_usluge = ?), ?, ?)");
                try {
                       ps.setInt(1, idTermina);
                       ps.setInt(2, idTermina);
                       ps.setInt(3, PrijavljeniKorisnik.id_korisnika);
                       ps.executeUpdate();
                       ps = db.exec("UPDATE usluga SET br_termina = br_termina - 1 WHERE id_usluge = ?");
                       try {
                            ps.setInt(1, idTermina);
                            ps.execute();
                            return true;
                        } catch (SQLException ex) {
                            Logger.getLogger(Termini.class.getName()).log(Level.SEVERE, null, ex);
                            return false;
                        }
                } catch (SQLException ex) {
                    return false; 
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Termini.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
         
    }
    
    public static boolean obrisiTermin(int id){
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("DELETE FROM usluga WHERE id_usluge = ?");
        
        try {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Termini.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

