package entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "specialtys")
public class Specialty{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String specialty;
	private String description;
	
	@ManyToMany(mappedBy = "specialtys")
	private Set<Veterinarian> veterinarians = new HashSet<>();
	
	public Specialty() {
		
	}
	
	public Specialty(String specialty, String description) {
		this.specialty = specialty;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Veterinarian> getVeterinarians() {
		return veterinarians;
	}

	@Override
	public String toString() {
		return id+" - "+specialty;
	}

}
