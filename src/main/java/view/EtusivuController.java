package view;

import java.awt.Button;
import java.io.IOException;

import controller.ProfiiliController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Käyttäjä;
import model.Peli;
import model.PeliSovellusDAO;
import model.TiedostoKäsittely;

public class EtusivuController {
	
	@FXML
	private ListView<Peli> lista;
	@FXML
	private MainApp mainApp;
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
	private TextField pelihaku;
	@FXML
	private ChoiceBox<String> hakurajaus;

	
	private Stage dialogStage;

	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	ObservableList<Peli> pelidata = FXCollections.observableArrayList();
	FilteredList<Peli> filteredData = new FilteredList<>(pelidata, pelit -> true);

	
	/*public EtusivuController() {

	}*/
	
	public void initialize() {
		ObservableList<String> rajaus = FXCollections.observableArrayList("Nimi", "Kaupunki","Genre");
		hakurajaus.setItems(rajaus);
		listaaPelit();
		lista.setItems(filteredData);
		pelihaku.textProperty().addListener((obs, oldValue, newValue) -> {
			//String filter = pelihaku.getText();
			switch (hakurajaus.getValue()) {
			case "Nimi":
				filteredData.setPredicate(pelit -> pelit.getPelinNimi().toLowerCase().contains(newValue));
				break;
			case "Kaupunki":
				filteredData.setPredicate(pelit -> pelit.getKaupunki().toLowerCase().contains(newValue));
				break;
			case "Genre":
				filteredData.setPredicate(pelit -> pelit.getGenre().toLowerCase().contains(newValue));
				break;
			default:
				filteredData.setPredicate(pelit -> true);
					
			}
			
			/*
			 * 
			 * VANHA HAKU;
			 * EI TOIMI CHOICEBOXIN KANSSA
			 */
			/*if(newValue == null || newValue.length() == 0) {
				filteredData.setPredicate(pelit -> true);
			} else {
				
				filteredData.setPredicate(pelit -> pelit.getGenre().toLowerCase().contains(newValue));
				
			}*/
		});

		
		lista.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> pelinTiedot(newValue));
	
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage=dialogStage;
	} 

    @FXML
	public void listaaPelit() {
		//pelit.haePelit();
    //	lista.getItems().clear();
    	pelidata.clear();
		pelit = pelitdao.haePelit();
		for (int i = 0; i < pelit.length; i++) {
			//lista.setId(pelit[i].getPelinNimi());
		//lista.getItems().add(pelit[i]);
		pelidata.add(pelit[i]);
		
		
		}
	
			
	}
    private void pelinTiedot(Peli peli) {
    	if(peli != null) {
    		pelinNimi.setText(peli.getPelinNimi());
    		pelinHinta.setText(Integer.toString(peli.getHinta()));
    		paikkakunta.setText(peli.getKaupunki());
    		genre.setText(peli.getGenre());
    		ikäraja.setText(Integer.toString(peli.getIkaraja()));
    		pelaajamäärä.setText(Integer.toString(peli.getPelmaara()));
    		kuvaus.setText(peli.getKuvaus());
    	} else {
    		pelinNimi.setText("");
    		pelinHinta.setText("");
    		paikkakunta.setText("");
    		genre.setText("");
    		ikäraja.setText("");
    		pelaajamäärä.setText("");
    		kuvaus.setText("");
    	}
    	
    }
    
   
    
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void uusiPeli(ActionEvent event) throws IOException {	
		//vaihdetaan näkymää samalla viedään käyttäjän tiedot
		 FXMLLoader loader = new FXMLLoader();
       loader.setLocation(MainApp.class.getResource("Uusipeli.fxml"));
       BorderPane personOverview = (BorderPane) loader.load();
       Scene etusivulle = new Scene(personOverview);
       //stage
       Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(etusivulle);
	    	window.show();
	}
	
	@FXML
	public void handletapahtumatSivu(ActionEvent event) throws IOException {
		//vaihdetaan näkymää samalla viedään käyttäjän tiedot
		 FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("Tapahtumat.fxml"));
        BorderPane personOverview = (BorderPane) loader.load();
        Scene etusivulle = new Scene(personOverview);
        //stage
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(etusivulle);
	    	window.show();
		
	}
	
	@FXML
    void vieProofiliNäkymään(ActionEvent event) throws IOException {
		//vaihdetaan näkymää samalla viedään käyttäjän tiedot
		 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("Profiili.fxml"));
         BorderPane personOverview = (BorderPane) loader.load();
         Scene etusivulle = new Scene(personOverview);
         //stage
         Stage window=(Stage) ((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(etusivulle);
	    	window.show();
    }
	
	  @FXML
	    void LogOut(ActionEvent event) throws IOException {
	    	boolean test=TiedostoKäsittely.poistaTiedosto();
	    	if(test==true) {
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
	
	

	
}
