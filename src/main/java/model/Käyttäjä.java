package model;

import java.io.Serializable;

public class Käyttäjä implements Serializable {
	private static final long serialVersionUID = 1L;
	private int kayttajaID;
	private String salasana;
	private String etunimi;
	private String sukunimi;
	private int puhelinumero;
	private String sähköposti;
	public Käyttäjä(int kayttajaID, String salasana, String etunimi, String sukunimi, int puhelinumero,
			 String sähköposti) {
		super();
		this.kayttajaID = kayttajaID;
		this.salasana = salasana;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.puhelinumero = puhelinumero;
		this.sähköposti = sähköposti;
	}
	public Käyttäjä() {};
	public int getKayttajaID() {
		return kayttajaID;
	}
	public void setKayttajaID(int kayttajaID) {
		this.kayttajaID = kayttajaID;
	}
	public String getSalasana() {
		return salasana;
	}
	public void setSalasana(String salasana) {
		this.salasana = salasana;
	}
	public String getEtunimi() {
		return etunimi;
	}
	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}
	public String getSukunimi() {
		return sukunimi;
	}
	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}
	public int getPuhelinumero() {
		return puhelinumero;
	}
	public void setPuhelinumero(int puhelinumero) {
		this.puhelinumero = puhelinumero;
	}
	public String getSähköposti() {
		return sähköposti;
	}
	public void setSähköposti(String sähköposti) {
		this.sähköposti = sähköposti;
	};
	
	
	

}
