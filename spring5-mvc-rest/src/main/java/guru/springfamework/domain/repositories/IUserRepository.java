//: guru.springfamework.domain.repositories.IUserRepository.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}///:~