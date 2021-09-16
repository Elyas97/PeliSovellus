package model;


public class Peli {

	private int peliId;
	private String pelinNimi;
	private int tietoId;
	private Pelintyyppi tyyppi;

	public enum Pelintyyppi {
		DEFAULT, LAUTAPElI, VIDEOPELI
	}
	
	public Peli(int peliId, String pelinNimi, Pelintyyppi tyyppi, int tietoId) {
		this.peliId = peliId;
		this.pelinNimi = pelinNimi;
		this.tietoId = tietoId;
		this.tyyppi = tyyppi;
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
	
	public void setPelinTyyppi(Pelintyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	
	public Pelintyyppi getPelintyyppi() {
		return tyyppi;
	}
	
	public int getTietoId() {
		return tietoId;
	}
	
	public void setTietoId(int tietoId) {
		this.tietoId = tietoId;
	}
}
