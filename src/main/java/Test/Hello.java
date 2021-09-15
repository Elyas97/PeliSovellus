package Test;

import java.util.Iterator;


import model.Käyttäjä;
import model.PeliSovellusDAO;

public class Hello {
	public static void main(String[] args) {
		System.out.println("hello world!!!!");
		for (int i = 0; i < 10; i++) {
			System.out.println("Testi" + " x" +i);
		}
		PeliSovellusDAO pelisovellusdao=new PeliSovellusDAO();
	//	Käyttäjä[] testi=pelisovellusdao.readKäyttäjät();
		
	//	for(int i=0;i<testi.length;i++) {
		//	System.out.println(testi[i].getEtunimi()+testi[i].getSähköposti());
	//	}
	}

}
