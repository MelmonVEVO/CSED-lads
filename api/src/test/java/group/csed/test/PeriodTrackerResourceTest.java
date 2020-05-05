package group.csed.test;

import group.csed.api.account.session.SessionHelper;
import group.csed.api.periodTracker.PeriodData;
import group.csed.api.periodTracker.PeriodTrackerDao;
import group.csed.api.periodTracker.PeriodTrackerResource;
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
public class PeriodTrackerResourceTest {

    private final String TEST_SESSION_ID = "43a1a8ff-b705-4db6-9867-85e6bd10d693";

    private static final PeriodTrackerDao DAO = mock(PeriodTrackerDao.class);
    private static final SessionHelper SESSION_HELPER = mock(SessionHelper.class);

    private static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new PeriodTrackerResource(DAO, SESSION_HELPER))
            .build();

    private PeriodData data = new PeriodData(new Date(), 20);

    @Test
    public void update() {
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(1);
        doNothing().when(DAO).update(1, new Date(), 20);

        final Response response = RESOURCES.target("/period-tracker/update")
                .request()
                .cookie("session", TEST_SESSION_ID)
                .post(Entity.entity(data, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(true);
    }

    @Test
    public void updateNoSession() {
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(-1);
        doNothing().when(DAO).update(1, new Date(), 20);

        final Response response = RESOURCES.target("/period-tracker/update")
                .request()
                .post(Entity.entity(data, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(false);
    }
}