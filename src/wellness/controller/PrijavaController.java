/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.controller;

import wellness.model.PrijavljeniKorisnik;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceBoxBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 *
 * @author MK
 */
public class PrijavaController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    
    @FXML
    Label prijavi_se;
    
    @FXML
    TextField email_prijava;
    
    @FXML
    PasswordField lozinka_prijava;
    
    @FXML
    Button prijavi_se_button;
    
    @FXML
    Label obavijest;
    
    @FXML
    public void Prijava(ActionEvent e){
        String e_mail = email_prijava.getText();
        String lozinka = lozinka_prijava.getText();
       
        if(e_mail.equals("") || lozinka.equals("")){
            obavijest.setText("Unesite sve podatke!");
        }
        else{
            if (PrijavljeniKorisnik.prijava(e_mail, lozinka)) {
                if(PrijavljeniKorisnik.isAdmin(e_mail)){
                    try {
                    obavijest.setTextFill(Color.GREEN);
                    obavijest.setText("Uspješna prijava.");
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("wellness/view/GlavniAdmin.fxml"));
                    Stage stage = new Stage();
                    
                    stage.setTitle("Wellness_puj - Administrator");
                    stage.setScene(new Scene(root));
                    stage.resizableProperty().setValue(Boolean.FALSE);
                    stage.show();
                    obavijest.getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(PrijavaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                else{
                    try {
                    obavijest.setTextFill(Color.GREEN);
                    obavijest.setText("Uspješna prijava.");
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("wellness/view/GlavniOK.fxml"));
                    Stage stage = new Stage();
                    
                    stage.setTitle("Wellness_puj - OK");
                    stage.setScene(new Scene(root));
                    stage.show();
                    obavijest.getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(PrijavaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            } else {
                obavijest.setText("Unos neispravan.");
            }
        }
    }
    
     public void toRegistracija(){
        try {
                    Parent root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("wellness/view/Registracija.fxml"));
                    Stage stage = new Stage();
                    
                    stage.setTitle("Registracija");
                    stage.setScene(new Scene(root));
                    stage.resizableProperty().setValue(Boolean.FALSE);
                    stage.show();
                    obavijest.getScene().getWindow().hide();
                } catch (IOException ex) {
                    Logger.getLogger(PrijavaController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
