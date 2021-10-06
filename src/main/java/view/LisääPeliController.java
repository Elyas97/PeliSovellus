package view;

import java.io.IOException;
import java.util.Arrays;

import controller.ProfiiliController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Käyttäjä;
import model.Peli;
import model.PeliSovellusDAO;
import model.Pelingenre;
import model.TiedostoKäsittely;

public class LisääPeliController {
	@FXML
	private ToggleGroup tyyppi;
	@FXML
	private ToggleGroup pelintyyppi;
	@FXML
	private TextField hinta;
	@FXML
	private ChoiceBox<String> genre;
	@FXML
	private TextField pelinnimi;
	@FXML
	private TextField ikaraja;
	@FXML
	private TextField pelaajamaara;
	@FXML 
	private TextField kaupunki;
	@FXML 
	private TextField kuvaus;
	@FXML 
	private TextField tekstikenttä;
	@FXML
	private ChoiceBox<String> kunto;
	@FXML
	private ChoiceBox<String> konsoli;
	@FXML
	private Pane konsoliPane;
	@FXML
	private RadioButton video;
	@FXML
	private RadioButton lauta;
	Käyttäjä käyttäjä;

	
	
	private Stage dialogStage;
	//private Peli peli;
	private boolean tallennaClicked = false;
	private MainApp main;
	private Pelingenre genret;
	PeliSovellusDAO pelisovellusdao = new PeliSovellusDAO();
	EtusivuController etusivu = new EtusivuController();
	
	public LisääPeliController() {}
	@FXML
	public String tyyppiAction(ActionEvent Action) {
		
		String text = ((RadioButton)tyyppi.getSelectedToggle()).getText();
		System.out.println(text);
		return text;
	}
	
	@FXML
	public String tyyppi(ActionEvent Action) {
		
		String text = ((RadioButton)pelintyyppi.getSelectedToggle()).getText();
		System.out.println(text);
		
		if(text.equals("lauta")) {
			konsoliPane.setVisible(false);
		}else {
			konsoliPane.setVisible(true);
		}
		
		return text;
	}
	
	@FXML
	private void initialize() {
		ObservableList<String> options = FXCollections.observableArrayList("Urheilu", "Räiskintä","Toiminta"
				,"Ajopeli", "Jännitys", "Seikkailu", "Strategia", "Roolipeli", "Pulma",
				"Lautapeli");
		genre.setItems(options);
		
		ObservableList<String> kuntoOptions = FXCollections.observableArrayList("Erinomainen", "Kiitettävä","Hyvä"
				,"Kohtalainen", "Välttävä");
		kunto.setItems(kuntoOptions);
		
		ObservableList<String> konsoliOptions = FXCollections.observableArrayList("Xbox", "Playstation","Wii");
		konsoli.setItems(konsoliOptions);
		
		// ei toimi
		//genre.getItems().setAll(Pelingenre.values());
		//choiceBox.getItems().clear();
		//choiceBox.getItems().setAll(Pelingenre.values());
		//choiceBox.setItems((ObservableList<Pelingenre>) Arrays.asList(Pelingenre.values()));
		
	}
	public void setMainApp(MainApp main) {
		this.main = main;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void uusiPeli() {
		Peli peli = new Peli();
		peli.setPelinNimi(pelinnimi.getText());
		int price = Integer.parseInt(hinta.getText());
		peli.setHinta(price);
		int age = Integer.parseInt(ikaraja.getText());
		peli.setIkaraja(age);
		peli.setKaupunki(kaupunki.getText());
		String tyyppiText = ((RadioButton)tyyppi.getSelectedToggle()).getText();
		peli.setTalletusTyyppi(tyyppiText);
		peli.setKuvaus(kuvaus.getText());
		int players = Integer.parseInt(pelaajamaara.getText());
		peli.setPelmaara(players);
		String pelintyyppiText = ((RadioButton)pelintyyppi.getSelectedToggle()).getText();
		peli.setPelinTyyppi(pelintyyppiText);
		peli.setGenre(genre.getValue().toString());
		System.out.println(genre.getValue().toString());
		peli.setKunto(kunto.getValue().toString());
		peli.setTekstikenttä(tekstikenttä.getText());

		
		peli.setKonsoli(konsoli.getValue().toString());

		//System.out.println(peli.getPelinNimi()+" "+ peli.getHinta()+" "+ peli.getIkaraja());
		pelisovellusdao.lisaaPeli(peli, 2);
		tallennaClicked = true;
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Alert");
	    alert.setContentText("Uusi peli lisätty onnistuneesti!");
	    alert.showAndWait();
	    
		etusivu.listaaPelit();
		
		//dialogStage.close();
	}
	
	public boolean tallennaClicked() {
		return tallennaClicked;
	}
	
	 @FXML
	    void vieEtusivulle(ActionEvent event) throws IOException {
		//vaihdetaan näkymää samalla viedään käyttäjän tiedot
		 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));
         BorderPane personOverview = (BorderPane) loader.load();
         Scene etusivulle = new Scene(personOverview);
         //stage
         Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(etusivulle);
	    	window.show();

	    }

	    @FXML
	    void vieProfiiliNäkymä(ActionEvent event) throws IOException {
	    	//vaihdetaan näkymää samalla viedään käyttäjän tiedot
			 FXMLLoader loader = new FXMLLoader();
	         loader.setLocation(MainApp.class.getResource("Profiili.fxml"));
	         BorderPane personOverview = (BorderPane) loader.load();
	         Scene etusivulle = new Scene(personOverview);
	         //stage
	         Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
		    	window.setScene(etusivulle);
		    	window.show();

	    }

	    @FXML
	    void vieTapahtumat(ActionEvent event) throws IOException {
	    	//vaihdetaan näkymää samalla viedään käyttäjän tiedot
			 FXMLLoader loader = new FXMLLoader();
	         loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));
	         BorderPane personOverview = (BorderPane) loader.load();
	         Scene etusivulle = new Scene(personOverview);
	         //stage
	         Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
		    	window.setScene(etusivulle);
		    	window.show();

	    }
	    @FXML
	    void LogOut(ActionEvent event) throws IOException {
	    	boolean test=TiedostoKäsittely.poistaTiedosto();
	    	if(test==true) {
	    		//ajetaan kirjautumis sivulle
	    		FXMLLoader loader = new FXMLLoader();
		        
		        loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
		       
		        BorderPane etusivu = (BorderPane) loader.load();
		    	Scene kirjautumisNäkymä = new Scene(etusivu);
		    	//get stage
		    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
		    	window.setScene(kirjautumisNäkymä);
		    	window.show();
	    		
	    	}
	    	
	    }

}
