package model;

import java.util.ArrayList;

public class RegisterSystem {
private Käyttäjä[] rekistyröineet;
private PeliSovellusDAO pelisovellus;

public RegisterSystem() {
	PeliSovellusDAO pelisovellus= new PeliSovellusDAO();
	this.rekistyröineet=pelisovellus.readKäyttäjät();
}

public boolean register(Käyttäjä käyttäjä) {
	boolean test=true;
	//ei haluta samaa henkilöä rekistyröimään uudestaan
	for(int i=0;i<rekistyröineet.length;i++) {
		if(käyttäjä.getSähköposti()==rekistyröineet[i].getSähköposti()) {
			test=false;
		}
	}
	if(test=true) {
		pelisovellus.createKäyttäjä(käyttäjä);
	}
	return test;
}



}
