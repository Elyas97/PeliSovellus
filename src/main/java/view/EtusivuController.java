package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;

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
	
	private Stage dialogStage;

	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	

	
	public EtusivuController() {

	}
	
	private void initialize() {
		
		// ei toimi päivittäminen
		pelinTiedot(null);
		lista.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> pelinTiedot(newValue));
		
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage=dialogStage;
	}
	
    @FXML
	public void listaaPelit() {
		//pelit.haePelit();
    	lista.getItems().clear();
		pelit = pelitdao.haePelit();
		for (int i = 0; i < pelit.length; i++) {
			//lista.setId(pelit[i].getPelinNimi());
		lista.getItems().add(pelit[i]);
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
	public void uusiPeli() {
		
		mainApp.lisaaPeliOverview();
	}
	

	
}
