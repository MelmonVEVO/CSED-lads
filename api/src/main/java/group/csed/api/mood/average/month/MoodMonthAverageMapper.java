package group.csed.api.mood.average.month;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodMonthAverageMapper implements RowMapper<MoodMonthAverage> {
    @Override
    public MoodMonthAverage map(ResultSet set, StatementContext context) throws SQLException {
        return new MoodMonthAverage(set.getInt("year"), set.getInt("month"), set.getFloat("average"));
    }
}
