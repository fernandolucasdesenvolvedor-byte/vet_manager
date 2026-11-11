package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "animal_id")
	private Animal animal;
	private String descriptionMedical;
	
	public MedicalRecord() {
		
	}
	
	public MedicalRecord(Animal animal, String descriptionMedical) {
		this.animal = animal;
		this.descriptionMedical = descriptionMedical;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public String getDescriptionMedical() {
		return descriptionMedical;
	}

	public void setDescriptionMedical(String descriptionMedical) {
		this.descriptionMedical = descriptionMedical;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-----"+id+"-----\n");
		sb.append("Nome do Animal: "+animal.getName()+"\n");
		sb.append("Nome do Cliente: "+animal.getOwner().getName()+"\n");
		sb.append("Descrição Médica: "+descriptionMedical+"\n");
	
		return sb.toString();
	}
	
}
