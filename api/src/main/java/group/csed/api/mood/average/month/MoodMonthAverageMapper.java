package group.csed.api.mood.average.month;

import group.csed.api.mood.average.month.MoodMonthAverage;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodMonthAverageMapper implements ResultSetMapper<MoodMonthAverage> {
    @Override
    public MoodMonthAverage map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new MoodMonthAverage(set.getInt("year"), set.getInt("month"), set.getFloat("average"));
    }
}
