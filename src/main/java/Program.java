import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Address;
import entities.Animal;
import entities.Client;
import entities.Consultation;
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
			System.out.println("6 - Agendar consulta.");
			System.out.println("7 - Registrar atendimento veterinário.");
			System.out.println("8 - Consultar prontuário de um animal.");
			System.out.println("9 - Listar consultas de um veterinário.");
			System.out.println("0 - Sair.");
			System.out.println("");
			
			int choose = sc.nextInt();
			sc.nextLine();
			////////
			
			switch(choose) {
			
				case 1:{
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
				}
					
				case 2:{
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
							owner.getAnimals().add(animal);
							em.merge(owner);
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
				}
					
				case 3:{
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
				}
					
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
					
				case 5:{
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
					
					if(veterinariansWithoutSpecialty.isEmpty()) {
						System.out.println("Não tem veterinários disponíveis para vincular essa especialidade atualmente.");
						System.out.println();
						break;
					}
					
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
				}
					
				case 6:{
					System.out.println();
					DateTimeFormatter fm = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
					
					em.getTransaction().begin();
					
					List<Client> clients = em.createQuery("SELECT c FROM Client c", Client.class).getResultList();
					List<Long> clientsId = clients.stream().map(c -> c.getId()).collect(Collectors.toList());
					List<Veterinarian> veterinarians = em.createQuery("SELECT v FROM Veterinarian v", Veterinarian.class).getResultList();
					List<Long> veterinariansId = veterinarians.stream().map(v -> v.getId()).collect(Collectors.toList());;
					
					Long chooseClient = null;
					Long chooseAnimal = null;
					Long chooseVeterinarian = null;
					LocalDateTime dateTime;
					
					em.getTransaction().commit();
					
					if(clients.isEmpty()) {
						System.out.println("Não tem clientes atualmente.");
						break;
					}else if(veterinarians.isEmpty()) {
						System.out.println("Não tem veterinários atualmente.");
						break;
					}
					
					
					///////
					
					while(true) {
						System.out.println("Qual cliente vai fazer o agendamento?");
						
						clients.forEach(System.out::println);
						System.out.println("0 - Cancelar agendamento");
						System.out.println();
						chooseClient = sc.nextLong();
						sc.nextLine();
						
						if(chooseClient == 0L) {
							break;
						}else if(!clientsId.contains(chooseClient)) {
							System.out.println("Opção incorreta!");
							System.out.println("Passe uma opção válida!");
							System.out.println();
						}else {
							break;
						}
					}
					
					
					if(chooseClient == null) {
						break;
					}
					
					em.getTransaction().begin();
					
					Client client = em.find(Client.class, chooseClient);
					
					em.getTransaction().commit();
					
					//////
					
					List<Animal> animals = client.getAnimals();
					List<Long> animalsId = animals.stream().map(a -> a.getId()).collect(Collectors.toList());
					
					if(animals.isEmpty()) {
						System.out.println("Não tem animais atualmente.");
						break;
					}
					
					while(true) {
						System.out.println();
						System.out.println("Qual animal vai ser selecionado pra agendar a consulta?");
						System.out.println();
						System.out.println();
						
						animals.forEach(System.out::println);
						System.out.println("0 - Cancelar agendamento");
						System.out.println();
						
						chooseAnimal = sc.nextLong();
						sc.nextLine();
						
						if(chooseAnimal == 0L) {
							break;
						}else if(!animalsId.contains(chooseAnimal)) {
							System.out.println("Opção incorreta!");
							System.out.println("Passe uma opção válida!");
							System.out.println();
						}else {
							break;
						}
					}
					
					if(chooseAnimal == null) {
						break;
					}
					
					em.getTransaction().begin();
					
					Animal animal = em.find(Animal.class, chooseAnimal);
					
					em.getTransaction().commit();
					
					
					/////
					
					while(true) {
						System.out.println("Quem vai ser o veterinário ?");
						
						veterinarians.forEach(System.out::println);
						System.out.println("0 - Cancelar agendamento");
						System.out.println();
						chooseVeterinarian = sc.nextLong();
						sc.nextLine();
						
						if(chooseVeterinarian == 0L) {
							break;
						}else if(!veterinariansId.contains(chooseVeterinarian)) {
							System.out.println("Opção incorreta!");
							System.out.println("Passe uma opção válida!");
							System.out.println();
						}else {
							break;
						}
					}
					
					if(chooseVeterinarian == null) {
						break;
					}
					
					em.getTransaction().begin();
					
					Veterinarian veterinarian = em.find(Veterinarian.class, chooseVeterinarian);
					
					em.getTransaction().commit();
					
					/////
					
					while(true) {
						System.out.println();
						System.out.println("Qual data do agendamento? (??/??/????)");
						String date = sc.nextLine();
						System.out.println();
						System.out.println("Qual horário do agendamento? (??:??)");
						String time = sc.nextLine();
						dateTime = LocalDateTime.parse(date+" - "+time, fm);
						
						if(dateTime.isBefore(LocalDateTime.now())) {
							System.out.println("Data inválida!");
						}else {
							break;
						}
					}
					
					/////
					
					System.out.println();
					dateTime = dateTime.minusHours(3);
					System.out.println("Agendamento concluído!");
					Consultation consultation = new Consultation(client,animal,veterinarian,dateTime);
					
					em.getTransaction().begin();
					
					em.persist(consultation);
					veterinarian.getConsultations().add(consultation);
					
					em.getTransaction().commit();
					
					break;
				}
				
				case 7:{
					System.out.println("Funcionalidade ainda não implementada!");
					break;
				}
				
				case 8:{
					System.out.println();
					em.getTransaction().begin();
					
					List<Animal> animals = em.createQuery("SELECT a FROM Animal a WHERE EXISTS (SELECT 1 FROM MedicalRecord m WHERE a.id = m.animal.id)", Animal.class).getResultList();
					List<Long> animalsId = animals.stream().map(a -> a.getId()).collect(Collectors.toList());
					
					em.getTransaction().commit();
					
					if(animals.isEmpty()) {
						System.out.println("Não tem animais disponíveis.");
					}
					
					while(true) {
						System.out.println("Qual animal você quer ver os prontuários?");
						animals.forEach(System.out::println);
						System.out.println("0 - Sair");
						
						Long chooseId = sc.nextLong();
						System.out.println();
						
						
						if(chooseId == 0) {
							break;
						}else if(!animalsId.contains(chooseId)) {
							System.out.println("Selecione um animal disponível.");
						}else {
							System.out.println();
							
							em.getTransaction().begin();
							
							Animal animal = em.find(Animal.class, chooseId);
							animal.getMedicalRecords().forEach(m -> {
								System.out.println(m);
							});
							
							em.getTransaction().commit();
							
							break;
						}
					}
					
					break;
				}
				
				case 9:{
					System.out.println();
					em.getTransaction().begin();
					
					List<Veterinarian> veterinarians = em.createQuery("SELECT v FROM Veterinarian v", Veterinarian.class).getResultList();
					List<Long> veterinariansId = veterinarians.stream().map(v -> v.getId()).collect(Collectors.toList());
					
					em.getTransaction().commit();
					
					if(veterinarians.isEmpty()) {
						System.out.println("Não tem veterinários atualmente.");
						break;
					}
					
					while(true) {
						System.out.println("Ver as consultas de qual veterinário?");
						System.out.println();
						
						veterinarians.forEach(System.out::println);
						System.out.println("0 - Sair");
						
						Long chooseId = sc.nextLong();
						
						if(chooseId == 0) {
							break;
						}else if(!veterinariansId.contains(chooseId)) {
							System.out.println("Escolha um veterinário disponível.");
							System.out.println();
						}else {
							em.getTransaction().begin();
							
							Veterinarian veterinarian = em.find(Veterinarian.class, chooseId);
							System.out.println("Consultas do(a) Veterinário: "+veterinarian.getName());
							System.out.println();
							veterinarian.getConsultations().forEach(c -> {
								System.out.println(c);
							});
							
							em.getTransaction().commit();
						}
						
						
						break;
					}
					
					
					
					break;
				}
					
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
