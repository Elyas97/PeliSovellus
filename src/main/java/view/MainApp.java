package view;

import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Kayttaja;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKasittely;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@FXML
	private ListView<String> lista;

	PeliSovellusDAO pelitdao = new PeliSovellusDAO();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Pelienvuokraussovellus");

		initRootLayout();
		// Käyttäjän ei aina tarvitse kirjautua sisään
		Kayttaja alku = TiedostoKasittely.lueKäyttäjä();
		if (alku != null) {
			showEtusivu();
		} else {
			showVieras();
		}
	}

	/*
	 * Alustaa root layoutin
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("root.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showEtusivu() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));
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

	public boolean lisaaPeliOverview() {
		try {
			System.out.println("moi");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));
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

	public void tapahtumatSivuOverview() {
		try {
			System.out.println("Tapahtumat sivu");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));
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

	public void showLogin() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
			BorderPane kirjaudu = (BorderPane) loader.load();
			rootLayout.setCenter(kirjaudu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showRegister() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Rekistyröinti.fxml"));
			BorderPane register = (BorderPane) loader.load();
			rootLayout.setCenter(register);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showProfile() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Profiili.fxml"));
			BorderPane profile = (BorderPane) loader.load();
			rootLayout.setCenter(profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showVieras() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("Vieras.fxml"));
			BorderPane kirjaudu = (BorderPane) loader.load();
			rootLayout.setCenter(kirjaudu);
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
