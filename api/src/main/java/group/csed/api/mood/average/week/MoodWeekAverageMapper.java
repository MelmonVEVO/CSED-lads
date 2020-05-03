package group.csed.api.mood.average.week;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodWeekAverageMapper implements RowMapper<MoodWeekAverage> {
    @Override
    public MoodWeekAverage map(ResultSet set, StatementContext context) throws SQLException {
        return new MoodWeekAverage(
                set.getInt("week"),
                set.getInt("month"),
                set.getFloat("average")
        );
    }
}
