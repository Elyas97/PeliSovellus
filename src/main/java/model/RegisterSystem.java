package model;

import java.util.ArrayList;

public class RegisterSystem {
private Käyttäjä[] rekistyröineet;
private PeliSovellusDAO pelisovellus;

public RegisterSystem() {
	 this.pelisovellus= new PeliSovellusDAO();
	this.rekistyröineet=pelisovellus.readKäyttäjät();
}

public boolean register(Käyttäjä käyttäjä) {
	System.out.println(käyttäjä.getEtunimi());
	boolean test=true;
	//ei haluta samaa henkilöä rekistyröimään uudestaan
	for(int i=0;i<rekistyröineet.length;i++) {
		if(käyttäjä.getSähköposti().equals(rekistyröineet[i].getSähköposti())) {
			test=false;
		}
	}
	if(test=true) {
		test=pelisovellus.createKäyttäjä(käyttäjä);
	}
	return test;
}



}
