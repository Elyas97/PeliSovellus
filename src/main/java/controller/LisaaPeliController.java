package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Kayttaja;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;
import view.MainApp;

public class LisaaPeliController {
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
	private TextArea kuvaus;
	@FXML
	private TextArea tekstikenttä;
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
	@FXML
	private Label kirjaimet;
	@FXML
	private Text nimivaroitus;
	@FXML
	private Text hintavaroitus;
	@FXML
	private Text tyyppivaroitus;
	@FXML
	private Text paikkakuntavaroitus;
	@FXML
	private Text genrevaroitus;
	@FXML
	private Text ikarajavaroitus;
	@FXML
	private Text pelaajamaaravaroitus;
	@FXML
	private Text kuntovaroitus;
	@FXML
	private Text kuvausvaroitus;
	@FXML
	private Text tekstikenttavaroitus;
	@FXML
	private Text ilmoitustyyppivaroitus;

	Kayttaja käyttäjä;

	private Stage dialogStage;
	private boolean tallennaClicked = false;
	private MainApp main;
	PeliSovellusDAO pelisovellusdao = new PeliSovellusDAO();
	EtusivuController etusivu = new EtusivuController();
	String locale = Locale.getDefault().getLanguage();

	public LisaaPeliController() {
	}

	@FXML
	public String tyyppiAction(ActionEvent Action) {
		String text = ((RadioButton) tyyppi.getSelectedToggle()).getText();

		// Poistaa "pakollinen valinta" tekstikentän
		ilmoitustyyppivaroitus.setText("");
		
		switch (text) {
		case "Lahjoitetaan":
		case "Giveaway":
			hinta.setText(Integer.toString(0));
			hinta.setEditable(false);
			break;
		default:
			hinta.setText("");
			hinta.setEditable(true);
			break;
		}
		return text;
	}

	@FXML
	public String tyyppi(ActionEvent Action) {
		String text = ((RadioButton) pelintyyppi.getSelectedToggle()).getText();

		// Poistaa "pakollinen valinta" tekstikentän jos näkyvissä
		tyyppivaroitus.setText("");
		if (text.equals("Lautapeli") || text.equals("boardgame")) {
			konsoliPane.setVisible(false);
		} else {
			konsoliPane.setVisible(true);
		}
		return text;
	}

	@FXML
	private void initialize() {
		käyttäjä = TiedostoKasittely.lueKäyttäjä();
		
		if(locale.equals("en")) {
			//Tallentuu myös tietokantaan englanniksi, pitäisiköhän kääntää uudelleen suomeksi?
			ObservableList<String> options = FXCollections.observableArrayList("Sports", "Shooting", "Action",
					"Racing", "Horros", "Adventure", "Strategy", "Roleplay", "Puzzle", "Party", "Boardgame");
			genre.setItems(options);
			
			ObservableList<String> kuntoOptions = FXCollections.observableArrayList("Excellent", "Great", "Good",
					"Moderate", "Passable");
			kunto.setItems(kuntoOptions);
		}else {
			ObservableList<String> options = FXCollections.observableArrayList("Urheilu", "Räiskintä", "Toiminta",
					"Ajopeli", "Jännitys", "Seikkailu", "Strategia", "Roolipeli", "Pulma", "Seurapeli", "Lautapeli");
			genre.setItems(options);
			
			ObservableList<String> kuntoOptions = FXCollections.observableArrayList("Erinomainen", "Kiitettävä", "Hyvä",
					"Kohtalainen", "Välttävä");
			kunto.setItems(kuntoOptions);
		}
	
		ObservableList<String> konsoliOptions = FXCollections.observableArrayList("Xbox", "Playstation", "Wii");
		konsoli.setItems(konsoliOptions);

		tekstikenttä.setText(käyttäjä.getSähköposti() + "\n" + käyttäjä.getPuhelinumero() + " ");

		//kuvaus.setPromptText("Kuvaile peliä tai kerro kokemuksiasi pelistä");
		kirjaimet();
		tekstikenttä.textProperty().addListener((obs, old, newew) -> {
			kirjaimet();
		});

		validointiPiiloon();
	}

