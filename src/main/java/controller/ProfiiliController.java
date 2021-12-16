package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Kayttaja;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;
import view.MainApp;

public class ProfiiliController {
	private MainApp app;
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
	@FXML
	private ComboBox<String> maat;

	Kayttaja käyttäjä;
	String locale = Locale.getDefault().getLanguage();
	ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());

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
				// Päivitetään tiedosto
				TiedostoKasittely.kirjoitaTiedosto(käyttäjä);

				// Ilmoitetaan onnistumisesta
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(bundle.getString("ilmoitus"));
				alert.setContentText(bundle.getString("tietojenTallennusOnnistuiText"));
				alert.showAndWait();

				etu.setText(käyttäjä.getEtunimi());
				suku.setText(käyttäjä.getSukunimi());
				email.setText(käyttäjä.getSähköposti());
				numero.setText("" + käyttäjä.getPuhelinumero());
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(bundle.getString("ilmoitus"));
				alert.setContentText(bundle.getString("tietojenTallennusEpaonnistuiText"));
				alert.showAndWait();
			}
		}
	}

	@FXML
	void PoistaTili(ActionEvent event) throws IOException {

		ButtonType kyllä = new ButtonType(bundle.getString("kylläButton"), ButtonData.OK_DONE);
		ButtonType ei = new ButtonType(bundle.getString("peruutaButton"), ButtonData.CANCEL_CLOSE);

		Alert alert = new Alert(AlertType.CONFIRMATION, bundle.getString("tilinpoistoVaroitusText"), kyllä, ei);
		alert.setTitle(bundle.getString("varmistus"));
		alert.setHeaderText(bundle.getString("vahvistaTilinPoistoText"));
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == kyllä) {
			// Poistetaan tili
			PeliSovellusDAO poista = new PeliSovellusDAO();
			boolean test = poista.poistaKayttaja(käyttäjä);
			TiedostoKasittely.poistaTiedosto();
			System.out.println(test);
			// Viedään vierasNäkymään
			if (test = true) {
				// Ilmoitus tilin poistamisesta
				Alert alert2 = new Alert(AlertType.INFORMATION, bundle.getString("tiliPoistettuText"));
				alert2.setTitle(bundle.getString("tiliPoistettuText"));
				alert2.setHeaderText(bundle.getString("ilmoitus"));
				alert2.showAndWait();
				app.showVieras();
			}
		}
	}

	@FXML
	void LogOut(ActionEvent event) throws IOException {
		boolean test = TiedostoKasittely.poistaTiedosto();
		if (test == true) {
			// Viedään kirjautumissivulle
			app.showLogin();
		}
	}

	@FXML
	void VieEtusivunNäkymä(ActionEvent event) throws IOException {
		app.showEtusivu();
	}

	@FXML
	void vieTapahtumaNäkymä(ActionEvent event) throws IOException {
		app.tapahtumatSivuOverview();
	}

	@FXML
	void vieUuspeliNäkymä(ActionEvent event) throws IOException {
		app.lisaaPeliOverview();
	}

	@FXML
	void initialize() {
		this.käyttäjä = TiedostoKasittely.lueKäyttäjä();
		etu.setText(käyttäjä.getEtunimi());
		suku.setText(käyttäjä.getSukunimi());
		email.setText(käyttäjä.getSähköposti());
		numero.setText("" + käyttäjä.getPuhelinumero());

		// Kieli
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
					System.out.println("it shooudd " + newValue);
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
							System.out.println("TULI OIKEALLE");
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

			private void refreshPage() throws IOException {
				app.showProfile();
			}
		});
		maat.setCellFactory(c -> new StatusListCell());
		maat.setButtonCell(new StatusListCell());
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
			tippi.setText(bundle.getString("vaaraMuotoText"));
			test = false;
		}
		if (numero.getText() == "") {
			numero.setStyle("-fx-border-color:red");
			test = false;
		}
		if (validoiEmail() == false) {
			email.setStyle("-fx-border-color:red");
			tippi.setText(bundle.getString("sahkopostiKaytossaText"));
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

	public void setMainApp(MainApp mainApp) {
		this.app = mainApp;
	}
}
