package model;

public class RegisterSystem {
	private Kayttaja[] rekistyröineet;
	private PeliSovellusDAO pelisovellus;

	public RegisterSystem() {
		this.pelisovellus = new PeliSovellusDAO();
		this.rekistyröineet = pelisovellus.readKäyttäjät();
	}

	public boolean register(Kayttaja käyttäjä) {
		System.out.println(käyttäjä.getEtunimi());
		boolean test = true;

		// Ei haluta samaa henkilöä rekisteröitymään uudestaan
		for (int i = 0; i < rekistyröineet.length; i++) {
			System.out.println("Kierros");
			if (käyttäjä.getSähköposti().equals(rekistyröineet[i].getSähköposti())) {
				test = false;
				return test;
			}
		}
		if (test = true) {
			test = pelisovellus.createKäyttäjä(käyttäjä);
		}
		return test;
	}
}
