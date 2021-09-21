package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class KäyttäjäTest {

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
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals(1, käyttäjä.getKayttajaID(), "Käyttäjän id väärin.");
	}

	@Test
	void testSetKayttajaID() {
		Käyttäjä käyttäjä = new Käyttäjä();
		käyttäjä.setKayttajaID(1);
        assertTrue(käyttäjä.getKayttajaID() == 1);
	}

	@Test
	void testGetSalasana() {
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals("salasana", käyttäjä.getSalasana(), "Käyttäjän salasana väärin.");
	}

	@Test
	void testSetSalasana() {
		Käyttäjä käyttäjä = new Käyttäjä();
		käyttäjä.setSalasana("1234");
        assertTrue(käyttäjä.getSalasana() == "1234");
	}

	@Test
	void testGetEtunimi() {
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals("Niko", käyttäjä.getEtunimi(), "Käyttäjän etunimi väärin.");
	}

	@Test
	void testSetEtunimi() {
		Käyttäjä käyttäjä = new Käyttäjä();
		käyttäjä.setEtunimi("Niko");
        assertTrue(käyttäjä.getEtunimi() == "Niko");
	}

	@Test
	void testGetSukunimi() {
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals("Nieminen", käyttäjä.getSukunimi(), "Käyttäjän sukunimi väärin.");
	}

	@Test
	void testSetSukunimi() {
		Käyttäjä käyttäjä = new Käyttäjä();
		käyttäjä.setSukunimi("Nieminen");
        assertTrue(käyttäjä.getSukunimi() == "Nieminen");
	}

	@Test
	void testGetPuhelinumero() {
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals(408322313, käyttäjä.getPuhelinumero(), "Käyttäjän puhelinnumero väärin.");
	}

	@Test
	void testSetPuhelinumero() {
		Käyttäjä käyttäjä = new Käyttäjä();
		käyttäjä.setPuhelinumero(045321454);
        assertTrue(käyttäjä.getPuhelinumero() == 045321454);
	}

	@Test
	void testGetSähköposti() {
		Käyttäjä käyttäjä = new Käyttäjä(1, "salasana", "Niko", "Nieminen", 408322313, "moivaan@gmail.com");
		assertEquals("moivaan@gmail.com", käyttäjä.getSähköposti(), "Käyttäjän sähköposti väärin.");
	}

	@Test
	void testSetSähköposti() {
		Käyttäjä käyttäjä = new Käyttäjä();
		käyttäjä.setSähköposti("nikke@hotmail.com");
        assertTrue(käyttäjä.getSähköposti() == "nikke@hotmail.com");
	}
}
