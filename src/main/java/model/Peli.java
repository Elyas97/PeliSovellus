package model;


public class Peli {

	private int peliId;
	private String pelinNimi;
	//private int tietoId;
	private String tyyppi;
	private String talletusTyyppi;
	private int hinta; 
	private String genre;
	private int ikaraja;
	private int pelmaara;
	private String kuvaus;
	private String kaupunki;
	
	

	public enum Pelintyyppi {
		DEFAULT, LAUTAPElI, VIDEOPELI
	}
	
	public Peli(String pelinNimi, int peliId, String tyyppi, String talletustyyppi,
			int hinta, String genre, int ikaraja, int pelmaara, String kuvaus, String kaupunki) {
		this.peliId = peliId;
		this.pelinNimi = pelinNimi;
		this.tyyppi = tyyppi;
		this.talletusTyyppi =  talletustyyppi;
		this.hinta = hinta;
		this.genre = genre;
		this.ikaraja = ikaraja;
		this.pelmaara = pelmaara;
		this.kuvaus = kuvaus;
		this.kaupunki = kaupunki;
	}
	
	public Peli() {
		
	}
	
	public int getPeliId() {
		return peliId;
	}
	
	public void setPeliId(int peliId) {
		this.peliId = peliId;
	}
	
	public void setPelinNimi(String pelinNimi) {
		this.pelinNimi = pelinNimi;
	}
	
	public String getPelinNimi() {
		return pelinNimi;
	}
	
	public void setPelinTyyppi(String tyyppi) {
		this.tyyppi = tyyppi;
	}
	
	public String getPelintyyppi() {
		return tyyppi;
	}
	

	public String getTalletusTyyppi() {
		return talletusTyyppi;
	}

	public void setTalletusTyyppi(String talletusTyyppi) {
		this.talletusTyyppi = talletusTyyppi;
	}

	public int getHinta() {
		return hinta;
	}

	public void setHinta(int hinta) {
		this.hinta = hinta;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getIkaraja() {
		return ikaraja;
	}

	public void setIkaraja(int ikaraja) {
		this.ikaraja = ikaraja;
	}

	public int getPelmaara() {
		return pelmaara;
	}

	public void setPelmaara(int pelmaara) {
		this.pelmaara = pelmaara;
	}

	public String getKuvaus() {
		return kuvaus;
	}

	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}

	public String getKaupunki() {
		return kaupunki;
	}

	public void setKaupunki(String kaupunki) {
		this.kaupunki = kaupunki;
	}
}
