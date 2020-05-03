package group.csed.api.account.settings;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsMapper implements RowMapper<Settings> {

    @Override
    public Settings map(ResultSet set, StatementContext context) throws SQLException {
        return new Settings(set.getBoolean("pill_tracking"), set.getBoolean("period_tracking"));
    }
}
