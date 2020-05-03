package group.csed.api.periodTracker;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;

@RegisterRowMapper(PeriodTrackerMapper.class)
public interface PeriodTrackerDao {

    @SqlUpdate("INSERT INTO period_tracker VALUES (:id, :started, :cycleLength)")
    void insert(@Bind("id") int id, @Bind("started") Date started, @Bind("cycleLength") int cycleLength);

    @SqlUpdate("UPDATE period_tracker SET started=:started, cycle_length=:cycleLength WHERE id=:id")
    void update(@Bind("id") int id, @Bind("started") Date started,  @Bind("cycleLength") int cycleLength);

    @SqlQuery("SELECT started, cycle_length FROM period_tracker WHERE id=:id")
    PeriodData getData(@Bind("id") int id);

    @SqlQuery("SELECT EXISTS (SELECT id FROM period_tracker WHERE id=:id)")
    boolean entryExists(@Bind("id") int id);
}