//: guru.springfamework.api.RestResponseEntityExceptionHandler.java


package guru.springfamework.api;


import guru.springfamework.domain.services.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;


/*
 * @ControllerAdvice will apply to all classes that use the @Controller and
 * @RestController annotations
 *
 * To be more specific:
 *   - @ControllerAdvice("guru.springfamework.api")
 *   - @ControllerAdvice(value = "guru.springfamework.api")
 *   - @ControllerAdvice(basePackages = "guru.springfamework.api")
 *   - @ControllerAdvice({"guru.springfamework.api",
 *                        "guru.springfamework.api.v1.controllers"})
 *   When a package is chosen, it will be enabled for classes inside that
 *   package as well as sub-packages
 *   Multiple packages can also be chosen
 *
 *   Another way to specify a package is via the basePackageClasses property,
 *   which will enable @ControllerAdvice to all controllers inside the package
 *   that the class (or interface) lives in
 *   - @ControllerAdvice(basePackageClasses = CategoryController.class)
 *
 *   To apply to specific classes use assignableTypes:
 *   - @ControllerAdvice(assignableTypes = {CategoryController.class,
 *                                          CustomerController.class})
 *
 *   If want to apply it to controllers with certain annotations? For example,
 *   only assist controllers annotated with @RestController (which it covers by
 *   default) but will not include @Controller annotated classes:
 *   - @ControllerAdvice(annotations = RestController.class)
 *
 * If define more than one @ExceptionHandler for the same exception in different
 * ControllerAdvice classes, the application would start — but will use the
 * first handler it finds; This could cause unexpected behavior
 */
@ControllerAdvice

//@RestController

/*
 * The RequestMapping annotation here is used to set the content type that is
 * returned by the ResponseEntity
 * It could be added to the methods themselves when different types needed to
 * be returned
 */
@RequestMapping(produces = "application/json; charset=UTF-8")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /*
     * @ExceptionHandler allows to define a method that handles exceptions
     * If not using @ControllerAdvice, the code for handling these exceptions
     * would be in the controllers themselves
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception exception, WebRequest webRequest) {

        String errMsg = "Resource not found! " + exception.getMessage();
        return new ResponseEntity<>(errMsg, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception, WebRequest webRequest) {

        String details = exception.getConstraintViolations().stream().map(cve -> "'" + cve.getPropertyPath() + "' " + cve.getMessage()).reduce("", (s1, s2) -> s1 + s2);

        ApiError apiError = ApiError.ApiErrorBuilder.getInstance().setTimestamp(new Date()).setMessage("Data field validation failed!").setDetails(details).createApiError();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}///:~