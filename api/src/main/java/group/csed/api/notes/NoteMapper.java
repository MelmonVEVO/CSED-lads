package group.csed.api.notes;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteMapper implements RowMapper<Note> {
    @Override
    public Note map(ResultSet set, StatementContext context) throws SQLException {
        return new Note(
                set.getInt("id"),
                set.getString("title"),
                set.getString("content"),
                set.getDate("created"),
                set.getDate("edited")
        );
    }
}