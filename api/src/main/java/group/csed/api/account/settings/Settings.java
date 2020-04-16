package group.csed.api.account.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

    @JsonProperty private boolean pillTracking, periodTracking;

    public Settings(boolean pillTracking, boolean periodTracking) {
        this.pillTracking = pillTracking;
        this.periodTracking = periodTracking;
    }

    public Settings() {}

    public boolean pillTrackingEnabled() {
        return pillTracking;
    }

    public boolean periodTrackingEnabled() {
        return periodTracking;
    }
}