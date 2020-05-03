package group.csed.api.mood.average.month;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(MoodMonthAverageMapper.class)
public interface MoodMonthAverageDao {

    @SqlQuery("SELECT YEAR(recorded_at) AS year, MONTH(recorded_at) AS month, AVG(score) AS average FROM mood WHERE id=:id GROUP BY YEAR(recorded_at), MONTH(recorded_at) LIMIT 12")
    List<MoodMonthAverage> getAverages(@Bind("id") int id);
}