package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "animals")
public class Animal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String species;
	private String breed;
	private String gender;
	private String weight;
	private LocalDateTime dateofbirth;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Client owner;
	
	@OneToMany(mappedBy = "animal")
	private List<MedicalRecord> medicalRecords = new ArrayList<>();
	
	public Animal() {}
	
	public Animal(String name, String species, String breed, String gender, String weight, LocalDateTime dateOfBirth, Client owner) {
		this.name = name;
		this.species = species;
		this.breed = breed;
		this.gender = gender;
		this.weight = weight;
		this.dateofbirth = dateOfBirth;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}
	
	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public LocalDateTime getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(LocalDateTime dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}

	@Override
	public String toString() {
		return id+" - "+name;
	}

}
