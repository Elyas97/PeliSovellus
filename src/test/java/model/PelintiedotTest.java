package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Peli.Pelintyyppi;
import model.Pelintiedot.Pelingenre;

class PelintiedotTest {

	@Test
	void testPelintiedot() {	
	}


	@Test
	void testGetTietoId() {
		Pelintiedot tiedot = new Pelintiedot(1, Kunto.TYYDYTTÄVÄ, Pelingenre.JÄNNITYS, 12, "Jännä peli");
		assertEquals(1, tiedot.getTietoId(), "Pelin tietojen id väärin.");
	}
	
	@Test
	void testGetKunto() {
		Pelintiedot tiedot = new Pelintiedot(1, Kunto.TYYDYTTÄVÄ, Pelingenre.JÄNNITYS, 12, "Jännä peli");
		assertEquals(Kunto.TYYDYTTÄVÄ, tiedot.getKunto(), "Pelin kunto väärin.");
	}
	
	@Test
	void testSetKunto() {
		Pelintiedot tiedot = new Pelintiedot();
		tiedot.setKunto(Kunto.ERINOMAINEN);
        assertTrue(tiedot.getKunto() == Kunto.ERINOMAINEN);
	}

	@Test
	void testSetTietoId() {
		Pelintiedot tiedot = new Pelintiedot();
		tiedot.setTietoId(1);
        assertTrue(tiedot.getTietoId() == 1);
	}

	@Test
	void testGetGenre() {
		Pelintiedot tiedot = new Pelintiedot(1, Kunto.TYYDYTTÄVÄ, Pelingenre.JÄNNITYS, 12, "Jännä peli");
		assertEquals(Pelingenre.JÄNNITYS, tiedot.getGenre(), "Pelin genre väärin.");
	}

	@Test
	void testSetGenre() {
		Pelintiedot tiedot = new Pelintiedot();
		tiedot.setGenre(Pelingenre.ROOLIPELI);
        assertTrue(tiedot.getGenre() == Pelingenre.ROOLIPELI);
	}

	@Test
	void testGetIkäraja() {
		Pelintiedot tiedot = new Pelintiedot(1, Kunto.TYYDYTTÄVÄ, Pelingenre.JÄNNITYS, 18, "Jännä peli");
		assertEquals(18, tiedot.getIkäraja(), "Pelin ikäraja väärin.");
	}

	@Test
	void testSetIkäraja() {
		Pelintiedot tiedot = new Pelintiedot();
		tiedot.setIkäraja(18);
        assertTrue(tiedot.getIkäraja() == 18);
	}

	@Test
	void testGetKuvaus() {
		Pelintiedot tiedot = new Pelintiedot(1, Kunto.TYYDYTTÄVÄ, Pelingenre.JÄNNITYS, 12, "Jännä peli");
		assertEquals("Jännä peli", tiedot.getKuvaus(), "Pelin kuvaus väärin.");
	}

	@Test
	void testSetKuvaus() {
		Pelintiedot tiedot = new Pelintiedot();
		tiedot.setKuvaus("Hauska nelinpeli");
        assertTrue(tiedot.getKuvaus() == "Hauska nelinpeli");
	}

}
