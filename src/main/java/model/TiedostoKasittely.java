package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;


/**
 * Tiedostonkäsittely
 * 
 * @author elyasa
 *
 */
public class TiedostoKasittely {
	private final static String tiedostonimi = "asiakaat.data";

	/**
	 * Tallettaa käyttäjä tiedot
	 * 
	 * @param käyttäjä
	 */
	public static void kirjoitaTiedosto(Kayttaja käyttäjä) {
		try (FileOutputStream virta = new FileOutputStream(tiedostonimi);
				ObjectOutputStream käyttäjät = new ObjectOutputStream(virta);) {
			käyttäjät.writeObject(käyttäjä);
			System.out.println("Tallennus onnistui");
		} catch (IOException e) {
			System.out.println(tiedostonimi + " Ei voinut tallentaa");
		}
	}

	/**
	 * Lukee käyttäjä tiedot
	 * 
	 * @param käyttäjä
	 */
	public static Kayttaja lueKäyttäjä() {
		Kayttaja käyttäjä = null;
		try (FileInputStream virta = new FileInputStream(tiedostonimi);
				ObjectInputStream käyttäjät = new ObjectInputStream(virta);) {
			käyttäjä = (Kayttaja) käyttäjät.readObject();
			System.out.println(käyttäjä.getEtunimi());
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(tiedostonimi + " Ei voinut lukea");
		}
		return käyttäjä;
	}

	/**
	 * Poistaa käyttäjän tiedot
	 * 
	 * @return test
	 */
	public static boolean poistaTiedosto() {
		boolean test = false;
		try {
			new FileOutputStream(tiedostonimi).close();
			File f = new File(tiedostonimi);
			f.delete();
			test = true;
		} catch (FileNotFoundException e) {
			test = false;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			test = false;
		}
		return test;
	}

	/**
	 * Tallettaa kieliasetukset
	 * 
	 * @param properties
	 * @param path
	 */
	public static void tallennaKieli(Properties properties, String path) {
		try (FileWriter output = new FileWriter(path)) {
			properties.store(output, "These are properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
