//: guru.springframework.spring5reactiverest.controllers.v1.VendorController.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Vendor;
import guru.springframework.spring5reactiverest.domain.repositories.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
	Flux<Vendor> list() {
		return this.vendorRepository.findAll();
	}

	@GetMapping("/api/v1/vendors/{id}")
	Mono<Vendor> getById(@PathVariable String id) {
		return this.vendorRepository.findById(id);
	}

	@PostMapping("/api/v1/vendors")
	@ResponseStatus(HttpStatus.CREATED)
	Mono<Void> create(@RequestBody Publisher<Vendor> vendorsStream) {
		return this.vendorRepository.saveAll(vendorsStream).then();
	}

	@PutMapping("/api/v1/vendors/{id}")
	@ResponseStatus(HttpStatus.OK)
	Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
		vendor.setId(id);
		return this.vendorRepository.save(vendor);
	}

}///:~