package model;

public class Pelintiedot {
	
	private int tietoId;
	private Kunto kunto;
	private Pelingenre genre;
	private int ikäraja;
	private String kuvaus;
	
	public enum Pelingenre {
		TOIMINTA, JÄNNITYS, RÄISKINTÄ, AJOPELI, SEIKKAILU, STRATEGIA, ROOLIPELI, URHEILUPELI, PULMAPELI, OPPIMISPELI
	}
	
	/*public enum Kunto {
		VÄLTTÄVÄ, TYYDYTTÄVÄ, HYVÄ, KIITETTÄVÄ, ERINOMAINEN
	}*/

	public Pelintiedot(int tietoId, Kunto kunto, Pelingenre genre, int ikäraja, String kuvaus) {
		this.tietoId = tietoId;
		this.kunto = kunto;
		this.genre = genre;
		this.ikäraja = ikäraja;
		this.kuvaus = kuvaus;
	}
	
	public Pelintiedot() {
		
	}


	public int getTietoId() {
		return tietoId;
	}
	
	public void setTietoId(int tietoId) {
		this.tietoId = tietoId;
	}
	
	public void setKunto(Kunto kunto) {
		this.kunto = kunto;
	}
	
	public Kunto getKunto() {
		return kunto;
	}
	
	public Pelingenre getGenre() {
		return genre;
	}
	
	public void setGenre(Pelingenre genre) {
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

