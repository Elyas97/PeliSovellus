package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;
import view.MainApp;

public class EtusivuController {

	@FXML
	private ListView<Peli> lista;
	@FXML
	private MainApp mainApp;
	@FXML
	private Label pelinNimi;
	@FXML
	private Label pelinHinta;
	@FXML
	private Label paikkakunta;
	@FXML
	private Label genre;
	@FXML
	private Label ikäraja;
	@FXML
	private Label pelaajamäärä;
	@FXML
	private Label kuvaus;
	@FXML
	private Label päivämäärä;
	@FXML
	private Label tekstikenttä;
	@FXML
	private TextField pelihaku;
	@FXML
	private Label konsoli;
	@FXML
	private ComboBox<String> hakurajaus;
	@FXML
	private ToggleGroup hakutyyppi;
	@FXML
	private RadioButton kaikki;
	@FXML
	private RadioButton myynti;
	@FXML
	private RadioButton vuokraus;
	@FXML
	private RadioButton lahjoitus;
	@FXML
	private AnchorPane rajaahakuNäkymä;
	@FXML
	private ToggleGroup julkaisu;
	@FXML
	private RadioButton sell;
	@FXML
	private RadioButton rent;
	@FXML
	private RadioButton free;
	@FXML
	private TextField maara;
	@FXML
	private RadioButton uusin;
	@FXML
	private RadioButton vanhin;
	@FXML
	private TextField minimi;
	@FXML
	private TextField maxnum;
	@FXML
	private ChoiceBox<String> valinnat;
	@FXML
	private Label hintaOtsikko2;
	@FXML
	private Label hintaOtsikko;
	@FXML
	private Text hintaLabel;
	@FXML
	private RadioButton alhaisinhinta;
	@FXML
	private RadioButton korkeinhinta;

	private Stage dialogStage;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	ObservableList<Peli> pelidata = FXCollections.observableArrayList();
	FilteredList<Peli> filteredData = new FilteredList<>(pelidata, pelit -> true);

	public void initialize() {
		ObservableList<String> valinta = FXCollections.observableArrayList("3", "7", "12", "16", "18");
		valinnat.setItems(valinta);

		ObservableList<String> rajaus = FXCollections.observableArrayList("Nimi", "Kaupunki", "Genre");
		hakurajaus.setItems(rajaus);
		lista.setItems(filteredData);
		hakutyyppi();
		//hakurajaus.setPromptText("Rajaa hakua");
		listaaPelit();
		hakuTesti();
		// Kuuntelee pelihaku kenttää ja päivittää listaa kirjoitetun tekstin mukaan
		/*pelihaku.textProperty().addListener((obs, oldValue, newValue) -> {
			if (hakurajaus.getValue() != null) {
				switch (hakurajaus.getValue()) {
				case "Nimi":
					filteredData.setPredicate(pelit -> pelit.getPelinNimi().toLowerCase().contains(newValue));
					break;
				case "Kaupunki":
					filteredData.setPredicate(pelit -> pelit.getKaupunki().toLowerCase().contains(newValue));
					break;
				case "Genre":
					filteredData.setPredicate(pelit -> pelit.getGenre().toLowerCase().contains(newValue));
					break;
				default:
					filteredData.setPredicate(pelit -> true);
				}
			}
		});*/
		// Kuuntelee listauksessa olevia kenttiä ja välittää tiedot pelinTiedot metodiin
		lista.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> pelinTiedot(newValue));

