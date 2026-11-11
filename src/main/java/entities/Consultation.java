package entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "consultations")
public class Consultation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@OneToOne
	@JoinColumn(name = "animal_id")
	private Animal animal;
	
	@ManyToOne
	@JoinColumn(name = "veterinarian_id")
	private Veterinarian veterinarian;
	
	private LocalDateTime date;
	
	
	public Consultation() {
		
	}
	
	public Consultation(Client client, Animal animal, Veterinarian veterinarian, LocalDateTime date) {
		this.client = client;
		this.animal = animal;
		this.veterinarian = veterinarian;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Veterinarian getVeterinarian() {
		return veterinarian;
	}

	public void setVeterinarian(Veterinarian veterinarian) {
		this.veterinarian = veterinarian;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Cliente: "+client.getName()+"\n");
		sb.append("Animal: "+animal.getName()+"\n");
		sb.append("Veterinário: "+veterinarian.getName()+"\n");
		sb.append("Data: "+date.format(fm)+"\n");
		return sb.toString();
	}

}
