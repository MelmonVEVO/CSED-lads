package group.csed.api.account.session;

import java.util.UUID;

public class SessionHelper {

    private final SessionDao dao;

    public SessionHelper(SessionDao dao) {
        this.dao = dao;
    }

    public String createSession(int accountID) {
        final String sessionID = UUID.randomUUID().toString();
        dao.insert(sessionID, accountID);
        return sessionID;
    }

    public int getAccountID(String sessionID) {
        return dao.getAccountID(sessionID);
    }
}