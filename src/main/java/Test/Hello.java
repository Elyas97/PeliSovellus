package Test;

import java.util.Arrays;
import java.util.Iterator;


import model.Käyttäjä;
import model.LoginSystem;
import model.Peli;
import model.PeliSovellusDAO;
import model.Pelingenre;
import model.RegisterSystem;

public class Hello {
	public static void main(String[] args) {
		System.out.println("hello world!!!!");
		for (int i = 0; i < 10; i++) {
			System.out.println("Testi" + " x" +i);
		}
		PeliSovellusDAO pelisovellusdao=new PeliSovellusDAO();
Käyttäjä[] testi=pelisovellusdao.readKäyttäjät();
		
for(int i=0;i<testi.length;i++) {
System.out.println(testi[i].getEtunimi()+testi[i].getSähköposti());
}

LoginSystem login=new LoginSystem();
Käyttäjä testi23=login.login("elyasa@metropolia.fi", "12345678910");
System.out.println(testi23);
RegisterSystem register=new RegisterSystem();
boolean test=register.register(testi23);
System.out.println(test);
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
