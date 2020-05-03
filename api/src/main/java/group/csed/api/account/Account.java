package group.csed.api.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @JsonProperty private int id;
    @JsonProperty private String email, firstName, lastName, password, created;
    @JsonProperty private boolean pillTracking, periodTracking;

    public Account(int id, String email, String firstName, String lastName, String created) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.created = created;
    }

    public Account() {}

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getCreated() {
        return created;
    }

    public boolean pillTrackingEnabled() {
        return pillTracking;
    }

    public boolean periodTrackingEnabled() {
        return periodTracking;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPillTracking(boolean pillTracking) {
        this.pillTracking = pillTracking;
    }

    public void setPeriodTracking(boolean periodTracking) {
        this.periodTracking = periodTracking;
    }
}