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
	private String kunto;
	private String tekstikenttä;
	private String konsoli;
	
	/*public enum Pelingenre {
		TOIMINTA, JÄNNITYS, RÄISKINTÄ, AJOPELI, SEIKKAILU, STRATEGIA, ROOLIPELI, URHEILUPELI, PULMAPELI, OPPIMISPELI
	}*/
	
	public Peli(String pelinNimi, int peliId, String tyyppi, String talletustyyppi,
			int hinta, String genre, String konsoli, int ikaraja, int pelmaara, String kuvaus, String kaupunki, String kunto, String tekstikenttä) {
		this.peliId = peliId;
		this.pelinNimi = pelinNimi;
		this.tyyppi = tyyppi;
		this.talletusTyyppi =  talletustyyppi;
		this.hinta = hinta;
		this.genre = genre;
		this.konsoli = konsoli;
		this.ikaraja = ikaraja;
		this.pelmaara = pelmaara;
		this.kuvaus = kuvaus;
		this.kaupunki = kaupunki;
		this.kunto = kunto;
		this.tekstikenttä = tekstikenttä;
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
	
	public String getKunto() {
		return kunto;
	}

	public void setKunto(String kunto) {
		this.kunto = kunto;
	}
	
	public String getTekstikenttä() {
		return tekstikenttä;
	}

	public void setTekstikenttä(String tekstikenttä) {
		this.tekstikenttä = tekstikenttä;
	}
	
	public String getKonsoli() {
		return konsoli;
	}

	public void setKonsoli(String konsoli) {
		this.konsoli = konsoli;
	}
	
	// turha jos saa fiksusti toimimaan
	public String toString() {
		return getPelinNimi();
	}
}
