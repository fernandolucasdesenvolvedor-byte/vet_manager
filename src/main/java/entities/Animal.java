package entities;

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
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Client owner;
	
	@OneToMany(mappedBy = "animal")
	private List<MedicalRecord> medicalRecords = new ArrayList<>();
	
	public Animal() {}
	
	public Animal(String name, Client owner) {
		this.name = name;
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
	
	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}

	@Override
	public String toString() {
		return id+" - "+name;
	}

}
