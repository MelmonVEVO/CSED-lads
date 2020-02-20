package group.csed.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;

public class ResponseTemplate {

    private JSONObject response;
    private ObjectWriter objectWriter;

    public ResponseTemplate(boolean success) {
        this.response = new JSONObject();
        this.objectWriter = new ObjectMapper().writer().without(SerializationFeature.INDENT_OUTPUT);
        this.response.put("success", success);
    }

    public ResponseTemplate put(String key, Object value) {
        try {
            response.put(key, new JSONObject(objectWriter.writeValueAsString(value)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String build() {
        return response.toString();
    }
}