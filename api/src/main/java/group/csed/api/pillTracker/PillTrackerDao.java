package group.csed.api.pillTracker;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface PillTrackerDao {

    @SqlUpdate("INSERT INTO pill_tracker VALUES (:id, now())")
    void track(@Bind("id") int id);

    @SqlQuery("SELECT MAX(taken) AS taken FROM pill_tracker WHERE id=:id")
    String getMostRecent(@Bind("id") int id);
}