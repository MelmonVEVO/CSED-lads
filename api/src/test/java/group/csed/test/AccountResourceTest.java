package group.csed.test;

import group.csed.api.account.Account;
import group.csed.api.account.AccountDao;
import group.csed.api.account.AccountResource;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.account.settings.SettingsDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AccountResourceTest {

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
        account.setPassword("asdhjkashdkj");
        account.setPeriodTracking(true);
        account.setPeriodTracking(true);
    }

    @Test
    public void testValidLogin() {
        when(ACCOUNT_DAO.getPassword(account.getEmail())).thenReturn("$2a$10$FiTufMMlhuhMraqk7hISBu1NhpD6XBXYhFkQ4PNqLEX/dcKEoWUjy");
        when(ACCOUNT_DAO.getId(account.getEmail())).thenReturn(account.getId());
        when(SESSION_HELPER.createSession(account.getId())).thenReturn("43a1a8ff-b705-4db6-9867-85e6bd10d693");

        //Response response = resource.login(account);
        //ResponseTemplate responseTemplate = response.readEntity(ResponseTemplate.class);
        final Response response = RESOURCES.target("/accounts/login")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    }
}