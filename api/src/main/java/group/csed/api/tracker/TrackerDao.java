package group.csed.api.tracker;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(TrackerMapper.class)
public interface TrackerDao {

    @SqlUpdate("INSERT INTO tracker VALUES (:id, :started, :lasted, :cycleLength)")
    void insert(@Bind("id") int id, @Bind("started") String started, @Bind("lasted") int lasted, @Bind("cycleLength") int cycleLength);

    @SqlUpdate("UPDATE tracker SET started=:started, lasted=:lasted, cycle_length=:cycleLength WHERE id=:id")
    void update(@Bind("id") int id, @Bind("started") String started, @Bind("lasted") int lasted, @Bind("cycleLength") int cycleLength);

    @SqlQuery("SELECT started, lasted, cycle_length FROM tracker WHERE id=:id")
    PeriodData getData(@Bind("id") int id);

    @SqlQuery("SELECT EXISTS (SELECT id FROM tracker WHERE id=:id)")
    boolean entryExists(@Bind("id") int id);
}