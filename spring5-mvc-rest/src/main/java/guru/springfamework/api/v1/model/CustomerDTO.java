//: guru.springfamework.api.v1.model.CustomerDTO.java


package guru.springfamework.api.v1.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@EqualsAndHashCode
public final class CustomerDTO {

	// DTO class must have non-final fields because of the non-private
	// constructor
	@ApiModelProperty(value="The first name of the customer.", required = true)
	private String firstname;

	@ApiModelProperty(value="The last name of the customer.", required = true)
	private String lastname;

	@ApiModelProperty("The URL of customer")
	private String customerUrl;

	// DTO class must have a non-private default constructor
	CustomerDTO() {}

	private CustomerDTO(String firstname, String lastname, String customerUrl) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.customerUrl = customerUrl;
	}

	public final static class CustomerDTOBuilder {

		private String firstname;
		private String lastname;
		private String customerUrl;

		private CustomerDTOBuilder() {}

		public static CustomerDTOBuilder getInstance() {
			return new CustomerDTOBuilder();
		}

		public CustomerDTOBuilder setFirstname(String firstname) {
			this.firstname = firstname;
			return this;
		}

		public CustomerDTOBuilder setLastname(String lastname) {
			this.lastname = lastname;
			return this;
		}

		public CustomerDTOBuilder setCustomerUrl(String customerUrl) {
			this.customerUrl = customerUrl;
			return this;
		}

		public CustomerDTO createCustomerDTO() {
			return new CustomerDTO(firstname, lastname, customerUrl);
		}

	}//: End of class CustomerDTOBuilder

}///:~