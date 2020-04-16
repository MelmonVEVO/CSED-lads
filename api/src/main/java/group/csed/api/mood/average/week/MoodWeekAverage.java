package group.csed.api.mood.average.week;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoodWeekAverage {

    @JsonProperty private int week, month;
    @JsonProperty private float average;

    public MoodWeekAverage(int week, int month, float average) {
        this.week = week;
        this.month = month;
        this.average = average;
    }

    public int getWeek() {
        return week;
    }

    public int getMonth() {
        return month;
    }

    public float getAverage() {
        return average;
    }
}