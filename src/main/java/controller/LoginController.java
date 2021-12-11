package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Kayttaja;
import model.LoginSystem;
import model.TiedostoKasittely;
import view.MainApp;

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
	
	@FXML
	void kirjaudu(ActionEvent event) {
		boolean test = validointi();
		if (test == true) {
			LoginSystem login = new LoginSystem();
			kirjautunut = login.login(tunnus23.getText(), pwd23.getText());
			System.out.println("testing" + kirjautunut);
			if (kirjautunut != null) {

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				
				if(locale.equals("en")) {
					alert.setTitle("Information");
					alert.setContentText("Login succesfully!");
				}else {
					// Otsikon asetus
					alert.setTitle("Ilmoitus");
					// Sisällön asetus
					alert.setContentText("Kirjautuminen onnistui!");
				}
				
				alert.showAndWait();
				// siirretään etusivulle ja tallenetaan käyttäjä controlleriin
				
					// Annetaan kirjautuneen käyttäjän tiedot
					TiedostoKasittely.kirjoitaTiedosto(kirjautunut);
					// Ladataan etusivu
					System.out.println(TiedostoKasittely.lueKäyttäjä().getEtunimi());
					app.showEtusivu();
				
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				
				if(locale.equals("en")) {
					alert.setTitle("Notification");
					alert.setContentText("Password or username incorrect");
				}else {
					alert.setTitle("Ilmoitus");
					alert.setContentText("Salasana tai Sähköposti väärä");
				}
				
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
		this.app=mainApp;
	}
}
