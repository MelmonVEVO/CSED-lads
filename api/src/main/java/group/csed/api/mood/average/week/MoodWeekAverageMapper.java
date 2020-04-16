package group.csed.api.mood.average.week;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodWeekAverageMapper implements ResultSetMapper<MoodWeekAverage> {
    @Override
    public MoodWeekAverage map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new MoodWeekAverage(
                set.getInt("week"),
                set.getInt("month"),
                set.getFloat("average")
        );
    }
}
