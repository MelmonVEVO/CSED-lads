package group.csed.api.account.settings;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsMapper implements ResultSetMapper<Settings> {

    @Override
    public Settings map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new Settings(set.getBoolean("pill_tracking"), set.getBoolean("period_tracking"));
    }
}
