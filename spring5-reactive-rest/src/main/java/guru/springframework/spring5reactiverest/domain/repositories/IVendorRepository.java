//: guru.springframework.spring5reactiverest.domain.repositories.IVendorRepository.java


package guru.springframework.spring5reactiverest.domain.repositories;


import guru.springframework.spring5reactiverest.domain.model.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface IVendorRepository extends
		ReactiveMongoRepository<Vendor, String> {
}///:~