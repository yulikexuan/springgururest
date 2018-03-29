//: guru.springfamework.api.ApiInfo.java


package guru.springfamework.api;


import lombok.*;


@Getter
@EqualsAndHashCode
@ToString
public class ApiInfo {

	private final String name;
	private final String owner;

	private ApiInfo(String name, String owner) {
		this.name = name;
		this.owner = owner;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private String name;
		private String owner;

		private Builder() {
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setOwner(String owner) {
			this.owner = owner;
			return this;
		}

		public ApiInfo createApiInfo() {
			return new ApiInfo(name, owner);
		}
	}

}///:~