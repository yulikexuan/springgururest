//: guru.springfamework.api.v1.mapper.ICategoryMapperTest.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.model.Category;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class ICategoryMapperTest {

    private static final String NAME = "Joe";
    private static final Long ID = 1L;

    private ICategoryMapper categoryMapper;

    @Before
    public void setUp() {
        this.categoryMapper = ICategoryMapper.INSTANCE;
    }

    @Test
    public void able_To_Transform_Category_To_CategoryDTO() {

        // Given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        // When
        CategoryDTO dto = this.categoryMapper.toCategoryDTO(category);

        // Then
        assertThat(dto.getId(), is(ID));
        assertThat(dto.getName(), is(NAME));
    }

}///:~