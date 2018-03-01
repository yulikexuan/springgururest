//: guru.springfamework.api.v1.mapper.CategoryMapper.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ICategoryMapper {

	ICategoryMapper INSTANCE = Mappers.getMapper(ICategoryMapper.class);

	default CategoryDTO toCategoryDTO(Category category) {

		if (category == null) {
			return null;
		}

		return CategoryDTO.CategoryDTOBuilder.getInstance()
				.setId(category.getId())
				.setName(category.getName())
				.createCategoryDTO();
	}

}///:~