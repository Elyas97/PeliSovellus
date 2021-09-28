package view;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Peli;
import model.PeliSovellusDAO;
import model.Pelingenre;

public class LisääPeliController {
	@FXML
	private ToggleGroup tyyppi;
	@FXML
	private ToggleGroup pelintyyppi;
	@FXML
	private TextField hinta;
	@FXML
	private ChoiceBox<String> genre;
	@FXML
	private TextField pelinnimi;
	@FXML
	private TextField ikaraja;
	@FXML
	private TextField pelaajamaara;
	@FXML 
	private TextField kaupunki;
	@FXML 
	private TextField kuvaus;
	
	
	private Stage dialogStage;
	//private Peli peli;
	private boolean tallennaClicked = false;
	private MainApp main;
	private Pelingenre genret;
	PeliSovellusDAO pelisovellusdao = new PeliSovellusDAO();
	
	public LisääPeliController() {}
	@FXML
	public String tyyppiAction(ActionEvent Action) {
		
		String text = ((RadioButton)tyyppi.getSelectedToggle()).getText();
		System.out.println(text);
		
		return text;
	}
	
	@FXML
	private void initialize() {
		ObservableList<String> options = FXCollections.observableArrayList("Urheilu", "Räiskintä","Toiminta"
				,"Ajopeli", "Jännitys", "Seikkailu", "Strategia", "Roolipeli", "Pulma",
				"Lautapeli");
		genre.setItems(options);

		// ei toimi
		//genre.getItems().setAll(Pelingenre.values());
		//choiceBox.getItems().clear();
		//choiceBox.getItems().setAll(Pelingenre.values());
		//choiceBox.setItems((ObservableList<Pelingenre>) Arrays.asList(Pelingenre.values()));
		
	}
	public void setMainApp(MainApp main) {
		this.main = main;
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void uusiPeli() {
		Peli peli = new Peli();
		peli.setPelinNimi(pelinnimi.getText());
		int price = Integer.parseInt(hinta.getText());
		peli.setHinta(price);
		int age = Integer.parseInt(ikaraja.getText());
		peli.setIkaraja(age);
		peli.setKaupunki(kaupunki.getText());
		String tyyppiText = ((RadioButton)tyyppi.getSelectedToggle()).getText();
		peli.setTalletusTyyppi(tyyppiText);
		peli.setKuvaus(kuvaus.getText());
		int players = Integer.parseInt(pelaajamaara.getText());
		peli.setPelmaara(players);
		String pelintyyppiText = ((RadioButton)pelintyyppi.getSelectedToggle()).getText();
		peli.setPelinTyyppi(pelintyyppiText);
		peli.setGenre(genre.getValue().toString());
		System.out.println(genre.getValue().toString());
		//System.out.println(peli.getPelinNimi()+" "+ peli.getHinta()+" "+ peli.getIkaraja());
		pelisovellusdao.lisaaPeli(peli, 2);
		tallennaClicked = true;
	}
	
	public boolean tallennaClicked() {
		return tallennaClicked;
	}

}
