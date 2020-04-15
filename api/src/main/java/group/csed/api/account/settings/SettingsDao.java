package group.csed.api.account.settings;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(SettingsMapper.class)
public interface SettingsDao {

    @SqlQuery("SELECT pill_tracking, period_tracking FROM account WHERE id=:id")
    Settings getSettings(@Bind("id") int id);
}