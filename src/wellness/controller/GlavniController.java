/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wellness.controller;

import wellness.model.OstaliKorisnici;
import wellness.model.Rezervacije;
import wellness.model.Termini;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wellness.model.Kategorija;
/**
 *
 * @author MK
 */
public class GlavniController implements Initializable{

    @FXML
    private TableView pocetna;
    @FXML
    private TableColumn kategorija_pocetna;
    @FXML
    private TableColumn vrsta_usluge_pocetna;
    @FXML
    private TableColumn cijena_pocetna;
    @FXML
    private TableColumn datum_pocetna;
    @FXML
    private TableColumn vrijeme_pocetna;
    @FXML
    private ChoiceBox kategorija_choicebox_pocetna;
    @FXML
    private TextField vrsta_usluge;
    @FXML
    private TextField cijena;
    @FXML
    private DatePicker datum;
    @FXML
    private TextField vrijeme;
    @FXML
    private TextField br_termina;
    @FXML
    private TableView rezervacije;
    @FXML
    private TableColumn korisnik_rezervacije;
    @FXML
    private TableColumn kategorija_rezervacije;
    @FXML
    private TableColumn vrsta_usluge_rezervacije;
    @FXML
    private TableColumn datum_rezervacije;
    @FXML
    private TableColumn vrijeme_rezervacije;
    @FXML
    private TableView korisnici;
    @FXML
    private TableColumn ime_korisnici;
    @FXML
    private TableColumn prezime_korisnici;
    @FXML
    private TableColumn e_mail_korisnici;
    @FXML
    private TableColumn admin_korisnici;
    @FXML
    private Button odjavaButton;
    @FXML
    private TableView kategorije;
    @FXML
    private TableColumn naziv_kategorije_tablica;
    @FXML
    private TextField naziv_kategorije;
   
    public void izlistajTermine(){
        ObservableList<Termini> data = Termini.listaTermina();
        kategorija_pocetna.setCellValueFactory(new PropertyValueFactory<Termini, String>("id_kategorije"));
        vrsta_usluge_pocetna.setCellValueFactory(new PropertyValueFactory<Termini, String>("naziv"));
        cijena_pocetna.setCellValueFactory(new PropertyValueFactory<Termini, Float>("cijena"));
        datum_pocetna.setCellValueFactory(new PropertyValueFactory<Termini, String>("datum"));
        vrijeme_pocetna.setCellValueFactory(new PropertyValueFactory<Termini, String>("vrijeme"));
        pocetna.setItems(data);
    }

     public void izlistajKorisnike(){
        ObservableList<OstaliKorisnici> data = OstaliKorisnici.listaKorisnika();
        ime_korisnici.setCellValueFactory(new PropertyValueFactory<Termini, String>("Ime"));
        prezime_korisnici.setCellValueFactory(new PropertyValueFactory<Termini, String>("Prezime"));
        e_mail_korisnici.setCellValueFactory(new PropertyValueFactory<Termini, Float>("E_mail"));
        admin_korisnici.setCellValueFactory(new PropertyValueFactory<Termini, String>("Admin"));
        korisnici.setItems(data);
    }
    
