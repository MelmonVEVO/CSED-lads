package group.csed.api.mood;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(MoodMapper.class)
public interface MoodDao {

    @SqlUpdate("INSERT INTO mood VALUES (:id, :score, now())")
    void insert(@Bind("id") int id, @Bind("score") int score);

    @SqlQuery("SELECT score, recorded_at FROM mood WHERE id=:id ORDER BY recorded_at LIMIT 15")
    List<Mood> getAll(@Bind("id") int id);

    @SqlQuery("SELECT EXISTS (SELECT recorded_at FROM mood WHERE id=:id AND recorded_at=:date)")
    boolean entryExists(@Bind("id") int id, @Bind("date") String date);
}