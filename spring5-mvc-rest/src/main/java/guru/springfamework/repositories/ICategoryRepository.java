//: guru.springfamework.repositories.ICategoryRepository.java


package guru.springfamework.repositories;


import guru.springfamework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICategoryRepository extends JpaRepository<Category, Long> {
	Category findByName(String name);
}///:~