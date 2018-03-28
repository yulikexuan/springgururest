//: guru.springfamework.domain.model.Authority.java


package guru.springfamework.domain.model;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@EqualsAndHashCode
@ToString
public final class Authority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private final String authority;

	private Authority(String authority) {
		this.authority = authority;
	}

	public static final Builder builder() {
		return new Builder();
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	public static final class Builder {

		private String authority;

		private Builder() {}

		public Builder setAuthority(String authority) {
			this.authority = authority;
			return this;
		}

		public Authority createAuthority() {
			return new Authority(authority);
		}
	}

}///:~