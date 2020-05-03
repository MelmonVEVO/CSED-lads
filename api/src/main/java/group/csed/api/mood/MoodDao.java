package group.csed.api.mood;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(MoodMapper.class)
public interface MoodDao {

    @SqlUpdate("INSERT INTO mood VALUES (:id, :score, now())")
    void insert(@Bind("id") int id, @Bind("score") int score);

    @SqlQuery("SELECT score, recorded_at FROM mood WHERE id=:id ORDER BY recorded_at DESC LIMIT 14")
    List<Mood> getAll(@Bind("id") int id);

    @SqlQuery("SELECT EXISTS (SELECT recorded_at FROM mood WHERE id=:id AND recorded_at=:date)")
    boolean entryExists(@Bind("id") int id, @Bind("date") String date);
}