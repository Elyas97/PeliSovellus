package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Kayttaja;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;
import view.MainApp;

public class ProfiiliController {

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextField etu;
	@FXML
	private TextField suku;
	@FXML
	private TextField email;
	@FXML
	private TextField numero;
	@FXML
	private Text tippi;
	
	Kayttaja käyttäjä;

	@FXML
	void tallennaMuutokset(ActionEvent event) {
		
		boolean validointi = validointi();
		if (validointi == true) {
			käyttäjä.setEtunimi(etu.getText());
			käyttäjä.setSukunimi(suku.getText());
			käyttäjä.setSähköposti(email.getText());
			käyttäjä.setPuhelinumero(numero.getText());
			PeliSovellusDAO dao2 = new PeliSovellusDAO();
			boolean test = dao2.updateKäyttäjä(käyttäjä);
			
			if (test == true) {
				//Päivitetään tiedosto
				TiedostoKasittely.kirjoitaTiedosto(käyttäjä);
				
				//Ilmoitetaan onnistumisesta
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Ilmoitus");
				alert.setContentText("Tietojen tallennus onnistui");
				alert.showAndWait();
				
				etu.setText(käyttäjä.getEtunimi());
				suku.setText(käyttäjä.getSukunimi());
				email.setText(käyttäjä.getSähköposti());
				numero.setText("" + käyttäjä.getPuhelinumero());
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Ilmoitus");
				alert.setContentText("Tietojen tallennus epäonnistui palvelin ongelma");
				alert.showAndWait();
			}
		}
	}

	@FXML
	void PoistaTili(ActionEvent event) throws IOException {
		
		ButtonType kyllä = new ButtonType("Kyllä", ButtonData.OK_DONE);
		ButtonType ei = new ButtonType("Peruuta", ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Jos poistat tilin menetät kaikki tiedot\n" + "Kuten kaikki lisäämäsi pelit.", kyllä, ei);
		alert.setTitle("Vahvistus");
		alert.setHeaderText("Vahvista tilin poisto");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == kyllä) {
			//Poistetaan tili
			PeliSovellusDAO poista = new PeliSovellusDAO();
			boolean test = poista.poistaKayttaja(käyttäjä);
			TiedostoKasittely.poistaTiedosto();
			System.out.println(test);
			//Viedään vierasNäkymään
			if (test = true) {
				//Ilmoitus tilin poistamisesta
				Alert alert2 = new Alert(AlertType.INFORMATION, "Tili poistettu");
				alert2.setTitle("Tili poistettu");
				alert2.setHeaderText("Tiedoksi");
				alert2.showAndWait();
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("Vieras.fxml"));
				BorderPane tapahtuma = (BorderPane) loader.load();
				Scene tapahtumaNäkymä = new Scene(tapahtuma);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(tapahtumaNäkymä);
				window.show();
			}
		}
	}

	@FXML
	void LogOut(ActionEvent event) throws IOException {
		boolean test = TiedostoKasittely.poistaTiedosto();
		if (test == true) {
			//Viedään kirjautumissivulle
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
			BorderPane etusivu = (BorderPane) loader.load();
			Scene kirjautumisNäkymä = new Scene(etusivu);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(kirjautumisNäkymä);
			window.show();
		}
	}

	@FXML
	void VieEtusivunNäkymä(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));
		
		Locale locale = new Locale("en", "FI");
		ResourceBundle bundle = ResourceBundle.getBundle("TextResources", locale);
		loader.setResources(bundle); 
		
		BorderPane etusivu = (BorderPane) loader.load();
		Scene etusivuNäkymä = new Scene(etusivu);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(etusivuNäkymä);
		window.show();
	}

	@FXML
	void vieTapahtumaNäkymä(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));
		BorderPane tapahtuma = (BorderPane) loader.load();
		Scene tapahtumaNäkymä = new Scene(tapahtuma);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(tapahtumaNäkymä);
		window.show();
	}

	@FXML
	void vieUuspeliNäkymä(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));
		BorderPane uuspeli = (BorderPane) loader.load();
		Scene uuspeliNäkymä = new Scene(uuspeli);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(uuspeliNäkymä);
		window.show();
	}

	@FXML
	void initialize() {
		this.käyttäjä = TiedostoKasittely.lueKäyttäjä();
		etu.setText(käyttäjä.getEtunimi());
		suku.setText(käyttäjä.getSukunimi());
		email.setText(käyttäjä.getSähköposti());
		numero.setText("" + käyttäjä.getPuhelinumero());
	}

	boolean validointi() {
		boolean test = true;
		etu.setStyle("-fx-border-color:#0589ff");
		suku.setStyle("-fx-border-color:#0589ff");
		email.setStyle("-fx-border-color:#0589ff");
		numero.setStyle("-fx-border-color:#0589ff");
		tippi.setText("");
		if (etu.getText() == "") {
			etu.setStyle("-fx-border-color:red");
			test = false;
		}
		if (suku.getText() == "") {
			suku.setStyle("-fx-border-color:red");
			test = false;
		}
		if (email.getText() == "") {
			email.setStyle("-fx-border-color:red");
			test = false;
		}
		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email.getText());
		boolean validEmail = matcher.matches();
		if (validEmail == false) {
			email.setStyle("-fx-border-color:red");
			tippi.setText("Väärä muoto");
			test = false;
		}
		if (numero.getText() == "") {
			numero.setStyle("-fx-border-color:red");
			test = false;
		}
		if (validoiEmail() == false) {
			email.setStyle("-fx-border-color:red");
			tippi.setText("Sähköpostilla on jo rekistyröity");
			test = false;
		}
		return test;
	}

	public boolean validoiEmail() {
		boolean test = true;
		PeliSovellusDAO dao = new PeliSovellusDAO();
		Kayttaja[] rekistyröineet = dao.readKäyttäjät();
		for (int i = 0; i < rekistyröineet.length; i++) {
			if ((rekistyröineet[i].getSähköposti().equalsIgnoreCase(email.getText()))
					&& (rekistyröineet[i].getKayttajaID() != käyttäjä.getKayttajaID())) {
				test = false;
				return test;
			}
		}
		return test;
	}
}
