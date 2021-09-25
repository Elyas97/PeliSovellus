package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Käyttäjä;
import model.RegisterSystem;
import view.MainApp;

public class ReskistyröidyController {

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
    void Rekistyröidy(ActionEvent event) {
    	boolean test=validointi();
    	
    	if(test==true) {
    		
    		try {
    			Käyttäjä käyttäjä=new Käyttäjä();
        		käyttäjä.setEtunimi(etu.getText());
        		käyttäjä.setSukunimi(suku.getText());
    			int puh=Integer.parseInt(puhelinnumero.getText());
    			käyttäjä.setPuhelinumero(puh);
    			käyttäjä.setSähköposti(email.getText());
    			käyttäjä.setSalasana(salasana.getText());
    			RegisterSystem register =new RegisterSystem();
    			boolean tulos=register.register(käyttäjä);
    			if(tulos==true) {
    				
    				//ilmoitetaan
    				Alert alert = new Alert(Alert.AlertType.INFORMATION);
      		      //Setting the title
      		      alert.setTitle("Alert");
      		      
      		      //Setting the content of the dialog
      		      alert.setContentText("Register has been successfull");
      		      alert.showAndWait();
    				//viedään kirjautumissivulle
    				FXMLLoader loader = new FXMLLoader();
    		        
    		        loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
    		       
    		        BorderPane kirjaudu = (BorderPane) loader.load();
    		    	Scene kirjauduNäkymä = new Scene(kirjaudu);
    		    	//get stage
    		    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
    		    	window.setScene(kirjauduNäkymä);
    		    	window.show();
    			}
    		}catch(NumberFormatException e) {
    			System.out.println(e);
    			return;
    				
    		}catch(Exception e) {
    			System.out.println(e);
    			return;
    		}
    		
    		
    	}
    }

    @FXML
    void ViewNäkymäLogin(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(MainApp.class.getResource("Kirjautuminen.fxml"));
       
        BorderPane kirjaudu = (BorderPane) loader.load();
    	Scene kirjauduNäkymä = new Scene(kirjaudu);
    	//get stage
    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(kirjauduNäkymä);
    	window.show();
    }
    
    boolean validointi() {
    	 boolean test=true;
    	 if(etu.getText()=="") {
    		 etu.setStyle("-fx-border-color:red");
    		 test=false;
    	 }
    	 if(suku.getText()=="") {
    		 suku.setStyle("-fx-border-color:red");
    		 test=false;
    		 
    	 }
    	 if(email.getText()=="") {
    		 email.setStyle("-fx-border-color:red");
    		 test=false;
    	 }
    	 if(puhelinnumero.getText()=="") {
    		 puhelinnumero.setStyle("-fx-border-color:red");
    		 test=false;
    	 }
    	 if(salasana.getText()==""|| csalasana.getText()=="") {
    		 salasana.setStyle("-fx-border-color:red");
    		 csalasana.setStyle("-fx-border-color:red");
    		 test=false;
    	 }
    	return test;
    }

}
