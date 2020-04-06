package group.csed.api.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PeriodData {

    @JsonProperty private Date started;
    @JsonProperty private int cycleLength;

    public PeriodData(Date started, int cycleLength) {
        this.started = started;
        this.cycleLength = cycleLength;
    }

    public PeriodData() {}

    public Date getStarted() {
        return started;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public void setCycleLength(int cycleLength) {
        this.cycleLength = cycleLength;
    }
}