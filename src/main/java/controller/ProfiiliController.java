package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Käyttäjä;
import model.PeliSovellusDAO;
import view.MainApp;

public class ProfiiliController {

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
	    
	    Käyttäjä käyttäjä;

	    @FXML
	    void tallennaMuutokset(ActionEvent event) {
	    	boolean validointi=validointi();
	    	if(validointi==true) {
	    		käyttäjä.setEtunimi(etu.getText());
	    		käyttäjä.setSukunimi(suku.getText());
	    		käyttäjä.setSähköposti(email.getText());
	    		try {
	    			int puh=Integer.parseInt(numero.getText());
	    			käyttäjä.setPuhelinumero(puh);
	    			PeliSovellusDAO dao2 =new PeliSovellusDAO();
	    			boolean test=dao2.updateKäyttäjä(käyttäjä);
	    			if(test==true) {
	    				//ilmoitetaan
	    				Alert alert = new Alert(Alert.AlertType.INFORMATION);
	      		      //Setting the title
	      		      alert.setTitle("Alert");
	      		      
	      		      //Setting the content of the dialog
	      		      alert.setContentText("Tietojen tallennus onnistui");
	      		      alert.showAndWait();
	      		      etu.setText(käyttäjä.getEtunimi());
	      		      suku.setText(käyttäjä.getSukunimi());
	      		      email.setText(käyttäjä.getSähköposti());
	      		      numero.setText(""+käyttäjä.getPuhelinumero());
	    			}else {
	    				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		      		      //Setting the title
		      		      alert.setTitle("Alert");
		      		      
		      		      //Setting the content of the dialog
		      		      alert.setContentText("Tietojen tallennus epäonnistui palvelin ongelma");
		      		      alert.showAndWait();
	    			}
	    		}catch(NumberFormatException e) {
	    			return;
	    		}
	    		
	    		
	    	}

	    }

	    @FXML
	    void VieEtusivunNäkymä(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("Etusivu.fxml"));
	       
	        BorderPane etusivu = (BorderPane) loader.load();
	    	Scene etusivuNäkymä = new Scene(etusivu);
	    	//get stage
	    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(etusivuNäkymä);
	    	window.show();

	    }
	    @FXML
	    void vieTapahtumaNäkymä(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));
	       
	        BorderPane tapahtuma = (BorderPane) loader.load();
	    	Scene tapahtumaNäkymä = new Scene(tapahtuma);
	    	//get stage
	    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(tapahtumaNäkymä);
	    	window.show();

	    }
	    
	    @FXML
	    void vieUuspeliNäkymä(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader();
	        
	        loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));
	       
	        BorderPane uuspeli = (BorderPane) loader.load();
	    	Scene uuspeliNäkymä = new Scene(uuspeli);
	    	//get stage
	    	Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(uuspeliNäkymä);
	    	window.show();
	    }

	    @FXML
	    void initialize() {
	       

	    }
	    boolean validointi() {
	    	 boolean test=true;
	    	 etu.setStyle("-fx-border-color:#0589ff");
	    	 suku.setStyle("-fx-border-color:#0589ff");
	    	 email.setStyle("-fx-border-color:#0589ff");
	    	 numero.setStyle("-fx-border-color:#0589ff");
	    	 tippi.setText("");
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
	    		 tippi.setText("Väärä muoto");
	    		 test=false;
	    	 }
	    	 if(numero.getText()=="") {
	    		 numero.setStyle("-fx-border-color:red");
	    		 test=false;
	    	 }
	    	 if(validoiEmail()==false) {
	    		 email.setStyle("-fx-border-color:red");
	    		 tippi.setText("Sähköpostilla on jo rekistyröity");
	    		 test=false;
	    	 }
	    
	    	 
	    	return test;
	    }
	    public boolean validoiEmail() {
	    	boolean test =true;
	    	PeliSovellusDAO dao=new PeliSovellusDAO();
	    	Käyttäjä[] rekistyröineet =dao.readKäyttäjät();
	    	for(int i=0;i<rekistyröineet.length;i++) {
	    		if((rekistyröineet[i].getSähköposti().equalsIgnoreCase(email.getText()))&&(rekistyröineet[i].getKayttajaID()!=käyttäjä.getKayttajaID())) {
	    			test=false;
	    			return test;
	    		}
	    	}
	    	return test;
	    }
	    
	    public void initData(Käyttäjä käyttäjä) {
	    	this.käyttäjä=käyttäjä;
	    	
	    	etu.setText(käyttäjä.getEtunimi());
	    	suku.setText(käyttäjä.getSukunimi());
	    	email.setText(käyttäjä.getSähköposti());
	    	numero.setText(""+käyttäjä.getPuhelinumero());
	    	
	    }
}
