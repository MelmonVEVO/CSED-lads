package group.csed.api.mood;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Mood {

    @JsonProperty private int score;
    @JsonProperty private Date recordedAt;

    public Mood(int score, Date recordedAt) {
        this.score = score;
        this.recordedAt = recordedAt;
    }

    public Mood() {}

    public int getScore() {
        return score;
    }

    public Date getRecordedAt() {
        return recordedAt;
    }
}