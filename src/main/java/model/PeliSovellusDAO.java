package model;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import controller.TapahtumatController;

public class PeliSovellusDAO {

	Connection conn;
	final String URL = "jdbc:mariadb://10.114.32.16/PELIKESKUS";
	final String USERNAME = "ryhma";
	final String PWD = "ryhma";
	
	String locale = Locale.getDefault().getLanguage();

	public PeliSovellusDAO() {
		try {
			conn = DriverManager.getConnection(URL + "?user=" + USERNAME + "&password=" + PWD);
			System.out.println("Yhdistetty tietokantaan");
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);
			System.exit(-1);
		}
	}

	/*
	 * Luodaan uusi käyttäjä
	 */
	public boolean createKäyttäjä(Kayttaja käyttäjä) {
		boolean temp = false;
		try (PreparedStatement luoKäyttäjä = conn.prepareStatement(
				"INSERT INTO Käyttäjä (Salasana, Sähköposti, Sukunimi, Etunimi, Puhelinnumero) VALUES (?,?,?,?,?)")) {
			luoKäyttäjä.setString(1, käyttäjä.getSalasana());
			luoKäyttäjä.setString(2, käyttäjä.getSähköposti());
			luoKäyttäjä.setString(3, käyttäjä.getSukunimi());
			luoKäyttäjä.setString(4, käyttäjä.getEtunimi());
			luoKäyttäjä.setString(5, käyttäjä.getPuhelinumero());
			int count = luoKäyttäjä.executeUpdate();
			temp = true;
			System.out.println(count);
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/*
	 * Käyttäjien lukeminen tietokannasta
	 */
	public Kayttaja[] readKäyttäjät() {
		ArrayList<Kayttaja> lista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM Käyttäjä";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				int kayttajaID = rs.getInt("KäyttäjäID");
				String salasana = rs.getString("Salasana");
				String etunimi = rs.getString("Etunimi");
				String sukunimi = rs.getString("Sukunimi");
				String puhelinumero = rs.getString("Puhelinnumero");
				String sähköposti = rs.getString("Sähköposti");
				lista.add(new Kayttaja(kayttajaID, salasana, etunimi, sukunimi, puhelinumero, sähköposti));
			}
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());

			} while (e.getNextException() != null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Kayttaja[] users = new Kayttaja[lista.size()];
		for (int i = 0; i < users.length; i++) {
			users[i] = lista.get(i);
		}
		return users;
	}

	/*
	 * Käyttäjän päivitys
	 */
	public boolean updateKäyttäjä(Kayttaja käyttäjä) {
		boolean temp = false;
		String query = "UPDATE Käyttäjä SET Salasana=?,Sähköposti=?,Sukunimi=?,Etunimi=?,Puhelinnumero=? WHERE KäyttäjäID=?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(6, käyttäjä.getKayttajaID());
			stmt.setString(1, käyttäjä.getSalasana());
			stmt.setString(2, käyttäjä.getSähköposti());
			stmt.setString(3, käyttäjä.getSukunimi());
			stmt.setString(4, käyttäjä.getEtunimi());
			stmt.setString(5, käyttäjä.getPuhelinumero());
			stmt.executeUpdate();
			temp = true;

		} catch (SQLException e) {
			System.out.println(e);

		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	/*
	 * Pelin lisääminen tietokantaan
	 */
	public boolean lisaaPeli(Peli peli, int kayttajaID) {
		boolean temp = false;
		System.out.println(peli.getPelinNimi() + " " + kayttajaID);
		try (PreparedStatement lisaaPeli = conn.prepareStatement(
				"INSERT INTO Peli (Pelinimi, Pelintyyppi, Talletustyyppi, Hinta, Genre, Konsoli, Ikäraja, Pelaajamäärä, Kuvaus, Kaupunki, Kunto, Tekstikenttä, KäyttäjäID, Päivämäärä) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
			lisaaPeli.setString(1, peli.getPelinNimi());
			lisaaPeli.setString(2, peli.getPelintyyppi());
			lisaaPeli.setString(3, peli.getTalletusTyyppi());
			lisaaPeli.setInt(4, peli.getHinta());
			lisaaPeli.setString(5, peli.getGenre());
			lisaaPeli.setString(6, peli.getKonsoli());
			lisaaPeli.setInt(7, peli.getIkaraja());
			lisaaPeli.setInt(8, peli.getPelmaara());
			lisaaPeli.setString(9, peli.getKuvaus());
			lisaaPeli.setString(10, peli.getKaupunki());
			lisaaPeli.setString(11, peli.getKunto());
			lisaaPeli.setString(12, peli.getTekstikenttä());
			lisaaPeli.setInt(13, kayttajaID);
			lisaaPeli.setDate(14, peli.getPaiva());

			int count = lisaaPeli.executeUpdate();
			temp = true;
			System.out.println(count);
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/*
	 * Haetaan kaikki pelit tietokannasta
	 */
	public Peli[] haePelit() {
		ArrayList<Peli> peliLista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = "Select * from Peli";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String pelinimi = rs.getString("Pelinimi");
				int PeliID = rs.getInt("PeliID");
				String pelityyppi = rs.getString("Pelintyyppi");
				String talletustyyppi = rs.getString("Talletustyyppi");
				int hinta = rs.getInt("Hinta");
				String genre = rs.getString("Genre");
				int ika = rs.getInt("Ikäraja");
				int lukumaara = rs.getInt("Pelaajamäärä");
				String kuvaus = rs.getString("Kuvaus");
				String kaupunki = rs.getString("Kaupunki");
				String konsoli = rs.getString("Konsoli");
				String tekstikenttä = rs.getString("Tekstikenttä");
				String kunto = rs.getString("Kunto");
				Date paivamaara = rs.getDate("Päivämäärä");
				
				if(locale.equals("en")) {
					// Dollareina
					Locale usLocale = Locale.US;
					NumberFormat usNumberFormat = NumberFormat.getInstance(usLocale);
					hinta = Integer.parseInt(usNumberFormat.format(hinta));
					hinta = (int) (hinta * 1.13);
				}else {
					hinta = rs.getInt("Hinta");
				}

				peliLista.add(new Peli(pelinimi, PeliID, pelityyppi, talletustyyppi, hinta, genre, konsoli, ika,
						lukumaara, kuvaus, kaupunki, kunto, tekstikenttä, paivamaara));
			}
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Peli[] pelit = new Peli[peliLista.size()];
		for (int i = 0; i < pelit.length; i++) {
			pelit[i] = peliLista.get(i);
		}
		return pelit;
	}
	
	//Ikärajan mukaan
	public Peli[] haePelitIkaraja(int ikaraja) {
		System.out.println("ikäraja on " + ikaraja);
		ArrayList<Peli> peliLista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "Select * from Peli where ikäraja='"+ikaraja+"'";
			stmt = conn.createStatement();
			System.out.println("Haetaan tietokannasta pelejä ikärajalla: " + ikaraja);
			
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String pelinimi = rs.getString("Pelinimi");
				int PeliID = rs.getInt("PeliID");
				String pelityyppi = rs.getString("Pelintyyppi");
				String talletustyyppi = rs.getString("Talletustyyppi");
				int hinta = rs.getInt("Hinta");
				String genre = rs.getString("Genre");
				int ika = rs.getInt("Ikäraja");
				int lukumaara = rs.getInt("Pelaajamäärä");
				String kuvaus = rs.getString("Kuvaus");
				String kaupunki = rs.getString("Kaupunki");
				String konsoli = rs.getString("Konsoli");
				String tekstikenttä = rs.getString("Tekstikenttä");
				String kunto = rs.getString("Kunto");
				Date paivamaara = rs.getDate("Päivämäärä");

				peliLista.add(new Peli(pelinimi, PeliID, pelityyppi, talletustyyppi, hinta, genre, konsoli, ika,
						lukumaara, kuvaus, kaupunki, kunto, tekstikenttä, paivamaara));
			}
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Peli[] pelit = new Peli[peliLista.size()];
		for (int i = 0; i < pelit.length; i++) {
			pelit[i] = peliLista.get(i);
		}
		return pelit;
	}
	public Peli[] pelaajaMaara(int maara) {
		System.out.println("Pelaajienmäärä on " + maara);
		ArrayList<Peli> peliLista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "Select * from Peli where pelaajamäärä ='"+maara+"'";
			stmt = conn.createStatement();
			System.out.println("Haetaan tietokannasta pelejä pelaajamäärällä: " + maara);
			
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String pelinimi = rs.getString("Pelinimi");
				int PeliID = rs.getInt("PeliID");
				String pelityyppi = rs.getString("Pelintyyppi");
				String talletustyyppi = rs.getString("Talletustyyppi");
				int hinta = rs.getInt("Hinta");
				String genre = rs.getString("Genre");
				int ika = rs.getInt("Ikäraja");
				int lukumaara = rs.getInt("Pelaajamäärä");
				String kuvaus = rs.getString("Kuvaus");
				String kaupunki = rs.getString("Kaupunki");
				String konsoli = rs.getString("Konsoli");
				String tekstikenttä = rs.getString("Tekstikenttä");
				String kunto = rs.getString("Kunto");
				Date paivamaara = rs.getDate("Päivämäärä");

				peliLista.add(new Peli(pelinimi, PeliID, pelityyppi, talletustyyppi, hinta, genre, konsoli, ika,
						lukumaara, kuvaus, kaupunki, kunto, tekstikenttä, paivamaara));
			}
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Peli[] pelit = new Peli[peliLista.size()];
		for (int i = 0; i < pelit.length; i++) {
			pelit[i] = peliLista.get(i);
		}
		return pelit;
	}

	/*
	 * Poistetaan peli tietokannasta
	 */
	public void poistaPeli(int peliID) {
		System.out.println("poista peli metodi pelisovellus daossa ja peli id on: " + peliID);
		try {
			String query = "Delete from Peli where PeliID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, peliID);
			preparedStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Haetaan tietyn käyttäjän lisäämät pelit tietokannasta
	 */
	public Peli[] haeOmatPelit(int kayttajaID) {
		ArrayList<Peli> peliLista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			// Käyttäjä id:ksi kirjautuneen käyttäjän id
			String query = "Select * from Peli where KäyttäjäID = ?";
			PreparedStatement preppi = conn.prepareStatement(query);
			preppi.setInt(1, kayttajaID);
			rs = preppi.executeQuery();
			while (rs.next()) {
				String pelinimi = rs.getString("Pelinimi");
				int PeliID = rs.getInt("PeliID");
				String pelityyppi = rs.getString("Pelintyyppi");
				String talletustyyppi = rs.getString("Talletustyyppi");
				int hinta = rs.getInt("Hinta");
				String genre = rs.getString("Genre");
				int ika = rs.getInt("Ikäraja");
				int lukumaara = rs.getInt("Pelaajamäärä");
				String kuvaus = rs.getString("Kuvaus");
				String kaupunki = rs.getString("Kaupunki");
				String konsoli = rs.getString("Konsoli");
				String tekstikenttä = rs.getString("Tekstikenttä");
				String kunto = rs.getString("Kunto");
				Date paivamaara = rs.getDate("Päivämäärä");

				peliLista.add(new Peli(pelinimi, PeliID, pelityyppi, talletustyyppi, hinta, genre, konsoli, ika,
						lukumaara, kuvaus, kaupunki, kunto, tekstikenttä, paivamaara));
			}
		} catch (SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			} while (e.getNextException() != null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Peli[] pelit = new Peli[peliLista.size()];
		for (int i = 0; i < pelit.length; i++) {
			pelit[i] = peliLista.get(i);
		}
		return pelit;
	}

	/*
	 * Päivitetään pelin tiedot tietokataan
	 */
	public boolean paivitaPeli(Peli peli) {
		boolean temp = false;
		System.out
				.println("Nimi " + peli.getPelinNimi() + " Id " + peli.getPeliId() + " Kaupunki " + peli.getKaupunki());
		String query = "UPDATE Peli SET Pelinimi=?,Pelintyyppi=?,Talletustyyppi=?,Hinta=?,Genre=?, Konsoli=?, Ikäraja=?,Pelaajamäärä=?,Kuvaus=?,Kaupunki=?, Kunto=?, Tekstikenttä=? WHERE PeliID=?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, peli.getPelinNimi());
			stmt.setString(2, peli.getPelintyyppi());
			stmt.setString(3, peli.getTalletusTyyppi());
			stmt.setInt(4, peli.getHinta());
			stmt.setString(5, peli.getGenre());
			stmt.setString(6, peli.getKonsoli());
			stmt.setInt(7, peli.getIkaraja());
			stmt.setInt(8, peli.getPelmaara());
			stmt.setString(9, peli.getKuvaus());
			stmt.setString(10, peli.getKaupunki());
			stmt.setString(11, peli.getKunto());
			stmt.setString(12, peli.getTekstikenttä());
			stmt.setInt(13, peli.getPeliId());
			System.out.println(peli.getPeliId() + ", " + peli.getPelinNimi() + " ," + peli.getPelintyyppi() + ", "
					+ peli.getTalletusTyyppi() + ", " + peli.getHinta() + ", " + peli.getGenre() + ", "
					+ peli.getKonsoli() + ", " + peli.getIkaraja() + ", " + peli.getPelmaara() + ", " + peli.getKuvaus()
					+ ", " + peli.getKaupunki() + ", " + peli.getKunto() + ", " + peli.getTekstikenttä() + ", "
					+ peli.getPaiva());
			stmt.executeUpdate();
			temp = true;
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return temp;
	}

	/*
	 * Poistetaan käyttäjä tietokannasta
	 */
	public boolean poistaKayttaja(Kayttaja käyttäjä) {
		boolean test = false;
		try (PreparedStatement poista = conn.prepareStatement("DELETE FROM Käyttäjä WHERE KäyttäjäID=?")) {
			poista.setInt(1, käyttäjä.getKayttajaID());
			poista.executeUpdate();
			test = true;
		} catch (SQLException E) {
			System.out.println(E);
			test = false;
		} catch (Exception e) {
			System.out.println(e);
			test = false;
		}
		return test;
	}

}
