package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "receptionists")
@DiscriminatorValue(value = "RECEPTIONIST")
public class Receptionist extends Employee{

	public Receptionist() {
		
	}
	
	public Receptionist(String name, Double salary, Address address) {
		super(name,salary,address);
	}
	
}
