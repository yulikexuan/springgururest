//: guru.springfamework.domain.repositories.IVendorRepository.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IVendorRepository extends JpaRepository<Vendor, Long> {
}///:~