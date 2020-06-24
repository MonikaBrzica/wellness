/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wellness.model.PrijavljeniKorisnik;

/**
 * FXML Controller class
 *
 * @author MK
 */
public class RegistracijaController implements Initializable {

    @FXML
    private TextField ime_registracija;
    @FXML
    private TextField prezime_registracija;
    @FXML
    private TextField email_registracija;
    @FXML
    private PasswordField lozinka_registracija;
    @FXML
    Label registracija_label;

    
    public void prijava(ActionEvent event) {
        try {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("wellness/view/Prijava.fxml"));
                    Stage stage = new Stage();
                    
                    stage.setTitle("Prijava");
                    stage.setScene(new Scene(root));
                    stage.resizableProperty().setValue(Boolean.FALSE);
                    stage.show();
                    registracija_label.getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(RegistracijaController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    public void registracija(ActionEvent event) {
        String ime = ime_registracija.getText();
        String prezime = prezime_registracija.getText();
        String e_mail = email_registracija.getText();
        String lozinka = lozinka_registracija.getText();
        
        if(ime.length() >= 3 && prezime.length() > 3 && e_mail.length() > 3 && lozinka.length() >= 3){
            if(!PrijavljeniKorisnik.provjeriKorisnika(e_mail)){
                if(PrijavljeniKorisnik.registriraj(ime, prezime, e_mail, lozinka)){
                    
                        try {
                            Parent root;
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("wellness/view/Prijava.fxml"));
                            Stage stage = new Stage();
                            
                            stage.setTitle("Glavni ekran");
                            stage.setScene(new Scene(root));
                            stage.resizableProperty().setValue(Boolean.FALSE);
                            stage.show();
                            registracija_label.getScene().getWindow().hide();
                            registracija_label.setTextFill(Color.GREEN);
                            registracija_label.setText("Uspješna registracija.");
                        } catch (IOException ex) {
                            Logger.getLogger(RegistracijaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                }else{
                     registracija_label.setTextFill(Color.RED);
                     registracija_label.setText("Greška.");
                }
            }else{
                 registracija_label.setTextFill(Color.RED);
                 registracija_label.setText("Korisnik postoji u bazi.");
            }
            
         
        }
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
