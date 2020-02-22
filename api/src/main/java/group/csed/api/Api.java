package group.csed.api;

import group.csed.api.account.AccountDao;
import group.csed.api.account.AccountResource;
import group.csed.api.mood.MoodDao;
import group.csed.api.mood.MoodResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class Api extends Application<ApiConfig> {

    public static void main(String[] args) throws Exception {
        new Api().run(args);
    }

    @Override
    public void run(ApiConfig config, Environment environment) {
        final DBIFactory factory = new DBIFactory();
        DataSourceFactory database = config.getDatabase();
        database.setPassword(System.getenv("DB_PASSWORD"));

        final DBI dbi = factory.build(environment, database, "mysql");
        registerResources(environment, dbi);
    }

    /**
     * Registers API routes
     * @param environment
     * @param dbi
     */
    private void registerResources(Environment environment, DBI dbi) {
        final AccountDao accountDao = dbi.onDemand(AccountDao.class);
        final MoodDao moodDao = dbi.onDemand(MoodDao.class);

        environment.jersey().register(new AccountResource(accountDao));
        environment.jersey().register(new MoodResource(moodDao));
    }
}