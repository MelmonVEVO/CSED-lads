package group.csed.api.account;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements ResultSetMapper<Account> {

    @Override
    public Account map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new Account(set.getInt("id"),
                set.getString("email"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("created"));
    }
}
