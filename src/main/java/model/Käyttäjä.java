package model;

public class Käyttäjä {
	
	private int id;
	private String etunimi;
	private String sukunimi;
	private String käyttäjätunnus;
	private String sähköposti;
	private String salasana;
	private int puhelinnumero;
	

	
	public Käyttäjä(int id, String etunimi, String sukunimi, String käyttäjätunnus, String sähköposti, String salasana, int puhelinnumero) {
		this.id = id;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.käyttäjätunnus = käyttäjätunnus;
		this.sähköposti = sähköposti;
		this.salasana = salasana;
		this.puhelinnumero = puhelinnumero;
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
	
	public String getKäyttäjätunnus(String käyttäjätunnus) {
		return käyttäjätunnus;
	}
	
	public void setKäyttäjätunnus(String käyttäjätunnus) {
		this.käyttäjätunnus = käyttäjätunnus;
	}
	
	public String getSähköposti(String sähköposti) {
		return sähköposti;
	}
	
	public void setSähköposti(String sähköposti) {
		this.sähköposti = sähköposti;
	}
	
	public String getSalasana(String salasana) {
		return salasana;
	}
	
	public void setSalasana(String salasana) {
		this.salasana = salasana;
	}
	
	public int getPuhelinnumero(int puhelinnumero) {
		return puhelinnumero;
	}
	
	public void setPuhelinnumero(int puhelinnumero) {
		this.puhelinnumero = puhelinnumero;
	}
	
	
	
}
