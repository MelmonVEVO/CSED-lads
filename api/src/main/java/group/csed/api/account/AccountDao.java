package group.csed.api.account;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterRowMapper(AccountMapper.class)
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