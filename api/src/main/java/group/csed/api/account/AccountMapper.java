package group.csed.api.account;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account map(ResultSet set, StatementContext context) throws SQLException {
        return new Account(set.getInt("id"),
                set.getString("email"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("created"));
    }
}
