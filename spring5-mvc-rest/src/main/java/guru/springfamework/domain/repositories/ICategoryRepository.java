//: guru.springfamework.domain.repositories.ICategoryRepository.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}///:~