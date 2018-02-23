//: guru.springfamework.domain.services.ICategoryService.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Category;

import java.util.List;


public interface ICategoryService {

	List<Category> getAllCategories();

	Category getCategoryByName(String name);

}///:~