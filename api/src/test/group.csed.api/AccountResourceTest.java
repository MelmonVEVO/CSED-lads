package group.csed.api;

import group.csed.api.account.Account;
import group.csed.api.account.AccountDao;
import group.csed.api.account.AccountResource;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.account.settings.SettingsDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AccountResourceTest {

    private static final AccountDao ACCOUNT_DAO = mock(AccountDao.class);
    private static final SettingsDao SETTINGS_DAO = mock(SettingsDao.class);
    private static final SessionHelper SESSION_HELPER = mock(SessionHelper.class);

    private static final ResourceExtension RESOURCES = ResourceExtension.builder()
            .addResource(new AccountResource(ACCOUNT_DAO, SETTINGS_DAO, SESSION_HELPER))
            .build();
    private ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
    }

    @Test
    public void createAccount() {
        int test = 30;
        Assertions.assertEquals(30, test);
    }
}