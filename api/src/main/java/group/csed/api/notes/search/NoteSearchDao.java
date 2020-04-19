package group.csed.api.notes.search;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(NoteSearchMapper.class)
public interface NoteSearchDao {

    @SqlQuery("SELECT id, title, created, edited FROM note WHERE account_id=:id ORDER BY created DESC, edited")
    List<NoteSearch> get(@Bind("id") int id);
}