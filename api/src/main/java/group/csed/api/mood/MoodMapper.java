package group.csed.api.mood;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodMapper implements RowMapper<Mood> {
    @Override
    public Mood map(ResultSet set, StatementContext context) throws SQLException {
        return new Mood(set.getInt("score"), set.getDate("recorded_at"));
    }
}
