/**
 * LoginSystem esittää käyttäjän kirjautumista sovellukseen
 * 
 * @author elyasa
 */

package model;

public class LoginSystem {
	private Kayttaja[] rekistyröineet;
	private PeliSovellusDAO pelisovellus;

	/**
	 * Konstruktorin kutsuessa tiukasti kytketään Projektin DAO luokka
	 */
	public LoginSystem() {
		this.pelisovellus = new PeliSovellusDAO();
		this.rekistyröineet = pelisovellus.readKäyttäjät();
	}

	/**
	 * Tarkistaa onko käyttäjä olemassa ja palauttaa käyttäjän
	 * 
	 * @param sähköposti Käyttäjän sähköposti
	 * @param salasana   Käyttäjän salasana
	 * @return Käyttäjän tai null
	 */
	public Kayttaja login(String sähköposti, String salasana) {
		Kayttaja käyttäjä = null;
		for (int i = 0; i < rekistyröineet.length; i++) {
			if ((sähköposti.equalsIgnoreCase(rekistyröineet[i].getSähköposti()))
					& (salasana.equals(rekistyröineet[i].getSalasana()))) {
				käyttäjä = rekistyröineet[i];
			}
		}
		return käyttäjä;
	}
}
