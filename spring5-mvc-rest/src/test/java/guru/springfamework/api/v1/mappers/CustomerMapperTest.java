//: guru.springfamework.api.v1.mappers.CustomerMapperTest.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class CustomerMapperTest {

    class CustomerMapper implements ICustomerMapper {
        @Override
        public Customer toCustomer(CustomerDTO customerDTO) {
            return null;
        }
    }

    private ICustomerMapper customerMapper;

    private Random random;

    @Before
    public void setUp() {
        this.customerMapper = new CustomerMapper();
        this.random = new Random(System.currentTimeMillis());
    }

    @Test
    public void toCustomerDTO() {

        // Given
        Long id = this.random.nextLong();
        String firstname = UUID.randomUUID().toString();
        String lastname = UUID.randomUUID().toString();

        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstname(firstname);
        customer.setLastname(lastname);

        String uriStr = UriComponentsBuilder.newInstance().path(Mappings.API_V1_CUSTOMERS).path("/").path(Long.toString(id)).toUriString();

        // When
        CustomerDTO dto = this.customerMapper.toCustomerDTO(customer);

        // Then
        assertThat(dto.getCustomerUrl(), is(uriStr));
    }

}///:~