package z3;

//klasa reprezentuj¹ca Produkty 
//kazdy produkt posiada id, asin, liste u¿ytkowników oceniaj¹cych, dane oceny u¿ytkowników,
//liste podobnych produktów oraz liczbê podobynych produktów

//'Jak odczytywaæ Produkty i ich oceny?'
//'Produkt.custId.get(1)' oceni³ Produkt na ocenê 'Produkt.custRating.get(1)'
//listy custId oraz custRating maj¹ tê sam¹ d³ugoœæ

import java.util.ArrayList;
import java.util.List;

public class Produkt {
	private int id;
	private String asin;
	private List<String> custId = new ArrayList<>();
	private List<Integer> custRating = new ArrayList<>();
	private List<String> similar = new ArrayList<>();
	private int similiar_count;
	
	
	public Produkt(int id, String asin, List<String> custId, List<Integer> custRating, List<String> similar,
			int similiar_count) {
		this.id = id;
		this.asin = asin;
		this.custId = custId;
		this.custRating = custRating;
		this.similar = similar;
		this.similiar_count = similiar_count;
	}
	public Produkt(int id) {
		this.id = id;
	}
	public Produkt() {}
	
	
	@Override
	public String toString() {
		return "Produkt [id=" + id + ", asin=" + asin + ", custId=" + custId + ", custRating=" + custRating
				+ ", similar=" + similar + ", similiar_count=" + similiar_count + "]";
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public List<String> getCustId() {
		return custId;
	}
	public void setCustId(List<String> custId) {
		this.custId = custId;
	}
	public List<Integer> getCustRating() {
		return custRating;
	}
	public void setCustRating(List<Integer> custRating) {
		this.custRating = custRating;
	}
	public List<String> getSimilar() {
		return similar;
	}
	public void setSimilar(List<String> similar) {
		this.similar = similar;
	}
	public int getSimiliar_count() {
		return similiar_count;
	}
	public void setSimiliar_count(int similiar_count) {
		this.similiar_count = similiar_count;
	}
	
	
}
