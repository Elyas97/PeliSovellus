package model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Peli {

	private int peliId;
	private String pelinNimi;
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
	private Date paivamaara;
	
	String locale = Locale.getDefault().getLanguage();

	public Peli(String pelinNimi, int peliId, String tyyppi, String talletustyyppi, int hinta, String genre,
			String konsoli, int ikaraja, int pelmaara, String kuvaus, String kaupunki, String kunto,
			String tekstikenttä, Date paivamaara) {
		this.peliId = peliId;
		this.pelinNimi = pelinNimi;
		this.tyyppi = tyyppi;
		this.talletusTyyppi = talletustyyppi;
		this.hinta = hinta;
		this.genre = genre;
		this.konsoli = konsoli;
		this.ikaraja = ikaraja;
		this.pelmaara = pelmaara;
		this.kuvaus = kuvaus;
		this.kaupunki = kaupunki;
		this.kunto = kunto;
		this.tekstikenttä = tekstikenttä;
		this.paivamaara = paivamaara;
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

	public String toString() {
		
		if(locale.equals("fi")) {
			return getPelinNimi() + " Tyyppi: " + talletusTyyppi + "\nHinta: " + hinta + "€\n" + kaupunki + "\n "
					+ paivamaara;
		}else {
			// Päivämäärän formatointi
			DateFormat dateFormat;
			dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
			String paivamaaraFormat = dateFormat.format(paivamaara);
			

			return getPelinNimi() + " Type: " + talletusTyyppi + "\nPrice: " + hinta + "$\n" + kaupunki + "\n "
					+ paivamaaraFormat;
		}
		
	}

	public void setPaivamaara(Date paivamaara) {
		this.paivamaara = paivamaara;
	}

	public Date getPaiva() {
		return paivamaara;
	}
}
