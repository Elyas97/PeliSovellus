package controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Kayttaja;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;
import view.MainApp;

public class TapahtumatController {

	@FXML
	private ListView<Peli> omatPelit;
	@FXML
	private RadioButton myynti;
	@FXML
	private ToggleGroup tyyppi;
	@FXML
	private RadioButton vuokraus;
	@FXML
	private RadioButton lahjoitus;
	@FXML
	private TextField hinta;
	@FXML
	private TextField pelinnimi;
	@FXML
	private ToggleGroup pelintyyppi;
	@FXML
	private RadioButton video;
	@FXML
	private RadioButton lauta;
	@FXML
	private TextField kaupunki;
	@FXML
	private TextField ikaraja;
	@FXML
	private TextField pelaajamaara;
	@FXML
	private TextArea kuvaus;
	@FXML
	private TextArea tekstikenttä;
	@FXML
	private Text nimivaroitus;
	@FXML
	private Text hintavaroitus;
	@FXML
	private Text tyyppivaroitus;
	@FXML
	private Text paikkakuntavaroitus;
	@FXML
	private Text pelintyyppivaroitus;
	@FXML
	private Text konsolivaroitus;
	@FXML
	private Text genrevaroitus;
	@FXML
	private Text ikärajavaroitus;
	@FXML
	private Text pelaajamäärävaroitus;
	@FXML
	private Text kuntovaroitus;
	@FXML
	private Text kuvausvaroitus;
	@FXML
	private Text tekstikenttävaroitus;
	@FXML
	private ChoiceBox<String> genre;
	@FXML
	private ChoiceBox<String> kunto;
	@FXML
	private ChoiceBox<String> konsoli;
	@FXML
	private Pane konsoliPane;
	@FXML
	private Label kirjaimet;

	private Kayttaja käyttäjä;
	private Stage dialogStage;
	private MainApp main;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	String locale = Locale.getDefault().getLanguage();

	public TapahtumatController() {
	}

	@FXML
	public void initialize() {
		// Kirjautuneen käyttäjän haku
		this.käyttäjä = TiedostoKasittely.lueKäyttäjä();

		if (locale.equals("en")) {
			// Tallentuu myös tietokantaan englanniksi, pitäisiköhän kääntää uudelleen
			// suomeksi?
			ObservableList<String> options = FXCollections.observableArrayList("Sports", "Shooting", "Action", "Racing",
					"Horror", "Adventure", "Strategy", "Roleplay", "Puzzle", "Party", "Boardgame");
			genre.setItems(options);

			ObservableList<String> kuntoOptions = FXCollections.observableArrayList("Excellent", "Great", "Good",
					"Moderate", "Passable");
			kunto.setItems(kuntoOptions);
		} else {
			ObservableList<String> options = FXCollections.observableArrayList("Urheilu", "Räiskintä", "Toiminta",
					"Ajopeli", "Jännitys", "Seikkailu", "Strategia", "Roolipeli", "Pulma", "Seurapeli", "Lautapeli");
			genre.setItems(options);

			ObservableList<String> kuntoOptions = FXCollections.observableArrayList("Erinomainen", "Kiitettävä", "Hyvä",
					"Kohtalainen", "Välttävä");
			kunto.setItems(kuntoOptions);
		}

		ObservableList<String> konsoliOptions = FXCollections.observableArrayList("Xbox", "Playstation", "Wii");
		konsoli.setItems(konsoliOptions);

		listaaOmatPelit();
		omatPelit.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> pelinTiedot(newValue));

		kirjaimet();
		tekstikenttä.textProperty().addListener((observable, oldValue, newValue) -> {
			kirjaimet();
		});
		
