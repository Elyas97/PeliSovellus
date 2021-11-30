package controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;
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

public class VierasController {

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

		hakurajaus.setPromptText("Rajaa hakua");

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

	// Jää toteutukseen OTP2
	@FXML
	void Rajaa(ActionEvent event) {
		// ei valmis
		// String sql="SELECT * FROM PELI WHERE ";
		// rajaustoiminto
		// boolean test=sell.isSelected();
		// boolean test1=rent.isSelected();
		// boolean test2=free.isSelected();
		// boolean uus=uusin.isSelected();
		// boolean vanhat=vanhin.isSelected();
		// String ikä=valinnat.getSelectionModel().getSelectedItem();
		// if(test==true) {
		// sql=sql + "Talletustyyppi = " +"\"" + sell.getText() + "\"";
		// System.out.println(sql);
		// }
		// if(test1==true) {
		// sql=sql +" Talletustyyppi =" +"\"" + rent.getText() + "\"";
		// }
		// if(test2==true) {
		// sql=sql +" Talletustyyppi =" +"\"" + free.getText() + "\"";
		// }
		// System.out.println(sql);
		// System.out.println(test +" rent"+test1+" ilmainen"+test2+" uus"+ uus+" vanha
		// "+vanhat +" "+ikä);

		// sulje ikkuna
		rajaahakuNäkymä.setVisible(false);
	}

	@FXML
	void accessDenied(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Tiedoksi");
		alert.setContentText(
				"Peli ilmoituksen tekeminen vaatii tilin. " + "" + "" + "" + "" + "Haluatko kirjautua sisään?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
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
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Myynti"));
					break;
				case "Vuokrataan":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Vuokraus"));
					break;
				case "Lahjoitetaan":
					filteredData.setPredicate(pelit -> pelit.getTalletusTyyppi().contains("Lahjoitus"));
					break;
				case "Kaikki":
					filteredData.setPredicate(pelit -> true);
					break;
				}
			}
		});
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
			päivämäärä.setText("Ilmoitus jätetty: " + peli.getPaiva().toString());
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
		//Viedään kirjautumissivulle
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
