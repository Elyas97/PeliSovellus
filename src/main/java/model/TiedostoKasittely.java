package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TiedostoKasittely {
	private final static String tiedostonimi = "asiakaat.data";

	public static void kirjoitaTiedosto(Kayttaja käyttäjä) {
		try (FileOutputStream virta = new FileOutputStream(tiedostonimi);
				ObjectOutputStream käyttäjät = new ObjectOutputStream(virta);) {
			käyttäjät.writeObject(käyttäjä);
			System.out.println("Tallennus onnistui");
		} catch (IOException e) {
			System.out.println(tiedostonimi + " Ei voinut tallentaa");
		}
	}

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
}
