package entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "veterinarians")
@DiscriminatorValue(value = "VETERINARIAN")
public class Veterinarian extends Employee{

	@ManyToMany
	@JoinTable(name = "veterinarian_specialty", joinColumns = @JoinColumn(name = "veterianarian_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty> specialtys = new HashSet<>();
	
	@OneToMany(mappedBy = "veterinarian")
	private List<Consultation> consultations = new ArrayList<>();
	
	public Veterinarian() {
		
	}
	
	public Veterinarian(String name, Double salary, Address address) {
		super(name,salary,address);
	}

	public Set<Specialty> getSpecialtys() {
		return specialtys;
	}

	public List<Consultation> getConsultations() {
		return consultations;
	}
	
}
