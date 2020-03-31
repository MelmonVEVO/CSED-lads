package group.csed.frontend.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PeriodData {

    @SerializedName("started") @Expose @JsonProperty private Date started;
    @SerializedName("lasted") @Expose @JsonProperty private int lasted;
    @SerializedName("cycleLength") @Expose @JsonProperty private int cycleLength;

    public PeriodData(Date started, int lasted, int cycleLength) {
        this.started = started;
        this.lasted = lasted;
        this.cycleLength = cycleLength;
    }

    public PeriodData() {}

    public int getLasted() {
        return lasted;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public Date getStarted() {
        return started;
    }
}