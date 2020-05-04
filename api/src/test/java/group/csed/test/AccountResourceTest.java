package group.csed.test;

import group.csed.api.account.Account;
import group.csed.api.account.AccountDao;
import group.csed.api.account.AccountResource;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.account.settings.Settings;
import group.csed.api.account.settings.SettingsDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AccountResourceTest {

    private final String TEST_SESSION_ID = "43a1a8ff-b705-4db6-9867-85e6bd10d693";

    private static final AccountDao ACCOUNT_DAO = mock(AccountDao.class);
    private static final SettingsDao SETTINGS_DAO = mock(SettingsDao.class);
    private static final SessionHelper SESSION_HELPER = mock(SessionHelper.class);

    public static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new AccountResource(ACCOUNT_DAO, SETTINGS_DAO, SESSION_HELPER))
            .build();

    private Account account;

    public AccountResourceTest() {
        account = new Account();
        account.setId(1);
        account.setEmail("test@test.com");
        account.setFirstName("test");
        account.setLastName("test");
        account.setPeriodTracking(true);
        account.setPeriodTracking(true);
    }

    @Test
    public void validLogin() {
        account.setPassword("test");
        final String sessionID = "43a1a8ff-b705-4db6-9867-85e6bd10d693";
        when(ACCOUNT_DAO.getPassword(account.getEmail())).thenReturn("$2a$10$FiTufMMlhuhMraqk7hISBu1NhpD6XBXYhFkQ4PNqLEX/dcKEoWUjy");
        when(ACCOUNT_DAO.getId(account.getEmail())).thenReturn(account.getId());
        when(SESSION_HELPER.createSession(account.getId())).thenReturn(sessionID);

        final Response response = RESOURCES.target("/accounts/login")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(true);
        Assertions.assertThat(json.get("sessionID")).isEqualTo(sessionID);
    }

    @Test
    public void invalidLogin() {
        account.setPassword("1234");
        when(ACCOUNT_DAO.getPassword(account.getEmail())).thenReturn("$2a$10$FiTufMMlhuhMraqk7hISBu1NhpD6XBXYhFkQ4PNqLEX/dcKEoWUjy");
        when(ACCOUNT_DAO.getId(account.getEmail())).thenReturn(account.getId());
        when(SESSION_HELPER.createSession(account.getId())).thenReturn(TEST_SESSION_ID);

        final Response response = RESOURCES.target("/accounts/login")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(false);
    }

    @Test
    public void createValidAccount() {
        doNothing().when(ACCOUNT_DAO).create(
                account.getEmail(),
                account.getFirstName(),
                account.getLastName(),
                account.getPassword(),
                account.pillTrackingEnabled(),
                account.periodTrackingEnabled()
        );
        when(ACCOUNT_DAO.accountExists(account.getEmail())).thenReturn(false);

        final Response response = RESOURCES.target("/accounts/create")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(true);
    }

    @Test
    public void createAccountThatAlreadyExists() {
        doNothing().when(ACCOUNT_DAO).create(
                account.getEmail(),
                account.getFirstName(),
                account.getLastName(),
                account.getPassword(),
                account.pillTrackingEnabled(),
                account.periodTrackingEnabled()
        );
        when(ACCOUNT_DAO.accountExists(account.getEmail())).thenReturn(true);

        final Response response = RESOURCES.target("/accounts/create")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(false);
    }

    @Test
    public void getSettings() throws Exception {
        Settings settings = new Settings(true, true);
        when(SESSION_HELPER.getAccountID(TEST_SESSION_ID)).thenReturn(account.getId());
        when(SETTINGS_DAO.getSettings(account.getId())).thenReturn(settings);

        final Response response = RESOURCES.target("/accounts/settings/" + TEST_SESSION_ID)
                .request()
                .get();

        JSONObject json = new JSONObject(response.readEntity(String.class));
        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        Assertions.assertThat(json.get("success")).isEqualTo(true);
        Assertions.assertThat(json.get("settings").toString()).isEqualTo(RESOURCES.getObjectMapper().writeValueAsString(settings));
    }
}