import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Address;
import entities.Animal;
import entities.Client;

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
