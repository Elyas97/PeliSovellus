package model;

public class LoginSystem {
	//heti kun on rekiströity pitää hakea updated tietokanta lista
	private Käyttäjä[] rekistyröineet;	
 private PeliSovellusDAO pelisovellus=new PeliSovellusDAO();
 public LoginSystem() {
	 this.rekistyröineet=pelisovellus.readKäyttäjät();
 }
 public Käyttäjä login(String sähköposti, String salasana) {
		Käyttäjä käyttäjä=null;
		
		for(int i=0;i<rekistyröineet.length;i++) {
			if(sähköposti==rekistyröineet[i].getSähköposti()&& salasana==rekistyröineet[i].getSalasana()) {
				käyttäjä= rekistyröineet[i];
			}
		}
		return käyttäjä;
	}
}
