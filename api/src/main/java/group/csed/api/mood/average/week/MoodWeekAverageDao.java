package group.csed.api.mood.average.week;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(MoodWeekAverageMapper.class)
public interface MoodWeekAverageDao {

    @SqlQuery("SELECT WEEK(recorded_at) AS week, MONTH(recorded_at) AS month, AVG(score) as average FROM mood WHERE id=:id GROUP BY WEEK(recorded_at), MONTH(recorded_at) LIMIT 14")
    List<MoodWeekAverage> getAverages(@Bind("id") int id);
}