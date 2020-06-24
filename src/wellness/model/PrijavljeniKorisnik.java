/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author MK
 */
public class PrijavljeniKorisnik {
 public static int id_korisnika;
    public static String e_mail;
    
    public static boolean prijava(String korisnik, String lozinka){
      BazaPodataka db = new BazaPodataka();
      PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE e_mail = ? AND lozinka = ?");
      
      try{
          ps.setString(1, korisnik);
          ps.setString(2, lozinka);
          ResultSet rs = ps.executeQuery();
          
          if(rs.next()){
              id_korisnika = rs.getInt("id_korisnika");
              e_mail = rs.getString("e_mail");
              return true;
          }
          else{
              return false;
          }
      }catch(SQLException ex) {
            System.out.println("Greška: "+ex.getMessage());
            return false;
        }
    }
    public static boolean isAdmin(String korisnik){
         BazaPodataka db = new BazaPodataka();
      PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE e_mail = ? AND administrator = 1");
      
      try{
          ps.setString(1, korisnik);
          ResultSet rs = ps.executeQuery();
          
          if(rs.next()){
              return true;
          }
          else{
              return false;
          }
      }catch(SQLException ex) {
            System.out.println("Greška: "+ex.getMessage());
            return false;
        }
    }
    
    public static boolean provjeriKorisnika(String korisnik){
      BazaPodataka db = new BazaPodataka();
      PreparedStatement ps = db.exec("SELECT * FROM korisnik WHERE e_mail = ?");
      
      try{
          ps.setString(1, korisnik);
          ResultSet rs = ps.executeQuery();
          
          if(rs.next()){
              return true;
          }
          else{
              return false;
          }
      }catch(SQLException ex) {
            System.out.println("There was a error: "+ex.getMessage());
            return false;
        }
    }
    public static boolean registriraj(String ime, String prezime, String e_mail, String lozinka){
        BazaPodataka db = new BazaPodataka();
        PreparedStatement ps = db.exec("INSERT INTO korisnik (ime, prezime, e_mail, lozinka, administrator, aktivnost) VALUES (?,?,?,?,0,1)");
        try{
            ps.setString(1, ime);
            ps.setString(2, prezime);
            ps.setString(3, e_mail);
            ps.setString(4, lozinka);
            ps.execute();
            return true;
        }catch(SQLException ex) {
            System.out.println("There was a error: "+ex.getMessage());
            return false;
        } 
    }
}

