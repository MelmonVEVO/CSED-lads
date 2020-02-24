package group.csed.api.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PeriodData {

    @JsonProperty private String started;
    @JsonProperty private int id, lasted, cycleLength;

    public PeriodData(String started, int lasted, int cycleLength) {
        this.started = started;
        this.lasted = lasted;
        this.cycleLength = cycleLength;
    }

    public PeriodData() {}

    public int getId() {
        return id;
    }

    public String getStarted() {
        return started;
    }

    public int getLasted() {
        return lasted;
    }

    public int getCycleLength() {
        return cycleLength;
    }
}