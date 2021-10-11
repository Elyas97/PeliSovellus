package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class PeliTest {
	
	long millis=System.currentTimeMillis();  
    java.sql.Date paiva =new java.sql.Date(millis);  

	@Test
	void testGetPeliId() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals(2, peli.getPeliId(), "Pelin id väärin.");
	}

	@Test
	void testSetPeliId() {
		Peli peli = new Peli();
		peli.setPeliId(1);
        assertTrue(peli.getPeliId() == 1);
	}

	@Test
	void testSetPelinNimi() {
		Peli peli = new Peli();
		peli.setPelinNimi("Uno");
        assertTrue(peli.getPelinNimi() == "Uno");
	}

	@Test
	void testGetPelinNimi() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals("Mario Kart", peli.getPelinNimi(), "Pelin nimi väärin.");
	}

	@Test
	void testSetPelinTyyppi() {
		Peli peli = new Peli();
		peli.setPelinTyyppi("Lautapeli");
        assertTrue(peli.getPelintyyppi() == "Lautapeli");
	}

	@Test
	void testGetPelintyyppi() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals("Videopeli", peli.getPelintyyppi(), "Pelin tyyppi väärin.");
	}
	
	void testGetKunto() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals("Hyvä", peli.getKunto(), "Pelin kunto väärin.");
	}
	
	@Test
	void testSetKunto() {
		Peli peli = new Peli();
		peli.setKunto("Erinomainen");
        assertTrue(peli.getKunto() == "Erinomainen");
	}

	@Test
	void testGetGenre() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals("Jännitys", peli.getGenre(), "Pelin genre väärin.");
	}

	@Test
	void testSetGenre() {
		Peli peli = new Peli();
		peli.setGenre("Kauhu");
        assertTrue(peli.getGenre() == "Kauhu");
	}

	@Test
	void testGetIkäraja() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals(8, peli.getIkaraja(), "Pelin ikäraja väärin.");
	}

	@Test
	void testSetIkäraja() {
		Peli peli = new Peli();
		peli.setIkaraja(18);
        assertTrue(peli.getIkaraja() == 18);
	}

	@Test
	void testGetKuvaus() {
		Peli peli = new Peli("Mario Kart", 2, "Videopeli", "Vuokraus", 2, "Jännitys", "Playstation", 8, 2, "Kelpo peli", "Helsinki", "Hyvä", "Puh: 040 123456", paiva);
		assertEquals("Kelpo peli", peli.getKuvaus(), "Pelin kuvaus väärin.");
	}

	@Test
	void testSetKuvaus() {
		Peli peli = new Peli();
		peli.setKuvaus("Hauska nelinpeli");
        assertTrue(peli.getKuvaus() == "Hauska nelinpeli");
	}


}
