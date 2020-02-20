package group.csed.api.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account {

    @JsonProperty private int id;
    @JsonProperty private String email, firstName, lastName, password, dob, created;

    public Account(int id, String email, String firstName, String lastName, String dob, String created) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
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

    public String getDob() {
        return dob;
    }

    public String getCreated() {
        return created;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}