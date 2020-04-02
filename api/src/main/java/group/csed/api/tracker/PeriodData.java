package group.csed.api.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PeriodData {

    @JsonProperty private Date started;
    @JsonProperty private int id, lasted, cycleLength;

    public PeriodData(Date started, int lasted, int cycleLength) {
        this.started = started;
        this.lasted = lasted;
        this.cycleLength = cycleLength;
    }

    public PeriodData() {}

    public int getId() {
        return id;
    }

    public Date getStarted() {
        return started;
    }

    public int getLasted() {
        return lasted;
    }

    public int getCycleLength() {
        return cycleLength;
    }
}