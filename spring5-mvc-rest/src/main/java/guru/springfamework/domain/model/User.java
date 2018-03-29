//: guru.springfamework.domain.model.User.java


package guru.springfamework.domain.model;


import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String username;

	private String password;

	private boolean enabled = true;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "userId")
	private Set<Authority> authorities = new HashSet<>();

	private User(String username, String password,
	             Collection<Authority> authorities) {

		this.username = username;
		this.password = password;
		this.authorities.addAll(authorities);
	}

	public static final Builder builder() {
		return new Builder();
	}

	public void addAuthority(Authority auth) {
		this.authorities.add(auth);
	}

	@Override
	public Collection<Authority> getAuthorities() {
		return Collections.unmodifiableSet(this.authorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public static final class Builder {

		private String username;
		private String password;
		private Set<Authority> authorities = new HashSet<>();

		private Builder() {}

		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder addAuthority(Authority authority) {
			this.authorities.add(authority);
			return this;
		}

		public User createUser() {
			return new User(username, password, authorities);
		}
	}

}///:~