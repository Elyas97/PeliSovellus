package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Kayttaja;
import model.LoginSystem;
import model.TiedostoKasittely;
import view.MainApp;

/**
 * Kontrolloi kirjautumisnäkymän toiminnallisuutta
 * @author elyasa
 *
 */
public class LoginController {
	private MainApp app;
	Kayttaja kirjautunut = null;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private PasswordField pwd23;
	@FXML
	private Button buttoni;
	@FXML
	private TextField tunnus23;
	String locale = Locale.getDefault().getLanguage();
	ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());

	/**
	 * Kirjautuu sisään järjestelmään
	 * 
	 * @param event
	 */
	@FXML
	void kirjaudu(ActionEvent event) {
		boolean test = validointi();
		if (test == true) {
			LoginSystem login = new LoginSystem();
			kirjautunut = login.login(tunnus23.getText(), pwd23.getText());
			System.out.println("testing" + kirjautunut);
			if (kirjautunut != null) {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(bundle.getString("ilmoitus"));
				alert.setContentText(bundle.getString("kirjautuminenOnnistui"));
				alert.showAndWait();

				// Siirretään etusivulle ja tallenetaan käyttäjä controlleriin
				// Annetaan kirjautuneen käyttäjän tiedot
				TiedostoKasittely.kirjoitaTiedosto(kirjautunut);

				// Ladataan etusivu
				System.out.println(TiedostoKasittely.lueKäyttäjä().getEtunimi());
				app.showEtusivu();

			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(bundle.getString("ilmoitus"));
				alert.setContentText(bundle.getString("salasanaVaaraIlmoitus"));
				alert.showAndWait();
			}
		}
	}

	@FXML
	void vieRekistyröintiNäkymään(ActionEvent event) throws IOException {
		app.showRegister();
	}

	@FXML
	void vieVierasNäkymä(ActionEvent event) throws IOException {
		app.showVieras();
	}

	@FXML
	void initialize() {
		assert pwd23 != null : "fx:id=\"pwd23\" was not injected: check your FXML file 'Kirjautuminen.fxml'.";
		assert buttoni != null : "fx:id=\"buttoni\" was not injected: check your FXML file 'Kirjautuminen.fxml'.";
		assert tunnus23 != null : "fx:id=\"tunnus23\" was not injected: check your FXML file 'Kirjautuminen.fxml'.";
	}

	/**
	 * Validoi käyttäjän syötettä
	 * 
	 * @return
	 */
	boolean validointi() {
		boolean test = true;
		if (tunnus23.getText() == "") {
			tunnus23.setStyle("-fx-border-color:red");
			test = false;
		}
		if (pwd23.getText() == "") {
			pwd23.setStyle("-fx-border-color:red");
			test = false;
		}
		return test;
	}

	public void setMainApp(MainApp mainApp) {
		this.app = mainApp;
	}
}
