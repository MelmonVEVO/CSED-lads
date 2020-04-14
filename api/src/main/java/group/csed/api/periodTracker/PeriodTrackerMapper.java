package group.csed.api.periodTracker;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodTrackerMapper implements ResultSetMapper<PeriodData> {
    @Override
    public PeriodData map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new PeriodData(set.getDate("started"), set.getInt("cycle_length"));
    }
}