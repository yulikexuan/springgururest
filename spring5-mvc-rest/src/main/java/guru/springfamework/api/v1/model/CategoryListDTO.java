//: guru.springfamework.api.v1.model.CategoryListDTO.java


package guru.springfamework.api.v1.model;


import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class CategoryListDTO {

	private final List<CategoryDTO> categories = new ArrayList<>();

	public CategoryListDTO(@NonNull List<CategoryDTO> categories) {
		this.categories.addAll(categories);
	}

	public final List<CategoryDTO> getCategories() {
		return Collections.unmodifiableList(this.categories);
	}

}///:~