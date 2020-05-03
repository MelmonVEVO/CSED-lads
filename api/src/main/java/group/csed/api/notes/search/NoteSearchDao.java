package group.csed.api.notes.search;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(NoteSearchMapper.class)
public interface NoteSearchDao {

    @SqlQuery("SELECT id, title, created, edited FROM note WHERE account_id=:id ORDER BY created DESC, edited")
    List<NoteSearch> get(@Bind("id") int id);
}