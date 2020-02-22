package group.csed.api.mood;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Mood {

    @JsonProperty private int id;
    @JsonProperty private String description, recordedAt;

    public Mood(String description, String recordedAt) {
        this.description = description;
        this.recordedAt = recordedAt;
    }

    public Mood() {}

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getRecordedAt() {
        return recordedAt;
    }
}