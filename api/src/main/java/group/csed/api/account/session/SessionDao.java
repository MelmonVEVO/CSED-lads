package group.csed.api.account.session;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface SessionDao {

    @SqlUpdate("INSERT INTO account_session VALUES(:id, :account_id, now())")
    void insert(@Bind("id") String id, @Bind("account_id") int accountID);

    @SqlQuery("SELECT account_id FROM account_sessions WHERE id=:id")
    int getAccountID(@Bind("id") String id);
}