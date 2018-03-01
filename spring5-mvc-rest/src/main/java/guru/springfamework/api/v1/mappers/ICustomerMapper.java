//: guru.springfamework.api.v1.mapper.ICustomerMapper.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.util.UriComponentsBuilder;


@Mapper
public interface ICustomerMapper {

	ICustomerMapper INSTANCE = Mappers.getMapper(ICustomerMapper.class);

	Customer toCustomer(CustomerDTO customerDTO);

    default CustomerDTO toCustomerDTO(Customer customer) {

		if ( customer == null ) {
			return null;
		}

		Long id = customer.getId();
		String uri = (id == null) ? null :
				UriComponentsBuilder.newInstance()
						.path(Mappings.API_V1_CUSTOMERS)
						.path("/")
						.path(Long.toString(id))
						.toUriString();

	    return CustomerDTO.CustomerDTOBuilder.getInstance()
			    .setFirstname(customer.getFirstname())
			    .setLastname(customer.getLastname())
			    .setCustomerUrl(uri)
			    .createCustomerDTO();
	}

}///:~