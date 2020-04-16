package group.csed.api.mood.average.month;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoodMonthAverage {

    @JsonProperty private int year, month;
    @JsonProperty private float average;

    public MoodMonthAverage(int year, int month, float average) {
        this.year = year;
        this.month = month;
        this.average = average;
    }

    public MoodMonthAverage() {}

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public float getAverage() {
        return average;
    }
}