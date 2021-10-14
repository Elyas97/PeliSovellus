package view;

import java.io.IOException;
import java.util.Optional;
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

	public TapahtumatController() {
	}

	@FXML
	public void initialize() {
		// Kirjautuneen käyttäjän haku
		this.käyttäjä = TiedostoKasittely.lueKäyttäjä();

		ObservableList<String> options = FXCollections.observableArrayList("Urheilu", "Räiskintä", "Toiminta",
				"Ajopeli", "Jännitys", "Seikkailu", "Strategia", "Roolipeli", "Pulma", "Lautapeli", "Juomapeli",
				"Tappelupeli", "Tasohyppeli");
		genre.setItems(options);

		ObservableList<String> kuntoOptions = FXCollections.observableArrayList("Erinomainen", "Kiitettävä", "Hyvä",
				"Kohtalainen", "Välttävä");
		kunto.setItems(kuntoOptions);

		ObservableList<String> konsoliOptions = FXCollections.observableArrayList("Xbox", "Playstation", "Wii");
		konsoli.setItems(konsoliOptions);

		listaaOmatPelit();
		omatPelit.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> pelinTiedot(newValue));

		kirjaimet();
		tekstikenttä.textProperty().addListener((observable, oldValue, newValue) -> {
			kirjaimet();
		});
	}

	@FXML
	public String tyyppiAction(ActionEvent Action) {
		String text = ((RadioButton) tyyppi.getSelectedToggle()).getText();
		System.out.println(text);
		if (text.equals("Lahjoitus")) {
			hinta.setText(Integer.toString(0));
			hinta.setEditable(false);
		} else {
			hinta.setText("");
			hinta.setEditable(true);
		}
		return text;
	}

	@FXML
	public String tyyppi(ActionEvent Action) {
		String text = ((RadioButton) pelintyyppi.getSelectedToggle()).getText();

		// Konsolivalinta ilmestyy vain jos valitaan videopeli
		if (text.equals("lauta")) {
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

		ButtonType ok = new ButtonType("Ok", ButtonData.OK_DONE);
		ButtonType peruuta = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);

		Alert varmistus = new Alert(Alert.AlertType.CONFIRMATION,
				"Haluatko varmasti poistaa pelin? Poistamista ei voi peruuttaa.", ok, peruuta);
		varmistus.setTitle("Alert");
		Optional<ButtonType> vastaus = varmistus.showAndWait();

		if (vastaus.get() == ok) {
			Peli peli = omatPelit.getSelectionModel().getSelectedItem();
			poistaPeliTietokannasta(peli);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setContentText("Pelin poistaminen onnistui!");
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
			alert.setTitle("Alert");
			alert.setContentText("Muutokset tallennettu onnistuneesti!");
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
			nimivaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (hinta.getText().trim().isEmpty()) {
			hinta.setStyle("-fx-border-color:red");
			hintavaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (kaupunki.getText().trim().isEmpty()) {
			kaupunki.setStyle("-fx-border-color:red");
			kaupunki.setPromptText("Pakollinen kenttä!");
			paikkakuntavaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (ikaraja.getText().trim().isEmpty()) {
			ikaraja.setStyle("-fx-border-color:red");
			ikaraja.setPromptText("Pakollinen kenttä!");
			ikaraja.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (kuvaus.getText().trim().isEmpty()) {
			kuvaus.setStyle("-fx-border-color:red");
			kuvausvaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (tekstikenttä.getText().trim().isEmpty()) {
			tekstikenttä.setStyle("-fx-border-color:red");
			tekstikenttävaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (kunto.getValue() == null) {
			kunto.setStyle("-fx-border-color:red");
			kuntovaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (genre.getValue() == null) {
			genre.setStyle("-fx-border-color:red");
			genrevaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (tyyppi.getSelectedToggle() == null) {
			tyyppivaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (pelintyyppi.getSelectedToggle() == null) {
			pelintyyppivaroitus.setText("Pakollinen kenttä");
			kehotus = true;
		}
		if (kehotus == true) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setContentText("Täytä pakolliset kentät!");
			alert.showAndWait();
			return false;
		}
		return true;
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
			kirjaimet.setText("Kirjaimia jäljellä: " + 0);

			// Tekstikenttään ei voi kirjoittaa
			tekstikenttä.setEditable(false);

			// Ilmoitus kun tekstikenttä täynnä jolloin kirjoitus ei enää onnistu
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setContentText("Tekstikenttä täynnä!");
			alert.showAndWait();

			// Tekstikenttään voi taas kirjoittaa
			tekstikenttä.setEditable(true);
		} else {
			kirjaimet.setText("Kirjaimia jäljellä: " + jaljella);
		}
	}

	@FXML
	void vieEtusivulle(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));
		BorderPane personOverview = (BorderPane) loader.load();
		Scene etusivulle = new Scene(personOverview);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(etusivulle);
		window.show();
	}

	@FXML
	void lisaaUusiPeliNäkymä(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));
		BorderPane personOverview = (BorderPane) loader.load();
		Scene etusivulle = new Scene(personOverview);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(etusivulle);
		window.show();
	}

	@FXML
	void vieProfiiliNäkymään(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Profiili.fxml"));
		BorderPane personOverview = (BorderPane) loader.load();
		Scene etusivulle = new Scene(personOverview);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(etusivulle);
		window.show();
	}

	@FXML
	void LogOut(ActionEvent event) throws IOException {
		boolean test = TiedostoKasittely.poistaTiedosto();
		if (test == true) {
			// Viedään kirjautumissivulle
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));

			BorderPane etusivu = (BorderPane) loader.load();
			Scene kirjautumisNäkymä = new Scene(etusivu);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(kirjautumisNäkymä);
			window.show();
		}
	}

}