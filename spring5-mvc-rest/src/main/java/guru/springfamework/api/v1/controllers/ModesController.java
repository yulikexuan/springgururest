//: guru.springfamework.api.v1.controllers.ModesController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.api.v1.model.ModesDTO;
import guru.springfamework.domain.services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Mappings.API_V1_MODES)
public class ModesController {

    public ModesController() {
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ModesDTO getModes() {
        return new ModesDTO();
    }

}///:~