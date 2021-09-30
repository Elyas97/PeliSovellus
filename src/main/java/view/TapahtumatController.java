package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
//import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;

public class TapahtumatController {

	@FXML
	private ListView<Peli> omatPelit;
	@FXML
	private TextField pelinNimi;
	@FXML
	private TextField pelinHinta;
	@FXML 
	private TextField paikkakunta;
	@FXML
	private TextField genre;
	@FXML
	private TextField ikäraja;
	@FXML
	private TextField pelaajamäärä;
	@FXML
	private TextField kuvaus;
	
	private Stage dialogStage;
	private MainApp main;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	MainApp mainApp;
	
	public TapahtumatController() {
	}

	@FXML
	public void initialize() {
		listaaOmatPelit();
		omatPelit.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> pelinTiedot(newValue));
	}
	
	public void setMainApp(MainApp main) {
		this.main = main;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		
	}

	public void listaaOmatPelit() {
		System.out.println("listaa omat pelit");
		pelit = pelitdao.haeOmatPelit();
		System.out.println(pelit.toString());
		System.out.println(pelit.length);
			for (int i = 0; i < pelit.length; i++) {
				System.out.println(pelit[i]);
				omatPelit.getItems().add(pelit[i]);
			}
	 }
	
	 @FXML
	 public void lisaaUusiPeli() {

	 }

	 @FXML
	 public void muokkaaPelia() {

	 }

	 @FXML
	 public void poistaPeli() {

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

}

