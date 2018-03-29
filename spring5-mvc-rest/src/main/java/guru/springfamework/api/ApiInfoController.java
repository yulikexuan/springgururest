//: guru.springfamework.api.ApiInfoController.java


package guru.springfamework.api;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({"/", "/index", "/home"})
public class ApiInfoController {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiInfo getApiInfo() {
		return ApiInfo.builder()
				.setName("Spring 5 RESTful Guru")
				.setOwner("Yu LI")
				.createApiInfo();
	}

}///:~