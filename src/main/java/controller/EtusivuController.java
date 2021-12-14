package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Alert;
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
    private Label kunto;
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
	@FXML
	private RadioButton uusin2;
	@FXML
	private RadioButton vanhin2;
	@FXML
	private ToggleGroup julkaisuAika;

	private Stage dialogStage;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	ObservableList<Peli> pelidata = FXCollections.observableArrayList();
	FilteredList<Peli> filteredData = new FilteredList<>(pelidata, pelit -> true);
	String locale = Locale.getDefault().getLanguage();

	public void initialize() {
		ObservableList<String> valinta = FXCollections.observableArrayList("3", "7", "12", "16", "18");
		valinnat.setItems(valinta);

		if(locale.equals("en")) {
			ObservableList<String> rajaus = FXCollections.observableArrayList("Name", "City", "Genre");
			hakurajaus.setItems(rajaus);
		}else {
			ObservableList<String> rajaus = FXCollections.observableArrayList("Nimi", "Kaupunki", "Genre");
			hakurajaus.setItems(rajaus);
		}
		
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
	
	/**
	 * Kuuntelee etusivun valikkoa ja rajaa etusivun listaa kirjoitetun mukaan
	 */
	public void hakuTesti() {
		pelihaku.textProperty().addListener((obs, oldValue, newValue) -> {
			if (hakurajaus.getValue() != null) {
				switch (hakurajaus.getValue()) {
				case "Nimi":
				case "Name":
					filteredData.setPredicate(pelit -> pelit.getPelinNimi().toLowerCase().contains(newValue));
					break;
				case "Kaupunki":
				case "City":
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
            kunto.setText(peli.getKunto());
            kuvaus.setText(peli.getKuvaus());
            tekstikenttä.setText(peli.getTekstikenttä());
            DateFormat dateFormat;
            if(locale.equals("en")) {
                //Päivämäärän formatointi
                
                dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
                String paivamaaraFormat = dateFormat.format(peli.getPaiva()); 
                päivämäärä.setText("" + paivamaaraFormat);
            }else {
            	dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            	 String paivamaaraFormat = dateFormat.format(peli.getPaiva()); 
                päivämäärä.setText(""+paivamaaraFormat);
            }

        } else {
            pelinNimi.setText("");
            pelinHinta.setText("");
            paikkakunta.setText("");
            genre.setText("");
            ikäraja.setText("");
            pelaajamäärä.setText("");
            kunto.setText("");
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
		
		switch (text) {
		case "Lahjoitus":
		case "Giveaway":
			hintaOtsikko.setVisible(false);
			hintaOtsikko2.setVisible(false);
			minimi.setVisible(false);
			maxnum.setVisible(false);
			hintaLabel.setVisible(false);
			break;
		case "Myynti":
		case "Sell":
			hintaOtsikko.setVisible(true);
			hintaLabel.setVisible(true);
			hintaOtsikko2.setVisible(true);
			minimi.setVisible(true);
			maxnum.setVisible(true);
			break;
		case "Vuokraus":
		case "Rent":
			hintaOtsikko.setVisible(true);
			hintaLabel.setVisible(true);
			hintaOtsikko2.setVisible(true);
			minimi.setVisible(true);
			maxnum.setVisible(true);
			break;
		default:
		
		}

		//Vanha (tukee vain suomea)
		/*if (text.equals("Lahjoitus")) {
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
		}*/
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
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Myynti") || pelit.getTalletusTyyppi().contains("For sale"));
					hakuTesti();
					break;
				case "Vuokrataan":
				case "Rent":
					hakuTesti();
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Vuokraus") || pelit.getTalletusTyyppi().contains("Rent"));
					//hakuTesti();
					break;
				case "Lahjoitetaan":
				case "Giveaway":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Lahjoitus") || pelit.getTalletusTyyppi().contains("Giveaway"));
					break;
				case "Kaikki":
				case "All":
					filteredData.setPredicate(pelit -> true);
					break;
				}
			}
		});
	}
	/**
	 * Vie käyttäjän pelin lisäyssivulle
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void uusiPeli(ActionEvent event) throws IOException {
	mainApp.lisaaPeliOverview();
	}
	
	/**
	 * Vie omien tapahtumien sivuille
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void handletapahtumatSivu(ActionEvent event) throws IOException {
		mainApp.tapahtumatSivuOverview();
	}
	/**
	 * Vie profiilinäkymään
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void vieProofiliNäkymään(ActionEvent event) throws IOException {
		mainApp.showProfile();
	}
	/**
	 * Kirjaudu ulos painike
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void LogOut(ActionEvent event) throws IOException {
		boolean test = TiedostoKasittely.poistaTiedosto();
		if (test == true) {
			// Viedään kirjautumissivulle
			mainApp.showLogin();
		}
	}
	/**
	 * Peruuta painikkeen toiminto, sulkee rajausnäkymän
	 * @param event
	 */

	@FXML
	void peruuta(ActionEvent event) {
		rajaahakuNäkymä.setVisible(false);
	}
	/**
	 * Sulkee rajausnäkymän
	 * @param event
	 */
	@FXML
	void suljeRajaus(ActionEvent event) {
		rajaahakuNäkymä.setVisible(false);
	}
	/**
	 * Avaa rajausnäkymän
	 * @param event
	 */
	@FXML
	void avaaRajaus(ActionEvent event) {
		rajaahakuNäkymä.setVisible(true);
	}

	// Jää toteutukseen OTP2
	/**
	 * Rajausvalikon ehtolauseet
	 * @param event
	 */
	@FXML
	void Rajaa(ActionEvent event) {
		pelidata.clear();
		if(validoiRajaus() == true) {
		if(valinnat.getValue() == null && minimi.getText().isEmpty() && maxnum.getText().isEmpty()) {
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getPelmaara()).contains(maara.getText()));
			
		
		}else if(minimi.getText().isEmpty() && maxnum.getText().isEmpty()) {
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getIkaraja()).contains(valinnat.getValue()) && Integer.toString(pelit.getPelmaara()).contains(maara.getText()));
				
		}
		else if(valinnat.getValue() == null) {
			filteredData.setPredicate(pelit -> Integer.toString(pelit.getPelmaara()).contains(maara.getText()));
					
			
		}
		else if(!maara.getText().isEmpty() && valinnat.getValue() != null && !minimi.getText().isEmpty() && !maxnum.getText().isEmpty()) {
		filteredData.setPredicate(pelit -> Integer.toString(pelit.getIkaraja()).contains(valinnat.getValue()) && Integer.toString(pelit.getPelmaara()).contains(maara.getText()) &&
				pelit.getHinta() >= Integer.parseInt(minimi.getText()) && pelit.getHinta() <= Integer.parseInt(maxnum.getText()));
		
		}
		rajaahakuNäkymä.setVisible(false);
		}
		for (int i = 0; i < pelit.length; i++) {
			pelidata.add(pelit[i]);
		}
		
		// sulje ikkuna
		//rajaahakuNäkymä.setVisible(false);

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
	/**
	 * Etusivun Uusin painikkeen sorttaus
	 */
	public void uusinPeli() {
		vanhin2.setSelected(false);
		Collections.sort(filteredData.getSource(), (a, b) -> b.getPaiva().compareTo(a.getPaiva()));
		//Collections.sort(filteredData.getSource(), (a, b) -> a.getPaiva().compareTo(b.getPaiva()));
	}
	/**
	 * Etusivun Vanhin painikkeen sorttaus
	 */
	public void vanhinPeli() {
		uusin2.setSelected(false);
		Collections.sort(filteredData.getSource(), (a, b) -> a.getPaiva().compareTo(b.getPaiva()));
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
		Alert varoitus = new Alert(Alert.AlertType.WARNING);
		if(minimi.getText().isEmpty() && !maxnum.getText().isEmpty()) {
			varoitus.setContentText("Täytä molemmat kentät, minimi ja maksimi");
			varoitus.showAndWait();
		}else if(!minimi.getText().isEmpty() && maxnum.getText().isEmpty()) {
			varoitus.setContentText("Täytä molemmat kentät, minimi ja maksimi");
			varoitus.showAndWait();
		}
		try {
			int min = Integer.parseInt(minimi.getText());
			int max = Integer.parseInt(maxnum.getText());

			if (min < 0 || min > max) {
				
				minimi.setStyle("-fx-border-color:red");
				
				if(locale.equals("en")) { 
					minimi.setPromptText("Can't be negative number/bigger than max value");
				}else {
					minimi.setPromptText("Ei negativiinen/ei isompi kun max numero");
				}
			}
		

		} catch (NumberFormatException e) {
			System.out.println(e);
			System.out.println("Täällä");
			minimi.setStyle("-fx-border-color:red");
			minimi.setPromptText("numero");
			
		}
		try {
			int pelaajat = Integer.parseInt(maara.getText());
			if (pelaajat < 0) {
				
				maara.setStyle("-fx-border-color:red");
				
				if(locale.equals("en")) { 
					maara.setPromptText("Insert positive number");
				}else {
					maara.setPromptText("Syötä positiivinen");
				}
			}
		} catch (NumberFormatException e) {
			maara.setText("");
			System.out.println(e);
		}
		return true;
	}
}
