package group.csed.api.mood.average.month;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(MoodMonthAverageMapper.class)
public interface MoodMonthAverageDao {

    @SqlQuery("SELECT YEAR(recorded_at) AS year, MONTH(recorded_at) AS month, AVG(score) AS average FROM mood WHERE id=:id GROUP BY YEAR(recorded_at), MONTH(recorded_at) LIMIT 12")
    List<MoodMonthAverage> getAverages(@Bind("id") int id);
}