		// ObservableList<String> hinnanmukaan =
		// FXCollections.observableArrayList("Alhaisimmasta korkeimpaan", "Korkeimmasta
		// alhaisimpaan");

	}
	public void hakuTesti() {
		pelihaku.textProperty().addListener((obs, oldValue, newValue) -> {
			if (hakurajaus.getValue() != null) {
				switch (hakurajaus.getValue()) {
				case "Nimi":
					filteredData.setPredicate(pelit -> pelit.getPelinNimi().toLowerCase().contains(newValue));
					break;
				case "Kaupunki":
					filteredData.setPredicate(pelit -> pelit.getKaupunki().toLowerCase().contains(newValue));
					break;
				case "Genre":
					filteredData.setPredicate(pelit -> pelit.getGenre().toLowerCase().contains(newValue));
					break;
				default:
					filteredData.setPredicate(pelit -> true);
				}
			}
		});
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/*
	 * Hakee pelit tietokannasta ja asettaa ne listaan
	 */
	@FXML
	public void listaaPelit() {
		pelidata.clear();
		pelit = pelitdao.haePelit();
		for (int i = 0; i < pelit.length; i++) {
			pelidata.add(pelit[i]);
		}
	}

	/*
	 * Asettaa valitun pelin tiedot GridPanen labeleihin
	 */
	private void pelinTiedot(Peli peli) {
		if (peli != null) {
			pelinNimi.setText(peli.getPelinNimi());
			pelinHinta.setText(Integer.toString(peli.getHinta()));
			paikkakunta.setText(peli.getKaupunki());
			genre.setText(peli.getGenre());
			ikäraja.setText(Integer.toString(peli.getIkaraja()));
			konsoli.setText(peli.getKonsoli());
			pelaajamäärä.setText(Integer.toString(peli.getPelmaara()));
			kuvaus.setText(peli.getKuvaus());
			tekstikenttä.setText(peli.getTekstikenttä());
			
			//Päivämäärän formatointi
			DateFormat dateFormat;
			dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
			String paivamaaraFormat = dateFormat.format(peli.getPaiva()); 
			päivämäärä.setText("" + paivamaaraFormat);
			
			//päivämäärä.setText("Ilmoitus jätetty: " + peli.getPaiva().toString());
		} else {
			pelinNimi.setText("");
			pelinHinta.setText("");
			paikkakunta.setText("");
			genre.setText("");
			ikäraja.setText("");
			pelaajamäärä.setText("");
			kuvaus.setText("");
			päivämäärä.setText("");
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	void tyyppiAction(ActionEvent event) {
		String text = ((RadioButton) julkaisu.getSelectedToggle()).getText();

		if (text.equals("Lahjoitus")) {
			hintaOtsikko.setVisible(false);
			hintaOtsikko2.setVisible(false);
			minimi.setVisible(false);
			maxnum.setVisible(false);
			hintaLabel.setVisible(false);
		} else if (text.equals("Myynti")) {
			hintaOtsikko.setVisible(true);
			hintaLabel.setVisible(true);
			hintaOtsikko2.setVisible(true);
			minimi.setVisible(true);
			maxnum.setVisible(true);
		} else if (text.equals("Vuokraus")) {
			hintaOtsikko.setVisible(true);
			hintaLabel.setVisible(true);
			hintaOtsikko2.setVisible(true);
			minimi.setVisible(true);
			maxnum.setVisible(true);
		}
	}
	/*
	 * Kuuntelee radiobuttoneita etusivulla ja rajaa listan valikoimaa niiden mukaan
	 * Kutsutaan initializessa
	 */

	public void hakutyyppi() {
		kaikki.setSelected(true);
		hakutyyppi.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> obs, Toggle oldT, Toggle newT) {
				switch (((RadioButton) hakutyyppi.getSelectedToggle()).getText()) {
				case "Myynti":
				case "For sale":
					
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Myynti"));
					hakuTesti();
					break;
				case "Vuokrataan":
				case "Rent":
					hakuTesti();
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Vuokraus"));
					//hakuTesti();
					break;
				case "Lahjoitetaan":
				case "Giveaway":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Lahjoitus"));
					break;
				case "Kaikki":
				case "All":
					filteredData.setPredicate(pelit -> true);
					break;
				}
			}
		});
	}

	@FXML
	public void uusiPeli(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));
		
		Locale locale = new Locale("en", "FI");
		ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
		loader.setResources(bundle);
		
		BorderPane personOverview = (BorderPane) loader.load();
		Scene etusivulle = new Scene(personOverview);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(etusivulle);
		window.show();
	}

	@FXML
	public void handletapahtumatSivu(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));
		
		Locale locale = new Locale("en", "FI");
		ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
		loader.setResources(bundle);
		
		BorderPane personOverview = (BorderPane) loader.load();
		Scene etusivulle = new Scene(personOverview);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(etusivulle);
		window.show();
	}

	@FXML
	void vieProofiliNäkymään(ActionEvent event) throws IOException {
		// Vaihdetaan näkymää samalla viedään käyttäjän tiedot
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Profiili.fxml"));
		
		Locale locale = new Locale("en", "FI");
		ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
		loader.setResources(bundle);
		
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
			
			Locale locale = new Locale("en", "FI");
			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
			loader.setResources(bundle);
			
			BorderPane etusivu = (BorderPane) loader.load();
			Scene kirjautumisNäkymä = new Scene(etusivu);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(kirjautumisNäkymä);
			window.show();
		}
	}

	@FXML
	void peruuta(ActionEvent event) {
		rajaahakuNäkymä.setVisible(false);
	}

	@FXML
	void suljeRajaus(ActionEvent event) {
		rajaahakuNäkymä.setVisible(false);
	}

	@FXML
	void avaaRajaus(ActionEvent event) {
		rajaahakuNäkymä.setVisible(true);
	}

	// Jää toteutukseen OTP2
	@FXML
	void Rajaa(ActionEvent event) {

		
		if(vanhin.isSelected()) {
			System.out.println("Vanhin");
			Collections.sort(filteredData.getSource(), (a, b) -> a.getPaiva().compareTo(b.getPaiva()));
		}
	
		if(uusin.isSelected()) {
			System.out.println("Uusin");
			Collections.sort(filteredData.getSource(), (a, b) -> b.getPaiva().compareTo(a.getPaiva()));
		}
		
		if(!minimi.getText().isEmpty() && !maxnum.getText().isEmpty() && valinnat.getValue() != null && !maara.getText().isEmpty() ) {
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getIkaraja()).contains(valinnat.getValue()) && Integer.toString(pelit.getPelmaara()).contains(maara.getText()) &&
					pelit.getHinta() >= Integer.parseInt(minimi.getText()) && pelit.getHinta() <= Integer.parseInt(maxnum.getText()));
		}	
	
		else if(valinnat.getValue() != null) {
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getIkaraja()).contains(valinnat.getValue()));
		}
		else if(!maara.getText().isEmpty()) {
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getPelmaara()).contains(maara.getText()));
		}

		else if(valinnat.getValue() != null && !maara.getText().isEmpty()) {
			System.out.println("Toimii");
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getIkaraja()).contains(valinnat.getValue()) && Integer.toString(pelit.getPelmaara()).contains(maara.getText()));
		}
		
		else if(!minimi.getText().isEmpty() && !maxnum.getText().isEmpty()) {
		filteredData.setPredicate(pelit -> pelit.getHinta() >= Integer.parseInt(minimi.getText()) && pelit.getHinta() <= Integer.parseInt(maxnum.getText()));
		//filteredData.setPredicate(pelit -> pelit.getHinta() < Integer.parseInt(maxnum.getText()));
		}
	
		
		//Collections.sort(filteredData.getSource(), (a, b) -> a.getPaiva().compareTo(b.getPaiva()));
		
		
		
		
	


		for (int i = 0; i < pelit.length; i++) {
			pelidata.add(pelit[i]);
		}
		
		// sulje ikkuna
		rajaahakuNäkymä.setVisible(false);

	}
	
	
	/*
	 * Testailua 
	 * Alla aikasemmin rajaa funktiossa olleet toiminnot
	 * 
	 */

	public void pelaajaMaara() {
		if(!maara.getText().trim().isEmpty()) {
			int pelmaara = Integer.parseInt(maara.getText());
			pelit = pelitdao.pelaajaMaara(pelmaara);
			}
	}
	public void ikaraja() {
		if(valinnat.getValue() != null ) {
		int ikaraja = 0;
		for (int i = 0; i < pelit.length; i++) {
			if (valinnat.getValue().toString() == "3") {
				ikaraja = 3;
				pelit = pelitdao.haePelitIkaraja(ikaraja);
			} else if (valinnat.getValue().toString() == "7") {
				ikaraja = 7;
				pelit = pelitdao.haePelitIkaraja(ikaraja);
			} else if (valinnat.getValue().toString() == "12") {
				ikaraja = 12;
				pelit = pelitdao.haePelitIkaraja(ikaraja);	
			}
			//jne
		}
		
		}
	}

	// Listan järjestys hinnan mukaan
	@FXML
	public void AlhaisinHinta() {
		korkeinhinta.setSelected(false);
		pelit = pelitdao.haePelit();
		int i, j, pienin;
		Peli apu;

		for (i = 0; i < pelit.length; i++) {
			pienin = i;
			for (j = i + 1; j < pelit.length; j++) {
				if (pelit[j].getHinta() < pelit[pienin].getHinta()) {
					pienin = j;
				}
			}
			if (pienin != i) {
				apu = pelit[pienin];
				pelit[pienin] = pelit[i];
				pelit[i] = apu;
			}
		}
		pelidata.clear();
		for (int a = 0; a < pelit.length; a++) {
			pelidata.add(pelit[a]);
		}
	}

	@FXML
	public void KorkeinHinta() {
		alhaisinhinta.setSelected(false);
		pelit = pelitdao.haePelit();
		int i, j, suurin;
		Peli apu;

		for (i = 0; i < pelit.length; i++) {
			suurin = i;
			for (j = i + 1; j < pelit.length; j++) {
				if (pelit[j].getHinta() > pelit[suurin].getHinta()) {
					suurin = j;
				}
			}
			if (suurin != i) {
				apu = pelit[suurin];
				pelit[suurin] = pelit[i];
				pelit[i] = apu;
			}
		}
		pelidata.clear();
		for (int a = 0; a < pelit.length; a++) {
			pelidata.add(pelit[a]);
		}
	}

	// Jää toteutukseen OTP2
	private boolean validoiRajaus() {
		boolean test = true;
		try {
			int min = Integer.parseInt(minimi.getText());
			int max = Integer.parseInt(maxnum.getText());

			if (min < 0 || min > max) {
				test = false;
				minimi.setStyle("-fx-border-color:red");
				minimi.setPromptText("Ei negativiinen/ei isompi kun max numero");
			}

		} catch (NumberFormatException e) {
			System.out.println(e);
			minimi.setStyle("-fx-border-color:red");
			minimi.setPromptText("numero");
			test = false;
		}
		try {
			int pelaajat = Integer.parseInt(maara.getText());
			if (pelaajat < 0) {
				test = false;
				maara.setStyle("-fx-border-color:red");
				maara.setPromptText("Syötä positiivinen");
			}
		} catch (NumberFormatException e) {
			maara.setText("");
			System.out.println(e);
		}
		return test;
	}
}
