package model;

import java.sql.*;
import java.util.ArrayList;
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
	

}
