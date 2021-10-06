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
import model.Käyttäjä;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKäsittely;


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
        //käyttäjän ei aina tarvitse kirjautua sisään
        Käyttäjä alku=TiedostoKäsittely.lueKäyttäjä();
        if(alku!=null) {
        	showEtusivu();
        }else {
        	showVieras();
        }
        
        
        //tapahtumatSivuOverview();
        
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("root.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
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
    		
    	//	uuscont.listaaPelit();
  
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

    		
    		LisääPeliController controller = loader.getController();
    		
    		controller.setDialogStage(primaryStage);
    		controller.setMainApp(this);
    	
    		
    		
    		
    		//Tarviiko tätä ??
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
            // Load login overview.
        	
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
           
            BorderPane kirjaudu = (BorderPane) loader.load();
            
            
            
            // Set login overview into the center of root layout.
            rootLayout.setCenter(kirjaudu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showRegister() {
        try {
            // Load login overview.
        	
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class.getResource("Rekistyröinti.fxml"));
           
            BorderPane register = (BorderPane) loader.load();
            
            
            
            // Set login overview into the center of root layout.
            rootLayout.setCenter(register);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showProfile() {
        try {
            // Load login overview.
        	
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class.getResource("Profiili.fxml"));
           
            BorderPane profile = (BorderPane) loader.load();
            
            
            
            // Set login overview into the center of root layout.
            rootLayout.setCenter(profile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showVieras() {
        try {
            // Load login overview.
        	
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class.getResource("Vieras.fxml"));
           
            BorderPane kirjaudu = (BorderPane) loader.load();
            
            
            
            // Set login overview into the center of root layout.
            rootLayout.setCenter(kirjaudu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}

