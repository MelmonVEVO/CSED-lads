package group.csed.api.periodTracker;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodTrackerMapper implements RowMapper<PeriodData> {
    @Override
    public PeriodData map(ResultSet set, StatementContext context) throws SQLException {
        return new PeriodData(set.getDate("started"), set.getInt("cycle_length"));
    }
}