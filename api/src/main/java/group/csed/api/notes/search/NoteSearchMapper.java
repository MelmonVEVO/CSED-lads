package group.csed.api.notes.search;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteSearchMapper implements RowMapper<NoteSearch> {
    @Override
    public NoteSearch map(ResultSet set, StatementContext context) throws SQLException {
        return new NoteSearch(
                set.getInt("id"),
                set.getString("title"),
                set.getDate("created"),
                set.getDate("edited")
        );
    }
}
