package group.csed.api.account;

import group.csed.api.ResponseTemplate;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.account.settings.Settings;
import group.csed.api.account.settings.SettingsDao;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountDao accountDao;
    private final SettingsDao settingsDao;
    private final SessionHelper sessionHelper;

    public AccountResource(AccountDao accountDao, SettingsDao settingsDao, SessionHelper sessionHelper) {
        this.accountDao = accountDao;
        this.settingsDao = settingsDao;
        this.sessionHelper = sessionHelper;
    }

    @POST
    @Path("/create")
    public Response createAccount(Account account) {
        if(accountDao.accountExists(account.getEmail())) {
            return Response.ok(new ResponseTemplate(false).build()).build();
        }
        final String encryptedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        accountDao.create(account.getEmail(),
                account.getFirstName(),
                account.getLastName(),
                encryptedPassword,
                account.pillTrackingEnabled(),
                account.periodTrackingEnabled());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAccountData(@PathParam("id") int id) {
        return Response.ok(accountDao.getData(id)).build();
    }

    @POST
    @Path("/login")
    public Response login(Account account) {
        final String encryptedPassword = accountDao.getPassword(account.getEmail());
        if(encryptedPassword != null) {
            // Encrypted password and provided password are the same
            if(BCrypt.checkpw(account.getPassword(), encryptedPassword)) {
                final int accountId = accountDao.getId(account.getEmail());
                final String sessionID = sessionHelper.createSession(accountId);
                if(sessionID != null) {
                    return Response.ok(new ResponseTemplate(true).put("sessionID", sessionID).build()).build();
                }
            }
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @GET
    @Path("/settings/{sessionID}")
    public Response getSettings(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final Settings settings = settingsDao.getSettings(accountID);
            if(sessionID != null) {
                return Response.ok(new ResponseTemplate(true).put("settings", settings).build()).build();
            }
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @POST
    @Path("/settings/update")
    public Response update(@CookieParam("session") String sessionID, Settings settings) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            settingsDao.update(accountID, settings.pillTrackingEnabled(), settings.periodTrackingEnabled());
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}