package model;

public class Peli {

	private int peliId;
	private String pelinNimi;
	private String pelinTyyppi;
	private int tietoId;

	
	public Peli(int peliId, String pelinNimi, String pelinTyyppi, int tietoId) {
		this.peliId = peliId;
		this.pelinNimi = pelinNimi;
		this.pelinTyyppi = pelinTyyppi;
		this.tietoId = tietoId;
	}
	
	public int getPeliId() {
		return peliId;
	}
	
	public void setPeliId(int peliId) {
		this.peliId = peliId;
	}
	
	public String getPelinNimi() {
		return pelinNimi;
	}
	
	public void setPelinTyyppi(String pelinTyyppi) {
		this.pelinTyyppi = pelinTyyppi;
	}
	
	public int getTietoId(int tietoId) {
		return tietoId;
	}
	
	public void setTietoId(int tietoId) {
		this.tietoId = tietoId;
	}
}
