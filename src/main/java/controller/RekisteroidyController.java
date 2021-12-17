package controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Kayttaja;
import model.RegisterSystem;
import view.MainApp;

/**
 * Kontrolloi rekisteröintisivun toiminnallisuutta
 * 
 * @author elyasa
 */
public class RekisteroidyController {
	private MainApp app;
	@FXML
	private TextField etu;
	@FXML
	private TextField suku;
	@FXML
	private TextField email;
	@FXML
	private TextField puhelinnumero;
	@FXML
	private PasswordField salasana;
	@FXML
	private PasswordField csalasana;
	@FXML
	private Text tippi;
	@FXML
	private Text emailtip;
	String locale = Locale.getDefault().getLanguage();
	ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());

	/**
	 * Alustaa sivun, asettaa tooltipit
	 * 
	 * @param event
	 */
	@FXML
	void initialize() {
		Tooltip toolpwd = new Tooltip();
		Tooltip toolpwd2 = new Tooltip();
		Tooltip toolemail = new Tooltip();
		toolpwd.setText(bundle.getString("salasanaVahintaanText"));
		toolpwd2.setText(bundle.getString("salasanaVahintaanText"));
		toolemail.setText(bundle.getString("esimerkkiSahkopostiText"));
		salasana.setTooltip(toolpwd);
		csalasana.setTooltip(toolpwd);
		email.setTooltip(toolemail);
	}

	/**
	 * Rekisteröityy järjestelmään
	 * 
	 * @param event
	 */
	@FXML
	void Rekistyröidy(ActionEvent event) {
		boolean test = validointi();
		if (test == true) {
			try {
				Kayttaja käyttäjä = new Kayttaja();
				käyttäjä.setEtunimi(etu.getText());
				käyttäjä.setSukunimi(suku.getText());
				käyttäjä.setPuhelinumero(puhelinnumero.getText());
				käyttäjä.setSähköposti(email.getText());
				käyttäjä.setSalasana(salasana.getText());
				RegisterSystem register = new RegisterSystem();
				boolean tulos = register.register(käyttäjä);
				if (tulos == true) {

					// Ilmoitetaan rekisteröitymisen onnistumisesta
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle(bundle.getString("ilmoitus"));
					alert.setContentText(bundle.getString("rekisteroityminenOnnistuiText"));
					alert.showAndWait();

					// Viedään kirjautumissivulle
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
					BorderPane kirjaudu = (BorderPane) loader.load();
					Scene kirjauduNäkymä = new Scene(kirjaudu);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(kirjauduNäkymä);
					window.show();
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle(bundle.getString("ilmoitus"));
					alert.setContentText(bundle.getString("sahkopostiKaytossaText"));
					alert.show();
					email.setStyle("-fx-border-color:red");
				}
			} catch (NumberFormatException e) {
				System.out.println(e);
				return;
			} catch (Exception e) {
				System.out.println(e);
				return;
			}
		}
	}

	@FXML
	void ViewNäkymäLogin(ActionEvent event) throws IOException {
		app.showLogin();
	}

	@FXML
	void vieVierasNäkymä(ActionEvent event) throws IOException {
		app.showVieras();
	}

	/**
	 * Validoi käyttäjän syötettä
	 * 
	 * @return true,false
	 */
	boolean validointi() {
		boolean test = true;
		etu.setStyle("-fx-border-color:#0589ff");
		suku.setStyle("-fx-border-color:#0589ff");
		salasana.setStyle("-fx-border-color:#0589ff");
		csalasana.setStyle("-fx-border-color:#0589ff");
		email.setStyle("-fx-border-color:#0589ff");
		puhelinnumero.setStyle("-fx-border-color:#0589ff");
		etu.setStyle("-fx-border-color:#0589ff");
		tippi.setText("");
		emailtip.setText("");
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
			emailtip.setText(bundle.getString("vaaraMuotoText"));
			test = false;
		}
		if (puhelinnumero.getText() == "") {
			puhelinnumero.setStyle("-fx-border-color:red");
			test = false;
		}
		if (salasana.getText() == "" || csalasana.getText() == "") {
			salasana.setStyle("-fx-border-color:red");
			csalasana.setStyle("-fx-border-color:red");
			test = false;
		}
		boolean compare = salasana.getText().equals(csalasana.getText());
		if (compare != true) {
			salasana.setStyle("-fx-border-color:red");
			csalasana.setStyle("-fx-border-color:red");
			tippi.setText(bundle.getString("salasanatEivatTasmaaText"));
			test = false;
		}
		if (salasana.getText().length() < 6 || csalasana.getText().length() < 6) {
			salasana.setStyle("-fx-border-color:red");
			csalasana.setStyle("-fx-border-color:red");
			tippi.setText(bundle.getString("salasanaVahintaanText"));
			test = false;
		}
		return test;
	}

	public void setMainApp(MainApp mainApp) {
		this.app = mainApp;
	}
}
