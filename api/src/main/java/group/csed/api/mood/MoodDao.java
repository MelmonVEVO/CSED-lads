package group.csed.api.mood;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(MoodMapper.class)
public interface MoodDao {

    @SqlUpdate("INSERT INTO mood VALUES (:id, :description, now())")
    void insert(@Bind("id") int id, @Bind("description") String description);

    @SqlQuery("SELECT description, recorded_at FROM mood WHERE id=:id")
    List<Mood> getAll(@Bind("id") int id);

    @SqlQuery("SELECT EXISTS (SELECT recorded_at FROM mood WHERE recorded_at=:date)")
    boolean entryExists(@Bind("date") String date);
}