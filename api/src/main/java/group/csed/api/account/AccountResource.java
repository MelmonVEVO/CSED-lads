package group.csed.api.account;

import group.csed.api.ResponseTemplate;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountDao dao;

    public AccountResource(AccountDao dao) {
        this.dao = dao;
    }

    @POST
    @Path("/create")
    public Response createAccount(Account account) {
        if(dao.accountExists(account.getEmail())) {
            return Response.ok(new ResponseTemplate(false).build()).build();
        }
        final String encryptedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        dao.create(account.getEmail(), account.getFirstName(), account.getLastName(), encryptedPassword, account.getDob());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAccountData(@PathParam("id") int id) {
        return Response.ok(dao.getData(id)).build();
    }

    @POST
    @Path("/login")
    public Response login(Account account) {
        final String encryptedPassword = dao.getPassword(account.getEmail());
        if(encryptedPassword != null) {
            // Encrypted password and provided password are the same
            if(BCrypt.checkpw(account.getPassword(), encryptedPassword)) {
                return Response.ok(new ResponseTemplate(true).build()).build();
            }
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}