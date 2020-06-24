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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wellness.model.Rezervacije;
import wellness.model.Termini;

/**
 *
 * @author MK
 */
public class GlavniOKController implements Initializable{

    
    @FXML
    private TableView pocetnaOk;
    @FXML
    private TableColumn kategorija_pocetnaOk;
    @FXML
    private TableColumn vrstaUsluge_pocetnaOk;
    @FXML
    private TableColumn cijena_pocetnaOk;
    @FXML
    private TableColumn datum_pocetnaOk;
    @FXML
    private TableColumn vrijeme_pocetnaOk;
    @FXML
    private Button odjavaButtonOk;
    @FXML
    private TableView mojeRezervacijeOk;
    @FXML
    private TableColumn kategorija_mojerezervacije;
    @FXML
    private TableColumn vrsta_usluge_mojerezevacije;
    @FXML
    private TableColumn cijena_mojerezervacije;
    @FXML
    private TableColumn datum_mojerezervacije;
    @FXML
    private TableColumn vrijeme_mojerezervacije;
   
     public void izlistajTermine(){
        ObservableList<Termini> data = Termini.listaTermina();
        kategorija_pocetnaOk.setCellValueFactory(new PropertyValueFactory<Termini, String>("id_kategorije"));
        vrstaUsluge_pocetnaOk.setCellValueFactory(new PropertyValueFactory<Termini, String>("naziv"));
        cijena_pocetnaOk.setCellValueFactory(new PropertyValueFactory<Termini, Float>("cijena"));
        datum_pocetnaOk.setCellValueFactory(new PropertyValueFactory<Termini, String>("datum"));
        vrijeme_pocetnaOk.setCellValueFactory(new PropertyValueFactory<Termini, String>("vrijeme"));
        pocetnaOk.setItems(data);
    }
     
    public void izlistajRezervacije(){
        ObservableList<Rezervacije> data = Rezervacije.mojeRezervacijeLista();
        kategorija_mojerezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("Kategorija"));
        vrsta_usluge_mojerezevacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("id_usluge"));
        cijena_mojerezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("cijena"));
        datum_mojerezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("datum"));
        vrijeme_mojerezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("vrijeme"));
        mojeRezervacijeOk.setItems(data);
    }
    
    public int id2;
    public void postaviListenerT(){
        pocetnaOk.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Termini selektiraniTermin = (Termini) pocetnaOk.getSelectionModel().getSelectedItem();
               System.out.println(selektiraniTermin.getVrijeme());
               id2 = selektiraniTermin.getId();
            }
       });
    }
    
    public int idRezervacije;
    public void postaviListener(){
        mojeRezervacijeOk.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Rezervacije selektiranaRezervacija = (Rezervacije) mojeRezervacijeOk.getSelectionModel().getSelectedItem();
               System.out.println(selektiranaRezervacija.getVrijeme());
               idRezervacije = selektiranaRezervacija.getId_Rezervacije();
            }
        });
    }
     
    @FXML
    public void rezervirajTermin(ActionEvent e){
        pocetnaOk.setEditable(true);
        int selected = pocetnaOk.getSelectionModel().getSelectedIndex();
        if(selected >= 0){
            if(Termini.rezervirajTermin(id2)){
                izlistajTermine();
                izlistajRezervacije();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Uspješno");
                alert.setHeaderText("Uspješno ste rezervirali termin.");
                alert.setContentText("Rezervacija zavrsena.");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Ovaj termin ste već rezervirali.");
                alert.setContentText("Ovaj termin ste već rezervirali.");
                alert.show();
            }
        }
    } 
    
    @FXML
    public void otkaziRezervaciju(ActionEvent e){
        mojeRezervacijeOk.setEditable(true);
        int selected = mojeRezervacijeOk.getSelectionModel().getSelectedIndex();
        if(selected >= 0){
            if(Rezervacije.otkaziRezervaciju(idRezervacije)){
                izlistajRezervacije();
                izlistajTermine();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Uspješno");
                alert.setHeaderText("Uspješno ste otkazali rezervaciju.");
                alert.setContentText("Rezervacija otkazana.");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Dogodila se greska pri otkazivanju rezervacije.");
                alert.setContentText("Dogodila se greska pri otkazivanju rezervacije.");
                alert.show();
            }
        }
    } 
    
    @FXML
    public void odjava() throws IOException{
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("wellness/view/Prijava.fxml"));
            Stage stage = new Stage();
                    
            stage.setTitle("Prijavi se");
            stage.setScene(new Scene(root));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
            odjavaButtonOk.getScene().getWindow().hide();
            }catch (IOException ex) {
                Logger.getLogger(PrijavaController.class.getName()).log(Level.SEVERE, null, ex);
            }
    } 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        izlistajTermine();
        postaviListenerT();
        izlistajRezervacije();
        postaviListener();
    }
}
