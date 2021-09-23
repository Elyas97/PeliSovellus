package controller;

import java.util.Scanner;

import model.Peli;
import model.Peli.Pelintyyppi;

public class LisääPeli {
	
	public static void main(String[] args) {
		
		Peli peli = new Peli();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("LISÄÄ PELI.");
		
		System.out.println("Anna pelille id:");
		int pelinId = scanner.nextInt(); 
		peli.setPeliId(pelinId);
		scanner.nextLine();
		
		System.out.println("Anna pelille nimi:");
		String pelinNimi = scanner.nextLine(); 
		peli.setPelinNimi(pelinNimi);

	    System.out.println("Valitse pelin tyyppi (video/lauta):");
	    String tyyppi = scanner.nextLine(); 
	    
	    Pelintyyppi pelintyyppi;
	    if(tyyppi.equals("video")) {
	    	pelintyyppi = Pelintyyppi.VIDEOPELI;
	    }else {
	    	pelintyyppi = Pelintyyppi.LAUTAPElI;
	    }
	    //peli.setPelinTyyppi(pelintyyppi);
	    
	    System.out.println("Anna pelin tiedoille id:");
	    int tietoId = scanner.nextInt(); 
	   // peli.setTietoId(tietoId);
	    
	    //System.out.println("Peli " + peli.getPeliId()+ ", " + peli.getPelinNimi() + ", " + peli.getPelintyyppi() + ", " + peli.getTietoId());
	}	
}
