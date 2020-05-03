package group.csed.api.account.settings;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(SettingsMapper.class)
public interface SettingsDao {

    @SqlQuery("SELECT pill_tracking, period_tracking FROM account WHERE id=:id")
    Settings getSettings(@Bind("id") int id);

    @SqlUpdate("UPDATE account SET pill_tracking=:pillTracking, period_tracking=:periodTracking WHERE id=:id")
    void update(@Bind("id") int id, @Bind("pillTracking") boolean pillTracking, @Bind("periodTracking") boolean periodTracking);
}