package group.csed.api.notes;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteMapper implements ResultSetMapper<Note> {
    @Override
    public Note map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new Note(
                set.getInt("id"),
                set.getString("title"),
                set.getString("content"),
                set.getDate("created"),
                set.getDate("edited")
        );
    }
}