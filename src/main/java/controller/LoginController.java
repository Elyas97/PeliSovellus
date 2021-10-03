package controller;

import java.io.IOException;
import java.net.URL;
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
import model.Käyttäjä;
import model.LoginSystem;
import view.EtusivuController;
import view.MainApp;

public class LoginController {
	
Käyttäjä kirjautunut=null;
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

    @FXML
    void kirjaudu(ActionEvent event) {
    	boolean test=validointi();
    	if(test==true) {
    		LoginSystem login=new LoginSystem();
    		 kirjautunut=login.login(tunnus23.getText(), pwd23.getText());
    		System.out.println("testing"+ kirjautunut);
    		if(kirjautunut!=null) {
    			
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		      //Setting the title
    		      alert.setTitle("Alert");
    		      
    		      //Setting the content of the dialog
    		      alert.setContentText("Login has been successfull");
    		      alert.showAndWait();
    		      //siirretään etusivulle ja tallenetaan käyttäjä controlleriin
    		      try {
    		    	  //annetaan kirjautuneen käyttäjän tiedot 
    		    	 
    		            // Load person overview.
    		            FXMLLoader loader = new FXMLLoader();
    		            loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));
    		            BorderPane etusivuOverview = (BorderPane) loader.load();
    		            EtusivuController controller = loader.getController();
     		    	   controller.initData(kirjautunut);
    		            Scene etusivulle = new Scene(etusivuOverview);
    		            //stage
    		            Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
        		    	window.setScene(etusivulle);
        		    	window.show();
    		        } catch (IOException e) {
    		            e.printStackTrace();
    		        }
    		      
    		      
    			
    		}else {
    			Alert alert = new Alert(Alert.AlertType.INFORMATION);
  		      //Setting the title
  		      alert.setTitle("Alert");
  		      
  		      //Setting the content of the dialog
  		      alert.setContentText("Salasana tai Sähköposti väärä");
  		      alert.showAndWait();
    			
    		}
    	}

    }

    @FXML
    void vieRekistyröintiNäkymään(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(MainApp.class.getResource("Rekistyröinti.fxml"));
       
        BorderPane register = (BorderPane) loader.load();
    	Scene rekistyröintiNäkymä = new Scene(register);
    	//get stage
    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rekistyröintiNäkymä);
    	window.show();
    }

    @FXML
    void initialize() {
        assert pwd23 != null : "fx:id=\"pwd23\" was not injected: check your FXML file 'Kirjautuminen.fxml'.";
        assert buttoni != null : "fx:id=\"buttoni\" was not injected: check your FXML file 'Kirjautuminen.fxml'.";
        assert tunnus23 != null : "fx:id=\"tunnus23\" was not injected: check your FXML file 'Kirjautuminen.fxml'.";

    }
    boolean validointi() {
   	 boolean test=true;
   	 if(tunnus23.getText()=="") {
   		 tunnus23.setStyle("-fx-border-color:red");
   		 test=false;
   	 }
   	 if(pwd23.getText()=="") {
   		 pwd23.setStyle("-fx-border-color:red");
   		 test=false;
   		 
   	 }
   	 
   	return test;
   }
  
}
