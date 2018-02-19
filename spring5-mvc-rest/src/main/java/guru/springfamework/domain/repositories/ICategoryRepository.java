//: guru.springfamework.domain.repositories.ICategoryRepository.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICategoryRepository extends JpaRepository<Category, Long> {
}///:~