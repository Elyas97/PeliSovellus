package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import controller.EtusivuController;
import controller.LisaaPeliController;
import controller.LoginController;
import controller.ProfiiliController;
import controller.RekisteroidyController;
import controller.TapahtumatController;
import controller.VierasController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Kayttaja;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;

/**
 * Alustaa jokaisen käyttöliittymän sivun ja näyttää ne
 * 
 * @author jarnopk, jasmija, elyasa
 * @version 1.0
 * 
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	@FXML
	private ListView<String> lista;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();

	/**
	 * Käynnistää sovelluksen Hakee tiedostosta default kielen ja alustaa root
	 * layoutin
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Pelienvuokraussovellus");

		// Haetaan tiedostosa default kieli
		String appConfigPath = "resources/TextResources_Default.properties";
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(appConfigPath));
			String language = properties.getProperty("language");
			String country = properties.getProperty("country");
			Locale l = new Locale(language, country);
			System.out.println(country + "test");
			Locale.setDefault(l);
		} catch (FileNotFoundException e) {
			System.out.println("Tiedostoa ei löytynyt");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initRootLayout();
		// Käyttäjän ei aina tarvitse kirjautua sisään
		Kayttaja alku = TiedostoKasittely.lueKäyttäjä();
		if (alku != null) {
			showEtusivu();
		} else {
			showVieras();
		}
	}

	/**
	 * Alustaa root layoutin
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setLocation(MainApp.class.getResource("root.fxml"));
			loader.setResources(bundle);
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alustaa etusivun
	 */
	public void showEtusivu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane etusivu = (BorderPane) loader.load();
			Scene scene = new Scene(etusivu);
			primaryStage.setScene(scene);

			EtusivuController uuscont = loader.getController();
			uuscont.setDialogStage(primaryStage);
			uuscont.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alustaa lisää peli sivun
	 */
	public boolean lisaaPeliOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane uusipeli = (BorderPane) loader.load();
			Scene scene = new Scene(uusipeli);
			primaryStage.setScene(scene);

			LisaaPeliController controller = loader.getController();
			controller.setDialogStage(primaryStage);
			controller.setMainApp(this);

			return controller.tallennaClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Alustaa lisätyt pelit sivun
	 */
	public void tapahtumatSivuOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane tapahtumat = (BorderPane) loader.load();
			Scene scene = new Scene(tapahtumat);
			primaryStage.setScene(scene);

			TapahtumatController controller = loader.getController();
			controller.setDialogStage(primaryStage);
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alustaa kirjautumissivun
	 */
	public void showLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane kirjaudu = (BorderPane) loader.load();
			Scene scene = new Scene(kirjaudu);
			primaryStage.setScene(scene);

			LoginController uuscont = loader.getController();
			uuscont.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alustaa rekisteröitymissivun
	 */
	public void showRegister() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Rekisterointi.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane register = (BorderPane) loader.load();
			RekisteroidyController uuscont = loader.getController();
			uuscont.setMainApp(this);
			Scene scene = new Scene(register);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alustaa profiilisivun
	 */
	public void showProfile() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Profiili.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane profile = (BorderPane) loader.load();
			ProfiiliController uuscont = loader.getController();
			uuscont.setMainApp(this);
			Scene scene = new Scene(profile);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alustaa vierassivun
	 */
	public void showVieras() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Vieras.fxml"));

			ResourceBundle bundle = ResourceBundle.getBundle("TextResources", Locale.getDefault());
			loader.setResources(bundle);

			BorderPane kirjaudu = (BorderPane) loader.load();
			VierasController uuscont = loader.getController();
			uuscont.setMainApp(this);
			Scene scene = new Scene(kirjaudu);
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
