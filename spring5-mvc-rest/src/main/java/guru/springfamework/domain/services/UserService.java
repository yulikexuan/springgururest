//: guru.springfamework.domain.services.UserService.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.User;
import guru.springfamework.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

	private final IUserRepository userRepository;

	@Autowired
	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws
			UsernameNotFoundException {

		User user = this.userRepository.findByUsername(username);

		if (user != null) {
			return user;
		}

		throw new UsernameNotFoundException("User '" + username +
				"' not found!");
	}

}///:~