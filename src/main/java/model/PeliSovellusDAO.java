package model;

import java.sql.*;
import java.util.ArrayList;

import view.TapahtumatController;
public class PeliSovellusDAO {
Connection conn;
	
	final String URL = "jdbc:mariadb://10.114.32.16/PELIKESKUS";
	final String USERNAME="ryhma";
	final String PWD="ryhma";
	
	public PeliSovellusDAO() {
		try{
			conn=DriverManager.getConnection(URL +"?user=" + USERNAME + "&password="+PWD);
			System.out.println("Yhdistetty tietokantaan");
		}catch(SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
			}while(e.getNextException()!=null);
			System.exit(-1);
		}
	}
	public boolean createKäyttäjä(Käyttäjä käyttäjä) {
		boolean temp=false;
		try(PreparedStatement luoKäyttäjä= conn.prepareStatement("INSERT INTO Käyttäjä (Salasana, Sähköposti, Sukunimi, Etunimi, Puhelinnumero) VALUES (?,?,?,?,?)"))
		{
			luoKäyttäjä.setString(1, käyttäjä.getSalasana());
			luoKäyttäjä.setString(2, käyttäjä.getSähköposti());
			luoKäyttäjä.setString(3, käyttäjä.getSukunimi());
			luoKäyttäjä.setString(4, käyttäjä.getEtunimi());
			luoKäyttäjä.setInt(5, käyttäjä.getPuhelinumero());
			int count=luoKäyttäjä.executeUpdate();
			temp =true;
			System.out.println(count);
		}catch(SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
				}while(e.getNextException()!=null);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return temp;
	}

	public Käyttäjä readKäyttäjä(String KäyttäjäID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Käyttäjä[] readKäyttäjät() {
		ArrayList <Käyttäjä> lista=new ArrayList();
		Statement stmt=null;
		ResultSet rs=null;
		try {
			stmt=conn.createStatement();
			String query="SELECT * FROM Käyttäjä";
			rs=stmt.executeQuery(query);
			while(rs.next()) {
				 int kayttajaID=rs.getInt("KäyttäjäID");
				 String salasana=rs.getString("Salasana");
				 String etunimi=rs.getString("Etunimi");
				 String sukunimi=rs.getString("Sukunimi");
				 int puhelinumero=rs.getInt("Puhelinnumero");
				 String sähköposti=rs.getString("Sähköposti");
				 lista.add(new Käyttäjä(kayttajaID,salasana,etunimi,sukunimi,puhelinumero,sähköposti));
			}
		}catch(SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
	
			}while(e.getNextException()!=null);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
			} catch(Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Käyttäjä[] users=new Käyttäjä[lista.size()];
		for(int i=0;i<users.length;i++) {
			users[i]=lista.get(i);
		}
		return users;
	}

	public boolean updateKäyttäjä(Käyttäjä käyttäjä) {
		// TODO Auto-generated method stub
		return false;
	}
	/*
	 * Lisää pelin tietokantaan
	 * 
	 */
	public boolean lisaaPeli(Peli peli, int kayttajaID) {
		boolean temp = false;
		System.out.println(peli.getPelinNimi() + " "+ kayttajaID);
		try(PreparedStatement lisaaPeli = conn.prepareStatement("INSERT INTO Peli (Pelinimi, Pelintyyppi, Talletustyyppi, Hinta, Genre, Ikäraja, Pelaajamäärä, Kuvaus, Kaupunki, KäyttäjäID) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
			lisaaPeli.setString(1, peli.getPelinNimi());
			lisaaPeli.setString(2, peli.getPelintyyppi());
			lisaaPeli.setString(3, peli.getTalletusTyyppi());
			lisaaPeli.setInt(4, peli.getHinta());
			lisaaPeli.setString(5, peli.getGenre());
			lisaaPeli.setInt(6, peli.getIkaraja());
			lisaaPeli.setInt(7, peli.getPelmaara());
			lisaaPeli.setString(8, peli.getKuvaus());
			lisaaPeli.setString(9,  peli.getKaupunki());
			lisaaPeli.setInt(10, kayttajaID);
			
			int count = lisaaPeli.executeUpdate();
			temp = true;
			System.out.println(count);
		}catch(SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
				}while(e.getNextException()!=null);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	/*
	 * Hakee kaikki pelit tietokannasta
	 * 
	 * 
	 */
	public Peli[] haePelit() {
		ArrayList <Peli> peliLista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = "Select * from Peli";
			rs=stmt.executeQuery(query);
			while(rs.next()) {
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
				String teksti = rs.getString("Tekstikenttä");
				int kayttajaid = rs.getInt("KäyttäjäID");
				String kunto = "Hyvä";
				//String kunto = rs.getSring("Kunto");
				peliLista.add(new Peli(pelinimi, PeliID, pelityyppi, talletustyyppi, hinta, genre, ika, lukumaara, kuvaus, kaupunki, kunto));
			}
			
		}catch(SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
	
			}while(e.getNextException()!=null);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
			} catch(Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Peli [] pelit = new Peli[peliLista.size()];
		for(int i = 0; i < pelit.length; i++) {
			pelit[i] = peliLista.get(i);
		}
		return pelit;
	}
	/*
	 * Poistaa annetun parametrin mukaisen pelin tietokannasta
	 * 
	 * 
	 */
	public void poistaPeli(int peliID) {
		System.out.println("poista peli metodi pelisovellus daossa ja peli id on: " + peliID);
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String query = "Delete from Peli where PeliID = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, peliID);
			
			preparedStmt.execute();
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Peli[] haeOmatPelit() {
		//System.out.println("haeOmatPelit metodi");
		ArrayList <Peli> peliLista = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			//Käyttäjä id:ksi kirjautuneen käyttäjän id
			String query = "Select * from Peli where KäyttäjäID = 2";
			rs=stmt.executeQuery(query);
			while(rs.next()) {
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
				String teksti = rs.getString("Tekstikenttä");
				int kayttajaid = rs.getInt("KäyttäjäID");
				String kunto = "Hyvä";
				//String kunto = rs.getSring("Kunto");
				peliLista.add(new Peli(pelinimi, PeliID, pelityyppi, talletustyyppi, hinta, genre, ika, lukumaara, kuvaus, kaupunki, kunto));
			}
			
		}catch(SQLException e) {
			do {
				System.out.println("Viesti: " + e.getMessage());
				System.out.println("Virhekoodi: " + e.getErrorCode());
				System.out.println("Viesti: " + e.getSQLState());
	
			}while(e.getNextException()!=null);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null) {
					rs.close();
				}
				if(stmt!=null) {
					stmt.close();
				}
			} catch(Exception e) {
				System.out.println("Resurssien vapautuksessa virhe");
			}
		}
		Peli [] pelit = new Peli[peliLista.size()];
		for(int i = 0; i < pelit.length; i++) {
			pelit[i] = peliLista.get(i);
		}
		//System.out.println(pelit[1].toString());
		return pelit;
	}
}
