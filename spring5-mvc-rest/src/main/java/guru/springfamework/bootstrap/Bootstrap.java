//: guru.springfamework.bootstrap.Bootstrap.java


package guru.springfamework.bootstrap;


import guru.springfamework.domain.model.Category;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICategoryRepository;
import guru.springfamework.domain.repositories.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/*
 * CommandLineRunner interface used to indicate that a bean should run when it
 * is contained within a SpringApplication
 *
 * Multiple CommandLineRunner beans can be defined within the same application
 * context and can be ordered using the Ordered interface or @Order annotation
 *
 * Command Runner is a Spring Boot specific class of loading data and only going
 * to work with Spring Boot
 *
 * The run method of this class gets called on startup and any arguments that
 * have been passed into the JVM will also get picked up here
 */
@Component
public class Bootstrap implements CommandLineRunner {

	private final ICategoryRepository categoryRepository;
	private final ICustomerRepository customerRepository;

	@Autowired
	public Bootstrap(ICategoryRepository categoryRepository,
	                 ICustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	/*
	 * Load up some classed
	 */
	@Override
	public void run(String... args) {
		this.loadCategories();
		this.loadCustomers();
	}// End of run()

	private void loadCategories() {

		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		this.categoryRepository.save(fruits);
		this.categoryRepository.save(dried);
		this.categoryRepository.save(fresh);
		this.categoryRepository.save(exotic);
		this.categoryRepository.save(nuts);

		System.out.println(">>>>>>> Categories loaded = " +
				this.categoryRepository.count());
	}

	private void loadCustomers() {

		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Michale");
		customer1.setLastname("Weston");
		this.customerRepository.save(customer1);

		Customer customer2 = new Customer();
		customer2.setId(2l);
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");
		this.customerRepository.save(customer2);

		Customer customer3 = new Customer();
		customer3.setId(3l);
		customer3.setFirstname("Bill");
		customer3.setLastname("Gates");
		this.customerRepository.save(customer3);

		Customer customer4 = new Customer();
		customer4.setId(4l);
		customer4.setFirstname("Jobs");
		customer4.setLastname("Steve");
		this.customerRepository.save(customer4);

		System.out.println(">>>>>>> Customers Loaded = " +
				this.customerRepository.count());
	}

}///:~