package group.csed.api.account.settings;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SettingsMapper.class)
public interface SettingsDao {

    @SqlQuery("SELECT pill_tracking, period_tracking FROM account WHERE id=:id")
    Settings getSettings(@Bind("id") int id);

    @SqlUpdate("UPDATE account SET pill_tracking=:pillTracking, period_tracking=:periodTracking WHERE id=:id")
    void update(@Bind("id") int id, @Bind("pillTracking") boolean pillTracking, @Bind("periodTracking") boolean periodTracking);
}