package group.csed.api.pillTracker;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PillTrackerDao {

    @SqlUpdate("INSERT INTO pill_tracker VALUES (:id, now())")
    void track(@Bind("id") int id);

    @SqlQuery("SELECT MAX(taken) AS taken FROM pill_tracker WHERE id=:id")
    String getMostRecent(@Bind("id") int id);
}