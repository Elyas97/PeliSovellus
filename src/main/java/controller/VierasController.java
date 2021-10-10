package controller;

import java.io.IOException;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainApp;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class VierasController {
	
	 @FXML
	    private TextField pelihaku;

	    @FXML
	    private ChoiceBox<?> hakurajaus;

	    @FXML
	    private ListView<?> lista;

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
	    void accessDenied(ActionEvent event) throws IOException {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Confirmation Dialog");
	    	alert.setHeaderText("Tiedoksi");
	    	alert.setContentText("Peli ilmoituksen tekeminen vaatii tilin. "
	    			+ ""
	    			+ ""
	    			+ ""
	    			+ ""
	    			+ "Haluatko kirjautua sisään?");


	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		//ajetaan kirjautumis sivulle
	    		FXMLLoader loader = new FXMLLoader();
		        
		        loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
		       
		        BorderPane etusivu = (BorderPane) loader.load();
		    	Scene kirjautumisNäkymä = new Scene(etusivu);
		    	//get stage
		    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
		    	window.setScene(kirjautumisNäkymä);
		    	window.show();
	    	}

	    }

	    @FXML
	    void listaaPelit(ActionEvent event) {

	    }

	    @FXML
	    void vieKirjautumisNäkymään(ActionEvent event) throws IOException {
	    	//ajetaan kirjautumis sivulle
    		FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
	       
	        BorderPane etusivu = (BorderPane) loader.load();
	    	Scene kirjautumisNäkymä = new Scene(etusivu);
	    	//get stage
	    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(kirjautumisNäkymä);
	    	window.show();

	    }

}
