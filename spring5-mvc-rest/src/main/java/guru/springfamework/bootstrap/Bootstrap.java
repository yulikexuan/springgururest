//: guru.springfamework.bootstrap.Bootstrap.java


package guru.springfamework.bootstrap;


import guru.springfamework.domain.Category;
import guru.springfamework.repositories.ICategoryRepository;
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

	@Autowired
	public Bootstrap(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/*
	 * Load up some classed
	 */
	@Override
	public void run(String... args) {

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

	}// End of run()

}///:~