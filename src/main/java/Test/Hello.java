package Test;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;


import model.Kayttaja;
import model.LoginSystem;
import model.Peli;
import model.PeliSovellusDAO;
import model.Pelingenre;
import model.RegisterSystem;
import model.TiedostoKasittely;

public class Hello {
	public static void main(String[] args) {
		System.out.println("hello world!!!!");
		for (int i = 0; i < 10; i++) {
			System.out.println("Testi" + " x" +i);
		}
		PeliSovellusDAO pelisovellusdao=new PeliSovellusDAO();
Kayttaja[] testi=pelisovellusdao.readKäyttäjät();
		
for(int i=0;i<testi.length;i++) {
System.out.println(testi[i].getEtunimi()+testi[i].getSähköposti());
}

LoginSystem login=new LoginSystem();
Kayttaja testi23=login.login("mikko@hotmail.com", "123456");
System.out.println("Alku"+ testi23.getKayttajaID() +" "+ testi23.getEtunimi());
TiedostoKasittely.kirjoitaTiedosto(testi23);
TiedostoKasittely.poistaTiedosto();

Kayttaja testi24=TiedostoKasittely.lueKäyttäjä();


System.out.println("loppu");

//Pelingenre peli = null;
//System.out.println(Arrays.toString(Pelingenre.values()));
//Peli[] pelit = pelisovellusdao.haePelit();

//for (int i = 0; i < pelit.length; i++) {
	//System.out.println(pelit[i].getPelinNimi());
	//System.out.println(pelit[i].getPelinNimi()+", "+pelit[i].getHinta());
//}
/*
 * 
 * TESTAILUA
 * 
 */
//pelisovellusdao.poistaPeli(5);

//Peli uusiPeli = new Peli("Nhl", 5, "Video", "Myynti", 32, "Urheilu", 7, 2, "kuvaus", "Helsinki");
//pelisovellusdao.lisaaPeli(uusiPeli, testi[0].getKayttajaID());



	
	}

}
