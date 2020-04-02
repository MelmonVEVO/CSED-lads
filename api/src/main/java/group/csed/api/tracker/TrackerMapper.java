package group.csed.api.tracker;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackerMapper implements ResultSetMapper<PeriodData> {
    @Override
    public PeriodData map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new PeriodData(set.getDate("started"), set.getInt("lasted"), set.getInt("cycle_length"));
    }
}