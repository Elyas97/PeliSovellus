package model;

public class LoginSystem {
	//heti kun on rekiströity pitää hakea updated tietokanta lista
	private Kayttaja[] rekistyröineet;	
 private PeliSovellusDAO pelisovellus;
 public LoginSystem() {
	 this.pelisovellus=new PeliSovellusDAO();
	 this.rekistyröineet=pelisovellus.readKäyttäjät();
 }
 public Kayttaja login(String sähköposti,String salasana) {
		Kayttaja käyttäjä=null;
		
		for(int i=0;i<rekistyröineet.length;i++) {
			
			if((sähköposti.equalsIgnoreCase(rekistyröineet[i].getSähköposti())) & (salasana.equals(rekistyröineet[i].getSalasana()))) {
				käyttäjä= rekistyröineet[i];
			}
		}
		return käyttäjä;
	}
}
