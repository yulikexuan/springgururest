//: guru.springfamework.domain.repositories.CategoryRepositoryIT.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.Category;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryIT {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Test
    public void able_To_Get_All_Categories() {

        // When
        List<Category> categories = this.categoryRepository.findAll();

        // Then
        assertThat(categories.size(), greaterThan(0));
    }

    @Test
    public void able_To_Get_Category_By_Name() {

        // Given
        List<Category> categories = this.categoryRepository.findAll();
        String name = categories.get(0).getName();

        // When
        Category category = this.categoryRepository.findByName(name);

        // Then
        assertThat(category.getName(), is(name));
    }

}///:~