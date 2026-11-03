package entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String street;
	
	@Column(name = "number_street")
	private String numberStreet;
	
	public Address() {}
	
	public Address(String street, String numberStreet) {
		this.street = street;
		this.numberStreet = numberStreet;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumberStreet() {
		return numberStreet;
	}

	public void setNumberStreet(String numberStreet) {
		this.numberStreet = numberStreet;
	}
	
	
	
}
