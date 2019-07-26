package task.manager.controller.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

  private JsonUtil() {
  }

  private static final ObjectMapper mapper = new ObjectMapper();

  public static String asJsonString(final Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error("Unable to serialize given object as json", e);
      return null;
    }
  }

  public static <T> T fromJsonString(final String jsonString, TypeReference<T> clazz) {
    try {
      return mapper.readValue(jsonString, clazz);
    } catch (IOException e) {
      log.error("Unable to deserialize given json String in AccountingApiRequest object", e);
      return null;
    }
  }
}