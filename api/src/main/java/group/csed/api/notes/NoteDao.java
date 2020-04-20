package group.csed.api.notes;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(NoteMapper.class)
public interface NoteDao {

    @SqlUpdate("INSERT INTO note VALUES (default, :id, :title, :content, now(), null)")
    void create(@Bind("id") int id, @Bind("title") String title, @Bind("content") String content);

    @SqlQuery("SELECT id, title, content, created, edited FROM note WHERE id=:noteID AND account_id=:accountID")
    Note get(@Bind("noteID") int id, @Bind("accountID") int accountID);

    @SqlUpdate("UPDATE note SET title=:title, content=:content, edited=NOW() WHERE id=:id AND account_id=:accountID")
    void update(@Bind("id") int noteID, @Bind("accountID") int accountID, @Bind("title") String title, @Bind("content") String content);
}