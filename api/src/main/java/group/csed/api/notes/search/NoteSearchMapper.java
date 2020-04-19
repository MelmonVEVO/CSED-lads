package group.csed.api.notes.search;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteSearchMapper implements ResultSetMapper<NoteSearch> {
    @Override
    public NoteSearch map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new NoteSearch(
                set.getInt("id"),
                set.getString("title"),
                set.getDate("created"),
                set.getDate("edited")
        );
    }
}
