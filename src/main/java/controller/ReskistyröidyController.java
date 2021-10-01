package controller;

import java.io.IOException;
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
    private Text tippi;
    @FXML
    private Text emailtip;
    
    @FXML
    void initialize() {
    	
       Tooltip toolpwd=new Tooltip();
       toolpwd.setText("Salasanan pituus vähintään 6");
       salasana.setTooltip(toolpwd);
       Tooltip toolpwd2=new Tooltip();
       toolpwd2.setText("Salasanan pituus vähintään 6");
       csalasana.setTooltip(toolpwd);
       Tooltip toolemail=new Tooltip();
       toolemail.setText("Syötä Sähköposti esimerkiksi JohnDoe@Hotmail.com");
       email.setTooltip(toolemail);

    }

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
    			}else {
    				Alert alert= new Alert(Alert.AlertType.INFORMATION);
    				alert.setTitle("Tiedoksi");
    				alert.setContentText("Sähköpostillasi on jo rekistyröidytty");
    				alert.show();
    				 email.setStyle("-fx-border-color:red");
    				
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
    	 etu.setStyle("-fx-border-color:#0589ff");
    	 suku.setStyle("-fx-border-color:#0589ff");
    	 salasana.setStyle("-fx-border-color:#0589ff");
    	 csalasana.setStyle("-fx-border-color:#0589ff");
    	 email.setStyle("-fx-border-color:#0589ff");
    	 puhelinnumero.setStyle("-fx-border-color:#0589ff");
    	 etu.setStyle("-fx-border-color:#0589ff");
    	 tippi.setText("");
    	 emailtip.setText("");
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
    	 Pattern pattern = Pattern.compile("^.+@.+\\..+$");
    	 Matcher matcher = pattern.matcher(email.getText());
    	 boolean validEmail=matcher.matches();
    	 if(validEmail==false) {
    		 email.setStyle("-fx-border-color:red");
    		 emailtip.setText("Muoto väärä");
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
    	 boolean compare=salasana.getText().equals(csalasana.getText());
    	 if(compare!=true) {
    		 salasana.setStyle("-fx-border-color:red");
    		 csalasana.setStyle("-fx-border-color:red");
    		 tippi.setText("Salasanat eivät täsmää");
    		 test=false;
    		 }
    	 if(salasana.getText().length()<6 || csalasana.getText().length()<6) {
    		 salasana.setStyle("-fx-border-color:red");
    		 csalasana.setStyle("-fx-border-color:red");
    		 tippi.setText("Salasanan pituus vähintään 6");
    		 test=false;
    	 }
    	 
    	return test;
    }

}
