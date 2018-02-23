//: guru.springfamework.api.v1.model.CategoryListDTO.java


package guru.springfamework.api.v1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDTO {

	List<CategoryDTO> categories;

}///:~