package group.csed.api.notes;


import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(NoteMapper.class)
public interface NoteDao {

    @SqlUpdate("INSERT INTO note VALUES (default, :id, :title, :content, now(), null)")
    void create(@Bind("id") int id, @Bind("title") String title, @Bind("content") String content);

    @SqlQuery("SELECT id, title, content, created, edited FROM note WHERE id=:noteID AND account_id=:accountID")
    Note get(@Bind("noteID") int id, @Bind("accountID") int accountID);

    @SqlUpdate("UPDATE note SET title=:title, content=:content, edited=NOW() WHERE id=:id AND account_id=:accountID")
    void update(@Bind("id") int noteID, @Bind("accountID") int accountID, @Bind("title") String title, @Bind("content") String content);

    @SqlUpdate("DELETE FROM note WHERE id=:id AND account_id=:accountID")
    void delete(@Bind("id") int noteID, @Bind("accountID") int accountID);
}