//: guru.springframework.spring5reactiverest.domain.repositories.ICategoryRepository.java


package guru.springframework.spring5reactiverest.domain.repositories;


import guru.springframework.spring5reactiverest.domain.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface ICategoryRepository extends
		ReactiveMongoRepository<Category, String> {
}///:~