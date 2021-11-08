package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginTest {
	static LoginSystem logTest=new LoginSystem();
	static PeliSovellusDAO dao=new PeliSovellusDAO();
	static Kayttaja[] rekistyroineet;
	static Kayttaja testiHenkilo;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	 rekistyroineet=dao.readKäyttäjät();
	 int rnd= new Random().nextInt(rekistyroineet.length);
	 testiHenkilo=rekistyroineet[rnd];
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		testiHenkilo=null;
		rekistyroineet=null;
	}


	@Test
	void testLogin() {
	Kayttaja test=logTest.login(testiHenkilo.getSähköposti(),testiHenkilo.getSalasana());
	System.out.println(test.getEtunimi());
	assertEquals(testiHenkilo.getSähköposti(), test.getSähköposti(), "Kirjautuminen palautti väärän käyttäjän");
	
	}
	@Test
	void testLogin2() {
	Kayttaja test=logTest.login("hylätty", "1");
	assertEquals(null, test, "Kirjautuminen ei palauttanut null");
	}

}
