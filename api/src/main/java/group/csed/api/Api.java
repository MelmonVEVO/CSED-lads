package group.csed.api;

import group.csed.api.account.AccountDao;
import group.csed.api.account.AccountResource;
import group.csed.api.account.session.SessionDao;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.account.settings.SettingsDao;
import group.csed.api.mood.MoodDao;
import group.csed.api.mood.MoodResource;
import group.csed.api.mood.average.month.MoodMonthAverageDao;
import group.csed.api.mood.average.week.MoodWeekAverageDao;
import group.csed.api.periodTracker.PeriodTrackerDao;
import group.csed.api.periodTracker.PeriodTrackerResource;
import group.csed.api.pillTracker.PillTrackerDao;
import group.csed.api.pillTracker.PillTrackerResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter("allowedOrigins", "*");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());

        registerResources(environment, dbi);
    }

    /**
     * Registers API routes
     * @param environment
     * @param dbi
     */
    private void registerResources(Environment environment, DBI dbi) {
        final AccountDao accountDao = dbi.onDemand(AccountDao.class);
        final SettingsDao settingsDao = dbi.onDemand(SettingsDao.class);
        final SessionDao sessionDao = dbi.onDemand(SessionDao.class);
        final MoodDao moodDao = dbi.onDemand(MoodDao.class);
        final MoodMonthAverageDao moodMonthAverageDao = dbi.onDemand(MoodMonthAverageDao.class);
        final MoodWeekAverageDao moodWeekAverageDao = dbi.onDemand(MoodWeekAverageDao.class);
        final PeriodTrackerDao periodTrackerDao = dbi.onDemand(PeriodTrackerDao.class);
        final PillTrackerDao pillTrackerDao = dbi.onDemand(PillTrackerDao.class);
        final SessionHelper sessionHelper = new SessionHelper(sessionDao);

        environment.jersey().register(new AccountResource(accountDao, settingsDao, sessionHelper));
        environment.jersey().register(new MoodResource(moodDao, moodMonthAverageDao, moodWeekAverageDao, sessionHelper));
        environment.jersey().register(new PeriodTrackerResource(periodTrackerDao, sessionHelper));
        environment.jersey().register(new PillTrackerResource(pillTrackerDao, sessionHelper));
    }
}