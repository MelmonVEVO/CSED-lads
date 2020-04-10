package group.csed.api.mood;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodMapper implements ResultSetMapper<Mood> {
    @Override
    public Mood map(int i, ResultSet set, StatementContext context) throws SQLException {
        return new Mood(set.getInt("score"), set.getDate("recorded_at"));
    }
}
