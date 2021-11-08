package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegisterTest {
	static RegisterSystem regTest=new RegisterSystem();
	static PeliSovellusDAO dao=new PeliSovellusDAO();
	static Kayttaja[] rekistyroineet;
	static Kayttaja rekistyroinytHenkilo;
	static Kayttaja uusiHenkilo;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rekistyroineet=dao.readKäyttäjät();
		 int rnd= new Random().nextInt(rekistyroineet.length);
		 rekistyroinytHenkilo=rekistyroineet[rnd];
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		rekistyroinytHenkilo=null;
		rekistyroineet=dao.readKäyttäjät();
		for(int i=0;i<rekistyroineet.length;i++) {
			if(rekistyroineet[i].getSähköposti().equals(uusiHenkilo.getSähköposti())) {
				uusiHenkilo=rekistyroineet[i];
				break;
			}
		}
		//poisto tapahtuu id:n avulla mikä generoidaan tietokannassa.
		dao.poistaKayttaja(uusiHenkilo);
		uusiHenkilo=null;
		System.out.println("valmis");
		
		
	}

	
	@DisplayName("olemassa oleva käyttäjä yrittää rekistyröityä")
	@Test
	void testRegister() {
		boolean test=regTest.register(rekistyroinytHenkilo);
		assertEquals(false, test, "Ei palauttanut false kun rekistyröidyttiin samalla käyttäjällä");
	}
	@DisplayName("uusi käyttäjä yrittää rekistyröityä")
	@Test
	void testRegister2() {
		uusiHenkilo=new Kayttaja();
		uusiHenkilo.setEtunimi("JunitKayttaja");
		uusiHenkilo.setSukunimi("JunitSukunimi");
		uusiHenkilo.setSähköposti("JunitTestaus@hotmail.com");
		uusiHenkilo.setSalasana("JunitTestaus");
		uusiHenkilo.setPuhelinumero("044011666");
		boolean test=regTest.register(uusiHenkilo);
		assertEquals(true, test, "Ei palauttanut true kun rekistyröitiin");
	}

}