    public void izlistajRezervacije(){
        ObservableList<Rezervacije> data = Rezervacije.rezervacijeLista();
        korisnik_rezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("Korisnika"));
        kategorija_rezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("Kategorija"));
        vrsta_usluge_rezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("id_usluge"));
        datum_rezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("datum"));
        vrijeme_rezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("vrijeme"));
        rezervacije.setItems(data);
    }
    
    public void izlistajKategorije(){
        ObservableList<Kategorija> data = Kategorija.listaKategorija();
        naziv_kategorije_tablica.setCellValueFactory(new PropertyValueFactory<Rezervacije, String>("Naziv"));
        kategorije.setItems(data);
    }
    
    
    public int id2;
    public void postaviListenerT(){
        pocetna.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Termini selektiraniTermin = (Termini) pocetna.getSelectionModel().getSelectedItem();
               System.out.println(selektiraniTermin.getVrijeme());
               id2 = selektiraniTermin.getId();
               vrsta_usluge.setText(String.valueOf(selektiraniTermin.getNaziv()));
               cijena.setText(String.valueOf(selektiraniTermin.getCijena()));
               datum.setValue(LocalDate.parse(selektiraniTermin.getDatum(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
               vrijeme.setText(String.valueOf(selektiraniTermin.getVrijeme()));
               br_termina.setText(String.valueOf(selektiraniTermin.getBr_termina()));
            }
        });
    }
    
    public int id1;
    public void postaviListenerR(){
        rezervacije.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Rezervacije selektiranaRezervacija = (Rezervacije) rezervacije.getSelectionModel().getSelectedItem();
               id1 = selektiranaRezervacija.getId_Rezervacije();
               System.out.println(id1);
            }
        });
    } 
    
    public int idRezervacije;
    public void postaviListener(){
        rezervacije.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Rezervacije selektiranaRezervacija = (Rezervacije) rezervacije.getSelectionModel().getSelectedItem();
               System.out.println(selektiranaRezervacija.getVrijeme());
               idRezervacije = selektiranaRezervacija.getId_Rezervacije();
            }
       });
    }
    
    @FXML
    public void rezervirajTermin(ActionEvent e){
        pocetna.setEditable(true);
        int selected = pocetna.getSelectionModel().getSelectedIndex();
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
        rezervacije.setEditable(true);
        int selected = rezervacije.getSelectionModel().getSelectedIndex();
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
    
    public int idKorisnika;
    public void postaviListenerK(){
        korisnici.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               OstaliKorisnici selektiraniKorisnik = (OstaliKorisnici) korisnici.getSelectionModel().getSelectedItem();
               idKorisnika = selektiraniKorisnik.getIdKor();
            }
        });
    }
    
    public int idKategorije;
    public void postaviListenerKat(){
        kategorije.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               Kategorija selektiranaKategorija = (Kategorija) kategorije.getSelectionModel().getSelectedItem();
               idKategorije = selektiranaKategorija.getId_Kategorije();
               naziv_kategorije.setText(selektiranaKategorija.getNaziv());
                       
            }
        });
    }
    
    @FXML
    public void dodajAdmina (ActionEvent e){
        if(OstaliKorisnici.dodajAdmina(idKorisnika)){
            izlistajKorisnike();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Uspjeh");
            alert.setHeaderText("Dodali ste administratora.");
            alert.setContentText("Admin dodan");
            alert.show(); 
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Korisnik je već adminstrator.");
            alert.setContentText("Administrator već postavljen.");
            alert.show();
        }
    }
    
    @FXML
    public void ukloniAdmina (ActionEvent e){
        if(OstaliKorisnici.ukloniAdmina(idKorisnika)){
            izlistajKorisnike();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Uspjeh");
            alert.setHeaderText("Uspješno ste uklonili administratora.");
            alert.setContentText("Uklonili ste administratora");
            alert.show(); 

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Korisnik nije administrator.");
            alert.setContentText("Korisnik nije administrator.");
            alert.show();
        }
    }
    
    @FXML
    public void zavrsiRezervaciju(ActionEvent e){
        rezervacije.setEditable(true);
        int selected = rezervacije.getSelectionModel().getSelectedIndex();
        if(selected >= 0){
            if(Rezervacije.zavrsiRezervaciju(id1)){
                rezervacije.getItems().remove(selected);
                izlistajTermine();
                izlistajRezervacije();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Uspješno");
                alert.setHeaderText("Rezervacija zavrsena.");
                alert.setContentText("Rezervacija uklonjena sa liste.");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Dogodila se pogreška prilikom završetka rezervacije.");
                alert.setContentText("Niste uspjeli završiti rezervaciju.");
                alert.show();
            }
        }
    }
    
    @FXML
    public void dodajTermin(ActionEvent e){
        String Kategorija = String.valueOf(kategorija_choicebox_pocetna.getValue());
        String Vrsta_usluge = vrsta_usluge.getText();
        String Cijena = cijena.getText();
        String Datum = datum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        String Vrijeme = vrijeme.getText();
        String Br_termina = br_termina.getText();
        int kol;
        float cij;
        try{
            kol = Integer.parseInt(Br_termina);
            cij = Float.parseFloat(Cijena);
            
            if(Vrsta_usluge.length() >= 3 ){
                Termini t = new Termini(0, Vrsta_usluge, Datum, Vrijeme, kol, cij, Kategorija, true, 1);
                ObservableList<Termini> termini = Termini.listaTermina();
                boolean postoji = false;
                for(int i = 0; i < termini.size(); i++){
                    if(t.getId_kategorije().equals(termini.get(i).getId_kategorije()) 
                            && t.getDatum().equals(termini.get(i).getDatum())
                            && t.getVrijeme().equals(termini.get(i).getVrijeme())){
                        postoji = true;
                    }
                }
                if(postoji){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Greska");
                        alert.setHeaderText("Termin koji ste dodali vec postoji!");
                        alert.show();
                        izlistajTermine();
                        izlistajRezervacije();
                        vrsta_usluge.setText("");
                        cijena.setText("");
                        vrijeme.setText("");
                        br_termina.setText("");
                }else{
                    System.out.println("Usao");
                    if(Termini.dodajTermin(t)){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Uspjeh");
                        alert.setHeaderText("Uspješno ste dodali termin!");
                        alert.setContentText("Dodan termin.");
                        alert.show();
                        izlistajTermine();
                        vrsta_usluge.setText("");
                        cijena.setText("");

                        vrijeme.setText("");
                        br_termina.setText("");
                    }
                }
                
            }else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Greška");
               alert.setHeaderText("Neuspješno dodavanje termina.");
               alert.setContentText("Greška prillikom dodavanj termina.");
               alert.show();
            }
            
        }catch(NumberFormatException ev){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Unesite valjane podatke.");
            alert.setContentText("Cijena / broj termina moraju imati brojčanu vrijednost.");
            alert.show();
        }
    }
    
    @FXML
    public void urediTermin(ActionEvent e){
        String Kategorija = String.valueOf(kategorija_choicebox_pocetna.getValue());
        String Vrsta_usluge = vrsta_usluge.getText();
        String Cijena = cijena.getText();
        String Datum = datum.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String Vrijeme = vrijeme.getText();
        String Br_termina = br_termina.getText();
        int kol;
        float cij;
        try{
            kol = Integer.parseInt(Br_termina);
            cij = Float.parseFloat(Cijena);
            
            if(Vrsta_usluge.length() >= 3 && Kategorija != "null"){
                System.out.println(Kategorija);
                Termini t = new Termini(id2, Vrsta_usluge, Datum, Vrijeme, kol, cij, Kategorija, true,1);
                if(Termini.urediTermin(t)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Uspjeh");
                    alert.setHeaderText("Uredili ste termin.");
                    alert.setContentText("Termin uređen.");
                    alert.show();
                    izlistajTermine();
                    izlistajRezervacije();
                    vrsta_usluge.setText("");
                    cijena.setText("");
                   
                    vrijeme.setText("");
                    br_termina.setText(""); 
                }
            }else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Greška");
               alert.setHeaderText("Niste uspjeli urediti termin.");
               alert.setContentText("Dogodila se greška prilikom uređivanja termina.");
               alert.show();
            }
            
        }catch(NumberFormatException ev){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Unesite valjane podatke.");
            alert.setContentText("Cijena / broj termina moraju imati brojčanu vrijednost.");
            alert.show();
        }
    }
    
    @FXML
    public void obrisiTermin(ActionEvent e){
        pocetna.setEditable(true);
        int selected = pocetna.getSelectionModel().getSelectedIndex();
        if(selected >= 0){
            if(Termini.obrisiTermin(id2)){
                pocetna.getItems().remove(selected);
                izlistajTermine();
                izlistajRezervacije();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Uspješno");
                alert.setHeaderText("Uspješno obrisan termin.");
                alert.setContentText("Termin obrisan.");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Niste uspjeli obrisati termin.");
                alert.setContentText("Dogodila se greška prilikom brisanja termina.");
                alert.show();
            }
        }
    }
      
    public void dodajKategoriju(ActionEvent e){
        String nazivKategorije = naziv_kategorije.getText();
        if(Kategorija.dodajKategoriju(nazivKategorije)){
            izlistajKategorije();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Uspješno");
            alert.setHeaderText("Uspješno ste dodali kategoriju.");
            alert.setContentText("Kategorija dodana.");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Niste uspjeli dodati kategoriju.");
            alert.setContentText("Dogodila se greška prilikom dodavanja kategorije.");
            alert.show();
        }
    }
    
    public void urediKategoriju(ActionEvent e){
        String naziv = naziv_kategorije.getText();
        Kategorija k = new Kategorija(idKategorije, naziv, true);
        if(Kategorija.urediKategoriju(k)){
            izlistajKategorije();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Uspješno");
            alert.setHeaderText("Uspješno ste uredili kategoriju.");
            alert.setContentText("Kategorija uređena.");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Niste uspjeli urediti kategoriju.");
            alert.setContentText("Dogodila se greška prilikom uređivanja kategorije.");
            alert.show();
        }
    }
    
    public void obrisiKategoriju(ActionEvent e){
        kategorije.setEditable(true);
        int selected = kategorije.getSelectionModel().getSelectedIndex();
        if(selected >= 0){
            if(Kategorija.obrisiKategoriju(idKategorije)){
                kategorije.getItems().remove(selected);
                izlistajKategorije();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Uspješno");
                alert.setHeaderText("Uspješno ste obrisali kategoriju.");
                alert.setContentText("Kategorija obrisana.");
                alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Niste uspjeli obrisati kategoriju.");
                alert.setContentText("Dogodila se greška prilikom brisanja kategorije.");
                alert.show();
            }
        }
    }
    
    public void popuniChoiceboxKategorije(){
        ObservableList<Kategorija> kategorije = Kategorija.listaKategorija();
        for(int i = 0; i < kategorije.size(); i++){
            kategorija_choicebox_pocetna.getItems().add(kategorije.get(i).getNaziv());
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
            odjavaButton.getScene().getWindow().hide();
            }catch (IOException ex) {
                Logger.getLogger(PrijavaController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        izlistajTermine();
        postaviListener();
        postaviListenerT();
        postaviListenerR();
        popuniChoiceboxKategorije();
        izlistajRezervacije();
        izlistajKorisnike();
        izlistajKategorije();
        postaviListenerK();
        postaviListenerKat();
    }
}


