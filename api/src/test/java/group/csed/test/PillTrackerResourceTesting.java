package group.csed.test;

import group.csed.api.account.session.SessionHelper;
import group.csed.api.pillTracker.PillTrackerDao;
import group.csed.api.pillTracker.PillTrackerResource;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;

import java.sql.Date;

import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class PillTrackerResourceTesting {

    private final String TEST_SESSION_ID = "43a1a8ff-b705-4db6-9867-85e6bd10d693";

    private static final PillTrackerDao DAO = mock(PillTrackerDao.class);
    private static final SessionHelper SESSION_HELPER = mock(SessionHelper.class);

    private static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new PillTrackerResource(DAO, SESSION_HELPER))
            .build();

    @Test
    public void enterTwice() {
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(1);
        when(DAO.getMostRecent(1)).thenReturn(new Date(System.currentTimeMillis()).toString());
        doNothing().when(DAO).track(1);

        final Response response = RESOURCES.target("/pill-tracker/track")
                .request()
                .cookie("session", TEST_SESSION_ID)
                .post(null);

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(false);
    }
}