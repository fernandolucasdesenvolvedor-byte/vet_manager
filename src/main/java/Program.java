import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Address;
import entities.Animal;
import entities.Client;
import entities.Receptionist;
import entities.Specialty;
import entities.Veterinarian;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("vet_manager");
		EntityManager em = emf.createEntityManager();
		////////////////////////////////////////////////////////////////////////////////////////
		
		
		
		while(true) {
			
			System.out.println("Gerenciamento Vet Manager");
			System.out.println("");
			System.out.println("");
			System.out.println("1 - Cadastrar cliente.");
			System.out.println("2 - Cadastrar animal.");
			System.out.println("3 - Cadastrar funcionário.");
			System.out.println("4 - Cadastrar especialidade.");
			System.out.println("5 - Vincular especialidade ao veterinário.");
			System.out.println("0 - Sair.");
			System.out.println("");
			
			int choose = sc.nextInt();
			sc.nextLine();
			////////
			
			switch(choose) {
			
				case 1:
					System.out.println();
					System.out.println("Qual nome do cliente?");
					String nameClient = sc.nextLine();
					System.out.println("Qual nome da rua?");
					String streetClient = sc.nextLine();
					System.out.println("Qual número da rua?");
					String numberStreetClient = sc.nextLine();
					
					Address addressClient = new Address(streetClient,numberStreetClient);
					Client client = new Client(nameClient,addressClient);
					
					em.getTransaction().begin();;
					
					em.persist(client);
					System.out.println("Cliente registrado com sucesso!");
					
					em.getTransaction().commit();;
					
					System.out.println();
					break;
					
				case 2:
					System.out.println();
					System.out.println("Qual nome do animal?");
					String nameAnimal = sc.nextLine();
					
					em.getTransaction().begin();;
					
					List<Client> clients = em.createNamedQuery("Client.findAll", Client.class).getResultList();
					
					while(true) {
						
						System.out.println();
						System.out.println("Quem é o dono?");
						System.out.println();
						
						clients.forEach(c -> {
							System.out.println(c);
						});
						System.out.println("0 - Cancelar registro do animal.");
						System.out.println();
						
						Long chooseOwner = sc.nextLong();
						
						if(em.contains(em.find(Client.class, chooseOwner))) {
							Client owner = em.find(Client.class, chooseOwner);
							Animal animal = new Animal(nameAnimal, owner);
							em.persist(animal);
							System.out.println("Animal registrado com sucesso!");
							break;
						}else if(chooseOwner == 0){
							break;
						}else {
							System.out.println("Não existe esse cliente.");
							System.out.println("Escolha um cliente disponível.");
							System.out.println("");
						}
					}
					
					em.getTransaction().commit();
					
					System.out.println();
					break;
					
				case 3:
					System.out.println();
					System.out.println("Qual nome do funcionário?");
					String nameEmployee = sc.nextLine();
					System.out.println("Qual nome da rua?");
					String streetEmployee = sc.nextLine();
					System.out.println("Qual número da rua?");
					String numberStreetEmployee = sc.nextLine();
					
					Address addressEmployee = new Address(streetEmployee,numberStreetEmployee);
					
					
					while(true) {
						
						System.out.println("Que tipo de funcionário?");
						System.out.println("");
						System.out.println("1 - Veterinário");
						System.out.println("2 - Recepcionista");
						System.out.println("0 - Cancelar cadastro de funcionário");
						System.out.println("");
						
						int chooseWorker = sc.nextInt();
						sc.nextLine();
						
						switch(chooseWorker) {
							case 1: {
								System.out.println("");
								System.out.println("Qual salário do funcionário?");
								Double salaryEmployee = sc.nextDouble();
								Veterinarian veterinarian = new Veterinarian(nameEmployee, salaryEmployee, addressEmployee);
								em.getTransaction().begin();
								em.persist(veterinarian);
								em.getTransaction().commit();
								break;
							}
							case 2: {
								System.out.println("");
								System.out.println("Qual salário do funcionário?");
								Double salaryEmployee = sc.nextDouble();
								Receptionist receptionist = new Receptionist(nameEmployee, salaryEmployee, addressEmployee);
								em.getTransaction().begin();
								em.persist(receptionist);
								em.getTransaction().commit();
								break;
							}
							case 0:
								break;
							default:
								System.out.println("Opção Incorreta!");
								break;
						}
						
						if(chooseWorker == 1 || chooseWorker == 2 || chooseWorker == 0) {
							break;
						}
					}
					
					
					
					
					break;
					
				case 4:{
					System.out.println();
					System.out.println("Qual nome da especialidade?");
					String nameSpecialty = sc.nextLine();
					
					em.getTransaction().begin();
					
					List<Specialty> specialtys = em.createQuery("SELECT s FROM Specialty s", Specialty.class).getResultList();
					
					
					if(!specialtys.stream().anyMatch(s -> s.getSpecialty().equals(nameSpecialty))) {
						em.persist(new Specialty(nameSpecialty));
						System.out.println("Especialidade cadastrada!");
						System.out.println();
					}else {
						System.out.println();
						System.out.println("Já Existe essa especialidade!");
					}
					
					em.getTransaction().commit();
					
					break;
				}
					
				case 5:
					System.out.println();
					Specialty specialty = null;
					Veterinarian veterinarian = null;
					
					em.getTransaction().begin();
					
					List<Specialty> specialtys = em.createQuery("SELECT s FROM Specialty s",Specialty.class).getResultList();
					List<Long> specialtysIds = specialtys.stream().map(s -> s.getId()).collect(Collectors.toList());
					List<Veterinarian> veterinarians = em.createQuery("SELECT v FROM Veterinarian v",Veterinarian.class).getResultList();
					
					em.getTransaction().commit();
					
					if(specialtys.isEmpty()) {
						System.out.println("Não tem especialidades atualmente.");
						break;
					}else if(veterinarians.isEmpty()) {
						System.out.println("Não tem veterinários atualmente.");
						break;
					}
					
					
					while(true) {
						System.out.println("Qual especialidade vai ser vinculada?");
						System.out.println();
						specialtys.forEach(System.out::println);
						System.out.println("0 - Cancelar a vinculação.");
						
						Long chooseSpecialty = sc.nextLong();
						
						if(chooseSpecialty == 0L){
							System.out.println();
							break;
						}else if(!specialtysIds.contains(chooseSpecialty)) {
							System.out.println("Opção incorreta!");
							System.out.println("Passe uma opção válida!");
							System.out.println();
						}else {
							em.getTransaction().begin();
							
							specialty = em.find(Specialty.class, chooseSpecialty);
							
							em.getTransaction().commit();
							System.out.println();
							break;
						}
					}
					
					if(specialty == null) {
						break;
					}
					
					Specialty specialtyChoose = specialty;
					List<Veterinarian> veterinariansWithoutSpecialty = veterinarians.stream().filter(v -> !specialtyChoose.getVeterinarians().contains(v)).collect(Collectors.toList());
					List<Long> veterinariansWithoutSpecialtyIds = veterinariansWithoutSpecialty.stream().map(s -> s.getId()).collect(Collectors.toList());
					
					//////
					
					while(true) {
						System.out.println("Para qual veterinário essa especialidade vai ser vinculada?");
						System.out.println();
						veterinariansWithoutSpecialty.forEach(System.out::println);
						System.out.println("0 - Cancelar a vinculação.");
						
						Long chooseVeterinarian = sc.nextLong();
						
						if(chooseVeterinarian == 0L){
							System.out.println();
							break;
						}else if(!veterinariansWithoutSpecialtyIds.contains(chooseVeterinarian)) {
							System.out.println("Opção incorreta!");
							System.out.println("Passe uma opção válida!");
							System.out.println();
						}else {
							em.getTransaction().begin();
							
							veterinarian = em.find(Veterinarian.class, chooseVeterinarian);
							specialtyChoose.getVeterinarians().add(veterinarian);
							
							em.getTransaction().commit();
							System.out.println();
							break;
						}
					}
					
					if(veterinarian == null) {
						break;
					}
					
					//////
					
					veterinarian.getSpecialtys().add(specialtyChoose);
					
					em.getTransaction().begin();
					
					em.merge(veterinarian);
					
					em.getTransaction().commit();
					
					break;
					
				case 0:
					System.out.println("Saiu do Gerenciamento...");
					break;
					
				default:
					System.out.println("Comando Incorreto...");
					break;
			
			}
			
			
			if(choose == 0) {
				break;
			}
		}
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		////////////////////////////////////////////////////////////////////////////////////////
		em.close();
		emf.close();
		sc.close();

	}

}
