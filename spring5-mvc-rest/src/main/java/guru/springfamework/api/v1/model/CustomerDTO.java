//: guru.springfamework.api.v1.model.CustomerDTO.java


package guru.springfamework.api.v1.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private String firstname;
	private String lastname;
	private String customerUrl;

}///:~