//: guru.springfamework.api.v1.mapper.ICustomerMapper.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ICustomerMapper {

	ICustomerMapper INSTANCE = Mappers.getMapper(ICustomerMapper.class);

    default CustomerDTO toCustomerDTO(Customer customer) {

		if ( customer == null ) {
			return null;
		}

		CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setFirstname( customer.getFirstname() );
		customerDTO.setLastname( customer.getLastname() );
		customerDTO.setCustomerUrl(Mappings.API_V1_CUSTOMERS + customer.getId());

		return customerDTO;
	}

}///:~