package group.csed.api.account.session;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Session {

    @JsonProperty private String sessionID;
    @JsonProperty private int accountID;

    public Session(String sessionID, int accountID) {
        this.sessionID = sessionID;
        this.accountID = accountID;
    }

    public Session() {}
}