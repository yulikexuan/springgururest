//: guru.springfamework.bootstrap.Bootstrap.java


package guru.springfamework.bootstrap;


import guru.springfamework.domain.model.*;
import guru.springfamework.domain.repositories.ICategoryRepository;
import guru.springfamework.domain.repositories.ICustomerRepository;
import guru.springfamework.domain.repositories.IUserRepository;
import guru.springfamework.domain.repositories.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final PasswordEncoder passwordencoder;

	private final IUserRepository userRepository;
	private final ICategoryRepository categoryRepository;
	private final ICustomerRepository customerRepository;
	private final IVendorRepository vendorRepository;

	public Bootstrap(PasswordEncoder passwordencoder,
	                 IUserRepository userRepository,
	                 ICategoryRepository categoryRepository,
	                 ICustomerRepository customerRepository,
	                 IVendorRepository vendorRepository) {

		this.passwordencoder = passwordencoder;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	/*
	 * Load up some classed
	 */
	@Override
	public void run(String... args) {
		this.loadUsers();
		this.loadCategories();
		this.loadCustomers();
		this.loadVendors();
	}// End of run()

	private void loadUsers() {

		String defaultPassword = "password";
		String encryptedPw = this.passwordencoder.encode(defaultPassword);
		System.out.println("Encrypted password: " + encryptedPw);

		User user1 = User.builder()
				.setUsername("yli")
				.setPassword(encryptedPw)
				.addAuthority(
						Authority.builder()
								.setAuthority("ROLE_USER")
								.createAuthority())
				.createUser();

		User user2 = User.builder()
				.setUsername("admin")
				.setPassword(encryptedPw)
				.addAuthority(
						Authority.builder()
								.setAuthority("ROLE_USER")
								.setAuthority("ROLE_ADMIN")
								.createAuthority())
				.createUser();

		this.userRepository.save(user1);
		this.userRepository.save(user2);

		System.out.println(">>>>>>> Users Loaded = " +
				this.userRepository.count());
	}

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

	private void loadVendors() {

		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("Western Tasty Fruits Ltd.");
		this.vendorRepository.save(vendor1);

		Vendor vendor2 = new Vendor();
		vendor2.setId(2L);
		vendor2.setName("Exotic Fruits Company");
		this.vendorRepository.save(vendor2);

		Vendor vendor3 = new Vendor();
		vendor3.setId(3L);
		vendor3.setName("Home Fruits");
		this.vendorRepository.save(vendor3);

		Vendor vendor4 = new Vendor();
		vendor4.setId(4L);
		vendor4.setName("Fun Fresh Fruits Ltd.");
		this.vendorRepository.save(vendor4);

		Vendor vendor5 = new Vendor();
		vendor5.setId(5L);
		vendor5.setName("Nuts for Nuts Company");
		this.vendorRepository.save(vendor5);

		System.out.println(">>>>>>> Vendors Loaded = " +
				this.vendorRepository.count());
	}

}///:~