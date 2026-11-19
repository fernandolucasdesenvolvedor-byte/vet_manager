package entities;

import java.time.LocalDate;
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

	
	private String Crmv;
	
	@ManyToMany
	@JoinTable(name = "veterinarian_specialty", joinColumns = @JoinColumn(name = "veterianarian_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty> specialtys = new HashSet<>();
	
	@OneToMany(mappedBy = "veterinarian")
	private List<Consultation> consultations = new ArrayList<>();
	
	public Veterinarian() {
		
	}
	
	public Veterinarian(String name, String cpf, String telephone, Double salary, LocalDate dateOfHire, Address address, String Crmv) {
		super(name,cpf,telephone,salary,dateOfHire,address);
		this.Crmv = Crmv;
	}

	public String getCrmv() {
		return Crmv;
	}

	public void setCrmv(String crmv) {
		Crmv = crmv;
	}

	public Set<Specialty> getSpecialtys() {
		return specialtys;
	}

	public List<Consultation> getConsultations() {
		return consultations;
	}
	
}
