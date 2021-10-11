package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class KayttajaTest {

	/*@Test
	void testKäyttäjäIntStringStringStringIntString() {
	}

	@Test
	void testKäyttäjä() {
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals(1, käyttäjä.getKayttajaID(), "Käyttäjän id on väärin.");
	}*/

	@Test
	void testGetKayttajaID() {
		Kayttaja käyttäjä = new Kayttaja(1, "salasana", "Niko", "Nieminen", "0408322313", "moivaan@gmail.com");
		assertEquals(1, käyttäjä.getKayttajaID(), "Käyttäjän id väärin.");
	}

	@Test
	void testSetKayttajaID() {
		Kayttaja käyttäjä = new Kayttaja();
		käyttäjä.setKayttajaID(1);
        assertTrue(käyttäjä.getKayttajaID() == 1);
	}

	@Test
	void testGetSalasana() {
		Kayttaja käyttäjä = new Kayttaja(1, "salasana", "Niko", "Nieminen", "0408322313", "moivaan@gmail.com");
		assertEquals("salasana", käyttäjä.getSalasana(), "Käyttäjän salasana väärin.");
	}

	@Test
	void testSetSalasana() {
		Kayttaja käyttäjä = new Kayttaja();
		käyttäjä.setSalasana("1234");
        assertTrue(käyttäjä.getSalasana() == "1234");
	}

	@Test
	void testGetEtunimi() {
		Kayttaja käyttäjä = new Kayttaja(1, "salasana", "Niko", "Nieminen", "0408322313", "moivaan@gmail.com");
		assertEquals("Niko", käyttäjä.getEtunimi(), "Käyttäjän etunimi väärin.");
	}

	@Test
	void testSetEtunimi() {
		Kayttaja käyttäjä = new Kayttaja();
		käyttäjä.setEtunimi("Niko");
        assertTrue(käyttäjä.getEtunimi() == "Niko");
	}

	@Test
	void testGetSukunimi() {
		Kayttaja käyttäjä = new Kayttaja(1, "salasana", "Niko", "Nieminen", "0408322313", "moivaan@gmail.com");
		assertEquals("Nieminen", käyttäjä.getSukunimi(), "Käyttäjän sukunimi väärin.");
	}

	@Test
	void testSetSukunimi() {
		Kayttaja käyttäjä = new Kayttaja();
		käyttäjä.setSukunimi("Nieminen");
        assertTrue(käyttäjä.getSukunimi() == "Nieminen");
	}

	@Test
	void testGetPuhelinumero() {
		Kayttaja käyttäjä = new Kayttaja(1, "salasana", "Niko", "Nieminen", "0408322313", "moivaan@gmail.com");
		assertEquals("0408322313", käyttäjä.getPuhelinumero(), "Käyttäjän puhelinnumero väärin.");
	}

	@Test
	void testSetPuhelinumero() {
		Kayttaja käyttäjä = new Kayttaja();
		käyttäjä.setPuhelinumero("045321454");
        assertTrue(käyttäjä.getPuhelinumero() == "045321454");
	}

	@Test
	void testGetSähköposti() {
		Kayttaja käyttäjä = new Kayttaja(1, "salasana", "Niko", "Nieminen", "0408322313", "moivaan@gmail.com");
		assertEquals("moivaan@gmail.com", käyttäjä.getSähköposti(), "Käyttäjän sähköposti väärin.");
	}

	@Test
	void testSetSähköposti() {
		Kayttaja käyttäjä = new Kayttaja();
		käyttäjä.setSähköposti("nikke@hotmail.com");
        assertTrue(käyttäjä.getSähköposti() == "nikke@hotmail.com");
	}
}
