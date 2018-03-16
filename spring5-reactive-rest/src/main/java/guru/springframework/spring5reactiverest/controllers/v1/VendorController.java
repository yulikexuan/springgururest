//: guru.springframework.spring5reactiverest.controllers.v1.VendorController.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Vendor;
import guru.springframework.spring5reactiverest.domain.repositories.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
public class VendorController {

	private final IVendorRepository vendorRepository;


	public VendorController(IVendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	@GetMapping("/api/v1/vendors")
	public Flux<Vendor> list() {
		return this.vendorRepository.findAll();
	}

	@GetMapping("/api/v1/vendors/{id}")
	public Mono<Vendor> getById(@PathVariable String id) {
		return this.vendorRepository.findById(id);
	}

}///:~