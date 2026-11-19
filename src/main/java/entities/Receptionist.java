package entities;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "receptionists")
@DiscriminatorValue(value = "RECEPTIONIST")
public class Receptionist extends Employee{

	private String workShift;
	
	public Receptionist() {
		
	}
	
	public Receptionist(String name, String cpf, String telephone, Double salary, LocalDate dateOfHire, Address address, String workShift) {
		super(name,cpf,telephone,salary,dateOfHire,address);
		this.workShift = workShift;
	}

	public String getWorkShift() {
		return workShift;
	}

	public void setWorkShift(String workShift) {
		this.workShift = workShift;
	}
	
	
	
}
