package group.csed.api.account;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AccountMapper.class)
public interface AccountDao {

    @SqlUpdate("INSERT INTO account VALUES (default, :email,:firstName, :lastName, :password, now(), :pillTracking, :periodTracking)")
    void create(
            @Bind("email") String email,
            @Bind("firstName") String firstName,
            @Bind("lastName") String lastName,
            @Bind("password") String password,
            @Bind("pillTracking") boolean pillTracking,
            @Bind("periodTracking") boolean periodTracking
    );

    @SqlQuery("SELECT EXISTS(SELECT email FROM account WHERE email=:email)")
    boolean accountExists(@Bind("email") String email);

    @SqlQuery("SELECT id, email, first_name, last_name, dob, created FROM account WHERE id=:id")
    Account getData(@Bind("id") int id);

    @SqlQuery("SELECT password FROM account WHERE email=:email")
    String getPassword(@Bind("email") String email);

    @SqlQuery("SELECT id FROM account WHERE email=:email")
    int getId(@Bind("email") String email);
}