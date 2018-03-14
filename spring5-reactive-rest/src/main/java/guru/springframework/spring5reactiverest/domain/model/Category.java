//: guru.springframework.spring5reactiverest.domain.model.Category.java


package guru.springframework.spring5reactiverest.domain.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Category {

	@Id
	private String id;

	private String description;

}///:~