//: guru.springframework.spring5reactiverest.bootstrap.Bootstrap.java


package guru.springframework.spring5reactiverest.bootstrap;


import guru.springframework.spring5reactiverest.domain.model.Category;
import guru.springframework.spring5reactiverest.domain.model.Vendor;
import guru.springframework.spring5reactiverest.domain.repositories.ICategoryRepository;
import guru.springframework.spring5reactiverest.domain.repositories.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class Bootstrap implements CommandLineRunner {

	private final ICategoryRepository categoryRepository;
	private final IVendorRepository vendorRepository;

	@Autowired
	public Bootstrap(ICategoryRepository categoryRepository,
	                 IVendorRepository vendorRepository) {

		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		this.loadCategories();
		this.loadVendors();
	}

	private void loadVendors() {
		if (this.vendorRepository.count().block() == 0) {
			// Load vendor data
			System.out.println(">>>>>>> Loading Vendor Data on Bootstrap ... ");

			this.vendorRepository.save(Vendor.builder()
					.firstName("Joe")
					.lastName("Buck")
					.build())
					.block();

			this.vendorRepository.save(Vendor.builder()
					.firstName("Micheal")
					.lastName("Weston")
					.build())
					.block();

			this.vendorRepository.save(Vendor.builder()
					.firstName("Jessia")
					.lastName("Waters")
					.build())
					.block();

			this.vendorRepository.save(Vendor.builder()
					.firstName("Bill")
					.lastName("Nershi")
					.build())
					.block();

			this.vendorRepository.save(Vendor.builder()
					.firstName("Jimmy")
					.lastName("Buffett")
					.build())
					.block();

			System.out.println(">>>>>>> Loaded Vendors: " +
					this.vendorRepository.count().block());
		}
	}

	private void loadCategories() {
		if (this.categoryRepository.count().block() == 0) {
			// Load catefory data
			System.out.println(">>>>>>> Loading Category Data on Bootstrap ... ");

			this.categoryRepository.save(Category.builder()
					.description("Fresh")
					.build())
					.block();

			this.categoryRepository.save(Category.builder()
					.description("Nuts")
					.build())
					.block();

			this.categoryRepository.save(Category.builder()
					.description("Dried")
					.build())
					.block();

			this.categoryRepository.save(Category.builder()
					.description("Exotic")
					.build())
					.block();

			System.out.println(">>>>>>> Loaded Categories: " +
					this.categoryRepository.count().block());
		}
	}

}///:~