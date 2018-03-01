//: guru.springfamework.domain.services.ICategoryService.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.model.CategoryDTO;

import java.util.List;


public interface ICategoryService {

	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryByName(String name);

}///:~