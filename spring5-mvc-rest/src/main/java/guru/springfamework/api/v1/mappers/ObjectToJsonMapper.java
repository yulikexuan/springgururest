//: guru.springfamework.api.v1.mappers.ObjectToJsonMapper.java


package guru.springfamework.api.v1.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ObjectToJsonMapper {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> String toJson(T t) {

		try {
			return objectMapper.writeValueAsString(t);
		} catch (JsonProcessingException jpe) {
			log.debug(">>>>>>> Failde to convert " + t.getClass().getName() +
					" to JSON string.");
			throw new RuntimeException(jpe);
		}
	}

}///:~