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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author MK
 */
public class OstaliKorisnici {

    SimpleIntegerProperty id_korisnika = new SimpleIntegerProperty();
    SimpleStringProperty ime = new SimpleStringProperty();
    SimpleStringProperty prezime = new SimpleStringProperty();
    SimpleStringProperty e_mail = new SimpleStringProperty();
    SimpleStringProperty admin = new SimpleStringProperty();
    private boolean aktivnost;
    
   
    public OstaliKorisnici(int id, String ime, String prezime, String e_mail, Boolean aktivnost, Boolean admin) {
        this.id_korisnika = new SimpleIntegerProperty (id);
        this.ime = new SimpleStringProperty (ime);
        this.prezime = new SimpleStringProperty (prezime);
        this.e_mail= new SimpleStringProperty(e_mail);
        
        if(admin == true){
            this.admin = new SimpleStringProperty("Admin");
        }else{
            this.admin = new SimpleStringProperty("Obicni");
        }
        
        this.aktivnost = aktivnost;	
        }
     
    
   public String getAdmin (){
       return admin.get();
   } 

    public void setAdmin(SimpleStringProperty admin) {
        this.admin = admin;
    }
   
   public String getIme () {
        return ime.get();
    }
    public void setIme(String ime) {
        this.ime = new SimpleStringProperty(ime);
    }
    public String getPrezime () {
        return prezime.get();
    }
    public void setPrezime(String prezime) {
        this.prezime = new SimpleStringProperty(prezime);
    }
    public String getE_mail () {
        return e_mail.get();
    }
    public void setE_mail(String e_mail) {
        this.e_mail = new SimpleStringProperty(e_mail);
    }
    public boolean isAktivnost(){ 
        return aktivnost;
    }
    public void setAktivnost(boolean aktivnost){
        this.aktivnost = aktivnost;
    }
    public int getIdKor () {
        return id_korisnika.get();
    }
        
    public static ObservableList<OstaliKorisnici> listaKorisnika() {
       ObservableList<OstaliKorisnici> list = FXCollections.observableArrayList();
       BazaPodataka db = new BazaPodataka();
       PreparedStatement ps = db.exec("SELECT korisnik.id_korisnika, korisnik.ime, korisnik.prezime, korisnik.e_mail, korisnik.aktivnost, korisnik.administrator FROM korisnik WHERE aktivnost = true");
       try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OstaliKorisnici(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), rs.getBoolean(6)));
            }
        } catch (SQLException ex) {
            System.out.println("Greška prilikom iteriranja: " + ex.getMessage());
        }
        return list; 
    }
    
    
    public static boolean dodajAdmina(int idK){
         BazaPodataka db = new BazaPodataka();
       PreparedStatement ps = db.exec("UPDATE korisnik SET administrator = 1 WHERE id_korisnika = ?");
       
       try {
            ps.setInt(1, idK);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
           return false; 
        }
    }
    
    public static boolean ukloniAdmina(int idK){
       BazaPodataka db = new BazaPodataka();
       PreparedStatement ps =db.exec("SELECT COUNT(*) AS count FROM korisnik WHERE administrator = 1");
        try {
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               if(rs.getInt("count") < 2){
                   return false;
               }else{
                    ps = db.exec("UPDATE korisnik SET administrator = 0 WHERE id_korisnika = ?");
                    try{
                        ps.setInt(1, idK);
                        ps.executeUpdate();
                        return true;
                    }catch(SQLException ex){
                         System.out.println("There was a error: " + ex.getMessage());
                         return false;
                    }
               }
            }
        } catch (SQLException ex) {
            System.out.println("There was a error: " + ex.getMessage());
            return false;
        }
        return false;
    }
}