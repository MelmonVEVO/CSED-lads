package group.csed.api.tracker;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;

@RegisterMapper(TrackerMapper.class)
public interface TrackerDao {

    @SqlUpdate("INSERT INTO tracker VALUES (:id, :started, :cycleLength)")
    void insert(@Bind("id") int id, @Bind("started") Date started, @Bind("cycleLength") int cycleLength);

    @SqlUpdate("UPDATE tracker SET started=:started, cycle_length=:cycleLength WHERE id=:id")
    void update(@Bind("id") int id, @Bind("started") Date started,  @Bind("cycleLength") int cycleLength);

    @SqlQuery("SELECT started, cycle_length FROM tracker WHERE id=:id")
    PeriodData getData(@Bind("id") int id);

    @SqlQuery("SELECT EXISTS (SELECT id FROM tracker WHERE id=:id)")
    boolean entryExists(@Bind("id") int id);
}