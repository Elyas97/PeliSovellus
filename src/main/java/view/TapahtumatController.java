package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;

public class TapahtumatController {

	@FXML
    private ListView<Peli> omatPelit;

    @FXML
    private RadioButton myynti;

    @FXML
    private ToggleGroup tyyppi;

    @FXML
    private RadioButton vuokraus;

    @FXML
    private RadioButton lahjoitus;

    @FXML
    private TextField hinta;

    @FXML
    private TextField pelinnimi;

    @FXML
    private ToggleGroup pelintyyppi;

    @FXML
    private TextField kaupunki;

    @FXML
    private TextField ikaraja;

    @FXML
    private TextField pelaajamaara;

    @FXML
    private TextField kuvaus;
    
    @FXML
    private ChoiceBox<String> genre;

	
	private Stage dialogStage;
	private MainApp main;
	PeliSovellusDAO pelitdao = new PeliSovellusDAO();
	private Peli[] pelit;
	//MainApp mainApp;
	
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
		 main.lisaaPeliOverview();
	 }

	 @FXML
	 public void muokkaaPelia() {

	 }

	 @FXML
	 public void poistaPeli() { 
		 Peli peli = omatPelit.getSelectionModel().getSelectedItem();
		 poistaPeliTietokannasta(peli);	 
	 }
	 
	 public void poistaPeliTietokannasta(Peli peli) {
		 int id = peli.getPeliId();
		 System.out.println("poistettava id " + id);
		 pelitdao.poistaPeli(id);
	 }
	 
	 private void pelinTiedot(Peli peli) {
		 if(peli != null) {
	    		pelinnimi.setText(peli.getPelinNimi());
	    		hinta.setText(Integer.toString(peli.getHinta()));
	    		kaupunki.setText(peli.getKaupunki());
	    		genre.setValue(peli.getGenre());
	    		ikaraja.setText(Integer.toString(peli.getIkaraja()));
	    		pelaajamaara.setText(Integer.toString(peli.getPelmaara()));
	    		kuvaus.setText(peli.getKuvaus());
	    	} else {
	    		pelinnimi.setText("");
	    		hinta.setText("");
	    		kaupunki.setText("");
	    		genre.setValue("");
	    		ikaraja.setText("");
	    		pelaajamaara.setText("");
	    		kuvaus.setText("");
	    	}
	 }
	 
	/* @FXML
	 public void tallennaPeli() {
		 Peli peli = new Peli();
			peli.setPelinNimi(pelinNimi.getText());
			int price = Integer.parseInt(pelinHinta.getText());
			peli.setHinta(price);
			int age = Integer.parseInt(ikäraja.getText());
			peli.setIkaraja(age);
			peli.setKaupunki(paikkakunta.getText());
			String tyyppiText = ((RadioButton)tyyppi.getSelectedToggle()).getText();
			peli.setTalletusTyyppi(tyyppiText);
			peli.setKuvaus(kuvaus.getText());
			int players = Integer.parseInt(pelaajamäärä.getText());
			peli.setPelmaara(players);
			String pelintyyppiText = ((RadioButton)pelintyyppi.getSelectedToggle()).getText();
			peli.setPelinTyyppi(pelintyyppiText);
			peli.setGenre(genre.getValue().toString());
			System.out.println(genre.getValue().toString());
			
			//System.out.println(peli.getPelinNimi()+" "+ peli.getHinta()+" "+ peli.getIkaraja());
			pelitdao.lisaaPeli(peli, 2);
			//tallennaClicked = true;
	 }*/

	    @FXML
	    void tallennaMuutokset() {

	    }

	    @FXML
	    void tyyppiAction() {

	    }

}