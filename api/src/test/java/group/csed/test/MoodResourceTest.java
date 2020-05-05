package group.csed.test;

import group.csed.api.account.session.SessionHelper;
import group.csed.api.mood.Mood;
import group.csed.api.mood.MoodDao;
import group.csed.api.mood.MoodResource;
import group.csed.api.mood.average.month.MoodMonthAverageDao;
import group.csed.api.mood.average.week.MoodWeekAverageDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class MoodResourceTest {

    private final String TEST_SESSION_ID = "43a1a8ff-b705-4db6-9867-85e6bd10d693";

    private static final MoodDao MOOD_DAO = mock(MoodDao.class);
    private static final MoodMonthAverageDao MOOD_MONTH_AVERAGE_DAO = mock(MoodMonthAverageDao.class);
    private static final MoodWeekAverageDao MOOD_WEEK_AVERAGE_DAO = mock(MoodWeekAverageDao.class);
    private static final SessionHelper SESSION_HELPER = mock(SessionHelper.class);

    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new MoodResource(MOOD_DAO, MOOD_MONTH_AVERAGE_DAO, MOOD_WEEK_AVERAGE_DAO, SESSION_HELPER))
            .build();

    private Mood mood;

    public MoodResourceTest() {
        mood = new Mood();
        mood.setRecordedAt(new Date());
    }

    @Test
    public void trackMood() {
        mood.setScore(5);
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(1);
        when(MOOD_DAO.entryExists(1, new Date().toString())).thenReturn(false);
        doNothing().when(MOOD_DAO).insert(1, mood.getScore());

        final Response response = RESOURCES.target("/mood/insert")
                .request()
                .cookie("session", TEST_SESSION_ID)
                .post(Entity.entity(mood, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(true);
    }

    @Test
    public void trackMoodScoreBelow() {
        mood.setScore(-1);
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(1);
        when(MOOD_DAO.entryExists(1, new Date().toString())).thenReturn(false);
        doNothing().when(MOOD_DAO).insert(1, mood.getScore());

        final Response response = RESOURCES.target("/mood/insert")
                .request()
                .cookie("session", TEST_SESSION_ID)
                .post(Entity.entity(mood, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(false);
    }

    @Test
    public void trackMoodScoreAbove() {
        mood.setScore(6);
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(1);
        when(MOOD_DAO.entryExists(1, new Date().toString())).thenReturn(false);
        doNothing().when(MOOD_DAO).insert(1, mood.getScore());

        final Response response = RESOURCES.target("/mood/insert")
                .request()
                .cookie("session", TEST_SESSION_ID)
                .post(Entity.entity(mood, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(false);
    }
}