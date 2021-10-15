package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RegisterSystemTest {

	@Test
	void testRegister() {
		Kayttaja kayttaja = new Kayttaja();
		kayttaja.setSähköposti("user@mail.com");
		assertEquals("user@mail.com", kayttaja.getSähköposti(), "Väärä sähköposti.");
	}
}
