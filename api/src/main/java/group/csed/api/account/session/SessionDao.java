package group.csed.api.account.session;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SessionDao {

    @SqlUpdate("INSERT INTO account_session VALUES(:id, :account_id, now())")
    void insert(@Bind("id") String id, @Bind("account_id") int accountID);

    @SqlQuery("SELECT account_id FROM account_session WHERE id=:id")
    int getAccountID(@Bind("id") String id);
}