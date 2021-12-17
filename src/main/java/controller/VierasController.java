package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;
import view.MainApp;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
/**
 * Edustaa vierasNäkymää
 * @author Elyas
 *
 */
public class VierasController {
	@FXML
	private ComboBox<String> maat;
	@FXML
	private TextField pelihaku;
	@FXML
	private ComboBox<String> hakurajaus;
	@FXML
	private ListView<Peli> lista;
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
	private Label konsoli;
	@FXML
	private Label tekstikenttä;
	@FXML
	private Label päivämäärä;
	@FXML
	private AnchorPane rajaahakuNäkymä;
	@FXML
	private Label hintaOtsikko2;
	@FXML
	private TextField maara;
	@FXML
	private RadioButton uusin;
	@FXML
	private ToggleGroup julkaisu;
	@FXML
	private RadioButton vanhin;
	@FXML
	private TextField minimi;
	@FXML
	private TextField maxnum;
	@FXML
	private Text hintaLabel;
	@FXML
	private ChoiceBox<String> valinnat;
	@FXML
	private RadioButton sell;
	@FXML
	private RadioButton rent;
	@FXML
	private RadioButton free;
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
	private Label hintaOtsikko;
	@FXML
	private RadioButton uusin2;
	@FXML
	private RadioButton vanhin2;
	@FXML
	private ToggleGroup julkaisuAika;
	@FXML
	private RadioButton alhaisinhinta;
	@FXML
	private RadioButton korkeinhinta;
	@FXML
	private ToggleGroup hintaLajittelu;

	private Stage dialogStage;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	ObservableList<Peli> pelidata = FXCollections.observableArrayList();
	FilteredList<Peli> filteredData = new FilteredList<>(pelidata, pelit -> true);
	String locale = Locale.getDefault().getLanguage();
	ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());

	public void initialize() {
		Image img = new Image("file:finland.png");
		System.out.println(img);
		String fi = "FI";
		String eng = "EN";
		String name = Locale.getDefault().getLanguage();
		System.out.println(name);
		ObservableList<String> options = FXCollections.observableArrayList();
		options.addAll(eng, fi);
		maat.setItems(options);
		maat.setValue(name.toUpperCase());
		maat.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (oldValue.equalsIgnoreCase(newValue)) {
					return;
				} else {

					// Muutetaan kieliasetus
					String appConfigPath = "resources/TextResources_Default.properties";
					Properties properties = new Properties();
					try {
						properties.load(new FileInputStream(appConfigPath));
						properties.setProperty("language", newValue.toLowerCase());
						if (newValue.equalsIgnoreCase("EN")) {
							properties.setProperty("country", "US");
							Locale.setDefault(new Locale(newValue.toLowerCase(), "US"));
						} else {
							properties.setProperty("country", "FI");
							Locale.setDefault(new Locale(newValue.toLowerCase(), "FI"));
						}
					} catch (FileNotFoundException e) {
						System.out.println("Tiedostoa ei löytynyt");
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					TiedostoKasittely.tallennaKieli(properties, appConfigPath);
					try {
						refreshPage();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		maat.setCellFactory(c -> new StatusListCell());
		maat.setButtonCell(new StatusListCell());

		ObservableList<String> valinta = FXCollections.observableArrayList("3", "7", "12", "16", "18");
		valinnat.setItems(valinta);

		ObservableList<String> rajaus = FXCollections.observableArrayList("Nimi", "Kaupunki", "Genre");
		hakurajaus.setItems(rajaus);

		lista.setItems(filteredData);

		hakutyyppi();

		listaaPelit();

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
		lista.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> pelinTiedot(newValue));
	}

	private MainApp app;

	public void setMainApp(MainApp app) {
		this.app = app;
	}

	public void refreshPage() throws IOException {
		app.showVieras();
	}

	@FXML
	void Rajaa(ActionEvent event) {
	}

	@FXML
	void accessDenied(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(bundle.getString("varmistus"));
		alert.setHeaderText(bundle.getString("ilmoitus"));
		alert.setContentText(bundle.getString("vaaditaanTiliText"));

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// Viedään kirjautumissivulle
			app.showLogin();
		}
	}

	@FXML
	void listaaPelit() {
		pelidata.clear();
		pelit = pelitdao.haePelit();
		for (int i = 0; i < pelit.length; i++) {
			pelidata.add(pelit[i]);
		}
	}

	public void hakutyyppi() {
		kaikki.setSelected(true);
		hakutyyppi.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> obs, Toggle oldT, Toggle newT) {
				switch (((RadioButton) hakutyyppi.getSelectedToggle()).getText()) {
				case "Myydään":
				case "For sale":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Myynti")
							|| pelit.getTalletusTyyppi().contains("For sale"));
					break;
				case "Vuokrataan":
				case "Rent":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Vuokraus")
							|| pelit.getTalletusTyyppi().contains("Rent"));
					break;
				case "Lahjoitetaan":
				case "Giveaway":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Lahjoitus")
							|| pelit.getTalletusTyyppi().contains("Giveaway"));
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
	 * Etusivun Uusin painikkeen sorttaus
	 */
	public void uusinPeli() {
		vanhin2.setSelected(false);
		Collections.sort(filteredData.getSource(), (a, b) -> b.getPaiva().compareTo(a.getPaiva()));
		// Collections.sort(filteredData.getSource(), (a, b) ->
		// a.getPaiva().compareTo(b.getPaiva()));
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

	private void pelinTiedot(Peli peli) {
		if (peli != null) {
			pelinNimi.setText(peli.getPelinNimi());
			pelinHinta.setText(Integer.toString(peli.getHinta()));
			paikkakunta.setText(peli.getKaupunki());
			genre.setText(peli.getGenre());
			ikäraja.setText(Integer.toString(peli.getIkaraja()));
			pelaajamäärä.setText(Integer.toString(peli.getPelmaara()));
			kuvaus.setText(peli.getKuvaus());
			tekstikenttä.setText(peli.getTekstikenttä());
			
			// Päivämäärän formatointi
			DateFormat dateFormat;
			if (locale.equals("en")) {
				dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
				String paivamaaraFormat = dateFormat.format(peli.getPaiva());
				päivämäärä.setText("" + paivamaaraFormat);
			} else {
				dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String paivamaaraFormat = dateFormat.format(peli.getPaiva());
				päivämäärä.setText("" + paivamaaraFormat);
			}
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

	@FXML
	void vieKirjautumisNäkymään(ActionEvent event) throws IOException {
		app.showLogin();
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
}
