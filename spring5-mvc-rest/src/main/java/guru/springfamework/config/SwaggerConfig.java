//: guru.springfamework.config.SwaggerConfig.java


package guru.springfamework.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


/*
 * Enables Springfox Swagger 2
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig { // } extends WebMvcConfigurationSupport { // for non spring-boot app

	@Bean
	public Docket api() {
		/*
		 * Create a Docket bean in a Spring Boot configuration to configure
		 * Swagger 2 for the application
		 *
		 * A Springfox Docket instance provides the primary API configuration
		 * with sensible defaults and convenience methods for configuration
		 *
		 * Docket, Soringfox's primary api configuration mechanism is
		 * initialized for swagger sprcification 2.0
		 *
		 * select() returns an instance of ApiSelectorBuilder to give fine
		 * grained control over the endpoints exposed via swagger
		 *
		 * ApiSelectorBuilder, which provides the apis() and paths() methods to
		 * filter the controllers and methods being documented using String
		 * predicates
		 *
		 * RequestHandlerSelectors.basePackage predicate matches the
		 * guru.springfamework.api base package to filter the API
		 *
		 * apis() allows selection of RequestHandler's using a predicate
		 *
		 * paths() allows selection of Path's using a predicate
		 *
		 * build(), the selectorrequires to be built after configuring the api
		 * and path selectors
		 *
		 * pathMapping(), Adds a servlet path mapping, when the servlet has a
		 * path mapping, this prefixes paths with the provided path mapping
		 */
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("guru.springfamework.api"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(this.metaInfo());
	}

	private ApiInfo metaInfo() {

		Contact contact = new Contact("Yu LI",
				"https://github.com/yulikexuan/springgururest",
				"likexuan1996@yahoo.com");

		return new ApiInfo("RESTful Web Service in Spring",
				"Spring Framework 5: Beginner to Guru",
				"1.0.0", "Terms of Service", contact,
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	}

//    For non spring-boot app
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

}///:~