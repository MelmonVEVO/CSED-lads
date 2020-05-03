package group.csed.api.mood.average.week;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(MoodWeekAverageMapper.class)
public interface MoodWeekAverageDao {

    @SqlQuery("SELECT WEEK(recorded_at) AS week, MONTH(recorded_at) AS month, AVG(score) as average FROM mood WHERE id=:id GROUP BY WEEK(recorded_at), MONTH(recorded_at) LIMIT 14")
    List<MoodWeekAverage> getAverages(@Bind("id") int id);
}