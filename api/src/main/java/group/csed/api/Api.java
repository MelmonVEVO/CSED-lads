package group.csed.api;

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
    }
}