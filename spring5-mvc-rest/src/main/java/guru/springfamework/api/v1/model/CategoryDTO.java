//: guru.springfamework.api.v1.model.CategoryDTO.java


package guru.springfamework.api.v1.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public final class CategoryDTO {

    // DTO class must have non-final fields because of the non-private
    // constructor
    private Long id;
    private String name;

    // DTO class must have a non-private default constructor
    CategoryDTO() {
    }

    private CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static class CategoryDTOBuilder {

        private Long id;
        private String name;

        private CategoryDTOBuilder() {
        }

        public static CategoryDTOBuilder getInstance() {
            return new CategoryDTOBuilder();
        }

        public CategoryDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CategoryDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CategoryDTO createCategoryDTO() {
            return new CategoryDTO(id, name);
        }
    }

}///:~