		validointiPiiloon();
	}
	
	@FXML
	public String ilmoituksenTyyppiAction(ActionEvent Action) {
		String text = ((RadioButton) tyyppi.getSelectedToggle()).getText();
		
		tyyppivaroitus.setText("");
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

		
		pelintyyppivaroitus.setText("");
		// Konsolivalinta ilmestyy vain jos valitaan videopeli
		if (text.equals("lauta") || text.equals("boardgame")) {
			konsoliPane.setVisible(false);
		} else {
			konsoliPane.setVisible(true);
		}
		return text;
	}

	public void setMainApp(MainApp main) {
		this.main = main;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/*
	 * Käyttäjän omien lisättyjen pelien listaaminen
	 */
	public void listaaOmatPelit() {
		käyttäjä = TiedostoKasittely.lueKäyttäjä();
		pelit = pelitdao.haeOmatPelit(käyttäjä.getKayttajaID());
		for (int i = 0; i < pelit.length; i++) {
			System.out.println(pelit[i]);
			omatPelit.getItems().add(pelit[i]);
		}
	}

	/*
	 * Pelin poistaminen
	 */
	@FXML
	public void poistaPeli() {

		Alert varmistus;
		ButtonType ok;
		ButtonType peruuta;
		if (locale.equals("en")) {
			ok = new ButtonType("Ok", ButtonData.OK_DONE);
			peruuta = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			
			varmistus = new Alert(Alert.AlertType.CONFIRMATION,
					"Are you sure you want to delete the game? Deletion cannot be undone.", ok, peruuta);
			varmistus.setTitle("Alert");
		}else {
			ok = new ButtonType("Ok", ButtonData.OK_DONE);
			peruuta = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
			
			varmistus = new Alert(Alert.AlertType.CONFIRMATION,
					"Haluatko varmasti poistaa pelin? Poistamista ei voi peruuttaa.", ok, peruuta);
			varmistus.setTitle("Alert");
		}
		Optional<ButtonType> vastaus = varmistus.showAndWait();

		if (vastaus.get() == ok) {
			Peli peli = omatPelit.getSelectionModel().getSelectedItem();
			poistaPeliTietokannasta(peli);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			if (locale.equals("en")) {
				alert.setTitle("Information");
				alert.setContentText("Game deleted succesfully!");
			}else {
				alert.setTitle("Tiedoksi");
				alert.setContentText("Pelin poistaminen onnistui!");
			}
			alert.showAndWait();
		}
	}

	/*
	 * Pelin poistaminen tietokannasta
	 */
	public void poistaPeliTietokannasta(Peli peli) {
		int id = peli.getPeliId();
		pelitdao.poistaPeli(id);

		// Peli poistuu listasta heti
		omatPelit.getItems().clear();
		listaaOmatPelit();
	}

	/*
	 * Varoitukset piiloon kun klikataan toista peliä
	 */
	private void varoituksetPiiloon() {
		nimivaroitus.setText("");
		hintavaroitus.setText("");
		tyyppivaroitus.setText("");
		pelintyyppivaroitus.setText("");
		genrevaroitus.setText("");
		konsolivaroitus.setText("");
		paikkakuntavaroitus.setText("");
		ikärajavaroitus.setText("");
		kuvausvaroitus.setText("");
		tekstikenttävaroitus.setText("");
		pelaajamäärävaroitus.setText("");
		kuntovaroitus.setText("");

		// Punainen väri reunoille
		pelinnimi.setStyle("-fx-border-color:none");
		hinta.setStyle("-fx-border-color:none");
		kaupunki.setStyle("-fx-border-color:none");
		kunto.setStyle("-fx-border-color:none");
		genre.setStyle("-fx-border-color:none");
		pelaajamaara.setStyle("-fx-border-color:none");
		ikaraja.setStyle("-fx-border-color:none");
		kuvaus.setStyle("-fx-border-color:none");
		tekstikenttä.setStyle("-fx-border-color:none");
		konsolivaroitus.setStyle("-fx-border-color:none");
	}

	/*
	 * Pelin tietojen näyttäminen
	 */
	private void pelinTiedot(Peli peli) {

		varoituksetPiiloon();
		if (peli != null) {
			pelinnimi.setText(peli.getPelinNimi());
			hinta.setText(Integer.toString(peli.getHinta()));
			String pelintalletusstring = peli.getTalletusTyyppi();

			if (pelintalletusstring.equals("Myynti")) {
				tyyppi.selectToggle(myynti);
			} else if (pelintalletusstring.equals("Lahjoitus")) {
				tyyppi.selectToggle(lahjoitus);
				// Hinta aina nolla kun kyseessä lahjoitus
				hinta.setEditable(false);
			} else {
				tyyppi.selectToggle(vuokraus);
			}

			String pelintyyppistring = peli.getPelintyyppi();
			if (pelintyyppistring.equals("lauta")) {
				pelintyyppi.selectToggle(lauta);
				konsoliPane.setVisible(false);
			} else {
				pelintyyppi.selectToggle(video);
				konsoliPane.setVisible(true);
			}

			kaupunki.setText(peli.getKaupunki());
			genre.setValue(peli.getGenre());
			kunto.setValue(peli.getKunto());
			ikaraja.setText(Integer.toString(peli.getIkaraja()));
			pelaajamaara.setText(Integer.toString(peli.getPelmaara()));
			kuvaus.setText(peli.getKuvaus());
			tekstikenttä.setText(peli.getTekstikenttä());
			konsoli.setValue(peli.getKonsoli());
		} else {
			pelinnimi.setText("");
			hinta.setText("");
			kaupunki.setText("");
			genre.setValue("");
			ikaraja.setText("");
			pelaajamaara.setText("");
			kuvaus.setText("");
			tekstikenttä.setText("");
			konsoli.setValue("");
		}
	}

	/*
	 * Pelin tietojen muutosten tallennus
	 */
	@FXML
	public void tallennaMuutokset() {
		if (taytaTyhjatKentat() == true) {
			Peli peli = new Peli();
			Peli peliId = omatPelit.getSelectionModel().getSelectedItem();
			peli.setPeliId(peliId.getPeliId());
			peli.setPelinNimi(pelinnimi.getText());
			int price = Integer.parseInt(hinta.getText());
			peli.setHinta(price);
			int age = Integer.parseInt(ikaraja.getText());
			peli.setIkaraja(age);
			peli.setKaupunki(kaupunki.getText());
			String tyyppiText = ((RadioButton) tyyppi.getSelectedToggle()).getText();
			System.out.println("TALLETUKSEN TYYPPI:" + tyyppiText);
			peli.setTalletusTyyppi(tyyppiText);
			peli.setKuvaus(kuvaus.getText());
			int players = Integer.parseInt(pelaajamaara.getText());
			peli.setPelmaara(players);
			String pelintyyppiText = ((RadioButton) pelintyyppi.getSelectedToggle()).getText();
			peli.setPelinTyyppi(pelintyyppiText);
			peli.setGenre(genre.getValue().toString());
			System.out.println(genre.getValue().toString());
			peli.setKunto(kunto.getValue().toString());
			System.out.println(peli.getPelinNimi() + " " + peli.getHinta() + " " + peli.getIkaraja());
			peli.setTekstikenttä(tekstikenttä.getText());

			if (peli.getKonsoli() == null) {
				peli.setKonsoli(konsoli.getValue().toString());
			} else {
				peli.setKonsoli("");
			}
			pelitdao.paivitaPeli(peli);

			// Ilmoitus onnistuneesta tallennuksesta
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			if(locale.equals("en")) {
				alert.setTitle("Information");
				alert.setContentText("Changes saved succesfully!");
			}else {
				alert.setTitle("Information");
				alert.setContentText("Muutokset tallennettu onnistuneesti!");
			}
			alert.showAndWait();

			omatPelit.getItems().clear();
			listaaOmatPelit();
		}
	}

	/*
	 * Muutoslomakkeen validointi Tyhjistä kentistä huomautetaan alertilla ja
	 * punaisella värillä
	 */
	public boolean taytaTyhjatKentat() {

		boolean kehotus = false;

		if (pelinnimi.getText().trim().isEmpty()) {
			pelinnimi.setStyle("-fx-border-color:red");
			nimivaroitus.setVisible(true);
			kehotus = true;
		}
		if (hinta.getText().trim().isEmpty()) {
			hinta.setStyle("-fx-border-color:red");
			hintavaroitus.setVisible(true);
			kehotus = true;
		}
		if (kaupunki.getText().trim().isEmpty()) {
			kaupunki.setStyle("-fx-border-color:red");
			paikkakuntavaroitus.setVisible(true);
			kehotus = true;
		}
		if (ikaraja.getText().trim().isEmpty()) {
			ikaraja.setStyle("-fx-border-color:red");
			ikaraja.setVisible(true);
			kehotus = true;
		}
		if (kuvaus.getText().trim().isEmpty()) {
			kuvaus.setStyle("-fx-border-color:red");
			kuvausvaroitus.setVisible(true);
			kehotus = true;
		}
		if (tekstikenttä.getText().trim().isEmpty()) {
			tekstikenttä.setStyle("-fx-border-color:red");
			tekstikenttävaroitus.setVisible(true);
			kehotus = true;
		}
		if (kunto.getValue() == null) {
			kunto.setStyle("-fx-border-color:red");
			kuntovaroitus.setVisible(true);
			kehotus = true;
		}
		if (genre.getValue() == null) {
			genre.setStyle("-fx-border-color:red");
			genrevaroitus.setVisible(true);
			kehotus = true;
		}
		if (tyyppi.getSelectedToggle() == null) {
			tyyppivaroitus.setVisible(true);
			kehotus = true;
		}
		if (pelintyyppi.getSelectedToggle() == null) {
			pelintyyppivaroitus.setVisible(true);
			kehotus = true;
		}
		if(pelaajamaara.getText().trim().isEmpty()) {
            pelaajamaara.setStyle("-fx-border-color:red");
            pelaajamäärävaroitus.setVisible(true);
            kehotus = true;
        }
		if (kehotus == true) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			if(locale.equals("en")) {
				alert.setTitle("Information");
				alert.setContentText("Fill in the required fields!");
			}else {
				alert.setTitle("Alert");
				alert.setContentText("Täytä pakolliset kentät!");
			}
			alert.showAndWait();
			return false;
		}
		return true;
	}
	
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
			ikärajavaroitus.setText("");
			ikaraja.setStyle("-fx-border:none");
		});
		pelaajamaara.textProperty().addListener((obs, oldValue, newValue) -> {
			pelaajamäärävaroitus.setText("");
			pelaajamaara.setStyle("-fx-border:none");
		});
		kuvaus.textProperty().addListener((obs, oldValue, newValue) -> {
			kuvausvaroitus.setText("");
			kuvaus.setStyle("-fx-border:none");
		});
		tekstikenttä.textProperty().addListener((obs, oldValue, newValue) -> {
			tekstikenttävaroitus.setText("");
			tekstikenttä.setStyle("-fx-border:none");
		});
		

		// setOnAction "kuuntelee" valintaa ja kun valittu asettaa varoitustekstin pois
		genre.setOnAction((event) -> {
			genrevaroitus.setText("");
			genre.setStyle("-fx-border:none");
			
		});
		kunto.setOnAction((event) -> {
			kuntovaroitus.setText("");
			kunto.setStyle("-fx-border:none");
		});
	}

	/*
	 * Tekstikentän kirjainmäärälaskuri
	 */
	@FXML
	public void kirjaimet() {
		String kirjaimetstring = tekstikenttä.getText();
		int maxpituus = 200;
		int pituus = 0;
		pituus = kirjaimetstring.length();
		int jaljella = maxpituus - pituus;

		if (jaljella <= 0) {
			kirjaimet.setText("" + 0);

			// Tekstikenttään ei voi kirjoittaa
			tekstikenttä.setEditable(false);

			// Ilmoitus kun tekstikenttä täynnä jolloin kirjoitus ei enää onnistu
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			if(locale.equals("en")) {
				alert.setTitle("Information");
				alert.setContentText("Textfield is full!");
			}else {
				alert.setTitle("Alert");
				alert.setContentText("Tekstikenttä täynnä!");
			}
			alert.showAndWait();

			// Tekstikenttään voi taas kirjoittaa
			tekstikenttä.setEditable(true);
		} else {
			kirjaimet.setText("" + jaljella);
		}
	}

	@FXML
	void vieEtusivulle(ActionEvent event) throws IOException {
		main.showEtusivu();
	}

	@FXML
	void lisaaUusiPeliNäkymä(ActionEvent event) throws IOException {
		main.lisaaPeliOverview();
	}

	@FXML
	void vieProfiiliNäkymään(ActionEvent event) throws IOException {
		main.showProfile();
	}

	@FXML
	void LogOut(ActionEvent event) throws IOException {
		boolean test = TiedostoKasittely.poistaTiedosto();
		if (test == true) {
			main.showLogin();
		}
	}

}