	public void setMainApp(MainApp main) {
		this.main = main;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/*
	 * Luo uuden pelin tietokantaan Ennen tietojen lähettämistä tarkastaa onko
	 * kaikki kentät täytetty.
	 * 
	 */
	@FXML
	public void uusiPeli() {
		if (validointi() == true) {
			Peli peli = new Peli();
			peli.setPelinNimi(pelinnimi.getText());
			int price = Integer.parseInt(hinta.getText());
			peli.setHinta(price);
			int age = Integer.parseInt(ikaraja.getText());
			peli.setIkaraja(age);
			peli.setKaupunki(kaupunki.getText());
			String tyyppiText = ((RadioButton) tyyppi.getSelectedToggle()).getText();
			peli.setTalletusTyyppi(tyyppiText);
			peli.setKuvaus(kuvaus.getText());
			int players = Integer.parseInt(pelaajamaara.getText());
			peli.setPelmaara(players);
			String pelintyyppiText = ((RadioButton) pelintyyppi.getSelectedToggle()).getText();
			peli.setPelinTyyppi(pelintyyppiText);
			peli.setGenre(genre.getValue().toString());
			System.out.println(genre.getValue().toString());
			peli.setKunto(kunto.getValue().toString());
			peli.setTekstikenttä(tekstikenttä.getText());
			peli.setKonsoli(konsoli.getValue().toString());

			long millis = System.currentTimeMillis();
			java.sql.Date paiva = new java.sql.Date(millis);
			peli.setPaivamaara(paiva);

			käyttäjä = TiedostoKasittely.lueKäyttäjä();

			pelisovellusdao.lisaaPeli(peli, käyttäjä.getKayttajaID());
			tallennaClicked = true;

			// Ilmoitus pelin onnistuneesta lisäyksestä
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			
			if(locale.equals("en")) { 
				alert.setContentText("New game added succesfully!");
			}else {
				alert.setContentText("Uusi peli lisätty onnistuneesti!");
			}
			
			alert.showAndWait();

			etusivu.listaaPelit();
		}
	}

	/*
	 * Kuuntelee kenttien syöttöä ja asettaa varoitustekstit pois jos kentissä on
	 * tekstiä
	 */
	private void validointiPiiloon() {
		pelinnimi.textProperty().addListener((obs, oldValue, newValue) -> {
			nimivaroitus.setText("");
			pelinnimi.setStyle("-fx-border:none");
		});
		hinta.textProperty().addListener((obs, oldValue, newValue) -> {
			hintavaroitus.setText("");
			hinta.setStyle("-fx-border:none");
		});
		kaupunki.textProperty().addListener((obs, oldValue, newValue) -> {
			paikkakuntavaroitus.setText("");
			kaupunki.setStyle("-fx-border:none");
		});
		ikaraja.textProperty().addListener((obs, oldValue, newValue) -> {
			ikarajavaroitus.setText("");
			ikaraja.setStyle("-fx-border:none");
		});
		pelaajamaara.textProperty().addListener((obs, oldValue, newValue) -> {
			pelaajamaaravaroitus.setText("");
			pelaajamaara.setStyle("-fx-border:none");
		});
		kuvaus.textProperty().addListener((obs, oldValue, newValue) -> {
			kuvausvaroitus.setText("");
			kuvaus.setStyle("-fx-border:none");
		});
		tekstikenttä.textProperty().addListener((obs, oldValue, newValue) -> {
			tekstikenttavaroitus.setText("");
			tekstikenttä.setStyle("-fx-border:none");
		});

		// setOnAction "kuuntelee" valintaa ja kun valittu asettaa varoitustekstin pois
		genre.setOnAction((event) -> {
			genrevaroitus.setText("");
		});
		kunto.setOnAction((event) -> {
			kuntovaroitus.setText("");
		});
	}

	@FXML
	public void kirjaimet() {
		int maxpituus = 200;
		int pituus = tekstikenttä.getText().length();
		int jaljella = maxpituus - pituus;

		if (jaljella <= 0) {
			kirjaimet.setText("" + jaljella);

			// Tekstikenttään ei voi kirjoittaa
			tekstikenttä.setEditable(false);

			// Ilmoitus siitä että tekstikenttä on täynnä
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			
			if(locale.equals("en")) { 
				alert.setContentText("Textfield is full!");
			}else {
				alert.setContentText("Tekstikenttä täynnä!");
			}

			alert.showAndWait();

			// Tekstikenttään voi taas kirjoittaa
			tekstikenttä.setEditable(true);
		} else {
			jaljella = maxpituus - pituus;
			kirjaimet.setText("" + jaljella);
		}
	}

	/*
	 * 
	 * Tarkistaa onko uusipeli.fxml kentät tyhjiä ennen kuin lähettää ne
	 * tietokantaan Jos kentät ovat tyhjiä niin ilmoittaa siitä "pakollinen kenttä"
	 * punaisella tekstillä ja TextFieldien reunat muuttuvat punaiseksi Myös pop up
	 * ilmestyy näytölle joka ilmoittaa mitkä kentät ovat tyhjiä
	 * 
	 * Tällä hetkellä tarkistaa kaikki kentät (myös kentät jotka voivat olla NULL)
	 * 
	 */
	private boolean validointi() {

		StringBuilder virhe = new StringBuilder();

		if (pelinnimi.getText().trim().isEmpty()) {
			//Alertteihin myös properties tiedostosta käännökset?
			if(locale.equals("en")) {
				virhe.append("Insert name\n");
			}else {
				virhe.append("Syötä pelin nimi\n");
			}
			pelinnimi.setStyle("-fx-border-color:red");
			nimivaroitus.setVisible(true);
		}
		if (hinta.getText().trim().isEmpty()) {
			if(locale.equals("en")) {
				virhe.append("Insert price\n");
			}else {
				virhe.append("Syötä pelin hinta\n");
			}
			hinta.setStyle("-fx-border-color:red");
			hintavaroitus.setVisible(true);
		}
		if (kaupunki.getText().trim().isEmpty()) {
			if(locale.equals("en")) {
				virhe.append("Insert city\n");
			}else {
				virhe.append("Syötä kaupunki\n");
			}
			kaupunki.setStyle("-fx-border-color:red");
			paikkakuntavaroitus.setVisible(true);
		}
		if (((RadioButton) pelintyyppi.getSelectedToggle()) == null) {
			if(locale.equals("en")) {
				virhe.append("Insert game type\n");
			}else {
				virhe.append("Syötä pelintyyppi\n");
			}
			tyyppivaroitus.setVisible(true);
		}
		if (((RadioButton) tyyppi.getSelectedToggle()) == null) {
			if(locale.equals("en")) {
				virhe.append("Insert notice type\n");
			}else {
				virhe.append("Syötä ilmoituksen tyyppi\n");
			}
			ilmoitustyyppivaroitus.setVisible(true);
		}
		if (genre.getValue() == null) {
			if(locale.equals("en")) {
				virhe.append("Insert genre\n");
			}else {
				virhe.append("Syötä genre\n");
			}
			genrevaroitus.setVisible(true);
		}
		if (ikaraja.getText().trim().isEmpty()) {
			if(locale.equals("en")) {
				virhe.append("Insert agelimit\n");
			}else {
				virhe.append("Syötä pelin ikäraja\n");
			}
			ikaraja.setStyle("-fx-border-color:red");
			ikarajavaroitus.setVisible(true);
		}
		if (pelaajamaara.getText().trim().isEmpty()) {
			if(locale.equals("en")) {
				virhe.append("Insert number of players\n");
			}else {
				virhe.append("Syötä pelin pelaajamäärä \n");
			}
			pelaajamaara.setStyle("-fx-border-color:red");
			pelaajamaaravaroitus.setVisible(true);
		}
		if (kunto.getValue() == null) {
			if(locale.equals("en")) {
				virhe.append("Insert shape\n");
			}else {
				virhe.append("Syötä pelinkunto\n");
			}
			kuntovaroitus.setVisible(true);
		}
		if (kuvaus.getText().trim().isEmpty()) {
			if(locale.equals("en")) {
				virhe.append("Insert description\n");
			}else {
				virhe.append("Syötä kuvaus\n");
			}
			kuvaus.setStyle("-fx-border-color:red");
			kuvausvaroitus.setVisible(true);
		}
		if (tekstikenttä.getText().trim().isEmpty()) {
			if(locale.equals("en")) {
				virhe.append("Insert contact information\n");
			}else {
				virhe.append("Syötä tekstikenttään asioita \n");
			}
			tekstikenttä.setStyle("-fx-border-color:red");
			tekstikenttavaroitus.setVisible(true);
		}
		if (virhe.length() > 0) {
			Alert varoitus = new Alert(Alert.AlertType.WARNING);
			if(locale.equals("en")) {
				varoitus.setTitle("Failure");
				varoitus.setHeaderText("Warning");
			}else {
				varoitus.setTitle("Virhe");
				varoitus.setHeaderText("Varoitus");
			}
			varoitus.setContentText(virhe.toString());
			varoitus.showAndWait();
			return false;
		}
		return true;
	}

	public boolean tallennaClicked() {
		return tallennaClicked;
	}

	@FXML
	void vieEtusivulle(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		main.showEtusivu();

	}

	@FXML
	void vieProfiiliNäkymä(ActionEvent event) throws IOException {
		main.showProfile();
	}

	@FXML
	void vieTapahtumat(ActionEvent event) throws IOException {
		main.tapahtumatSivuOverview();
	}

	@FXML
	void LogOut(ActionEvent event) throws IOException {
		boolean test = TiedostoKasittely.poistaTiedosto();
		if (test == true) {
			// Viedään kirjautumissivulle
			main.showLogin();
		}
	}
}
