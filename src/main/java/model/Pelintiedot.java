package model;

public class Pelintiedot {
	
	
	private int tietoId;
	private String genre;
	private int ikäraja;
	private String kuvaus;

	public Pelintiedot(int tietoId, String genre, int ikäraja, String kuvaus) {
		this.tietoId = tietoId;
		this.genre = genre;
		this.ikäraja = ikäraja;
		this.kuvaus = kuvaus;
	}
	
	public int getTietoId() {
		return tietoId;
	}
	
	public void setTietoId(int tietoId) {
		this.tietoId = tietoId;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public int getIkäraja() {
		return ikäraja;
	}
	
	public void setIkäraja(int ikäraja) {
		this.ikäraja = ikäraja;
	}
	
	public String getKuvaus() {
		return kuvaus;
	}
	
	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}
	
}

