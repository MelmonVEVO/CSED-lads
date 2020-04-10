package group.csed.api.mood;

import group.csed.api.DateUtils;
import group.csed.api.ResponseTemplate;
import group.csed.api.account.session.SessionHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mood")
@Produces(MediaType.APPLICATION_JSON)
public class MoodResource {

    private final MoodDao dao;
    private final SessionHelper sessionHelper;

    public MoodResource(MoodDao dao, SessionHelper sessionHelper) {
        this.dao = dao;
        this.sessionHelper = sessionHelper;
    }

    @GET
    @Path("/{id}")
    public Response getAll(@PathParam("id") int id) {
        return Response.ok(dao.getAll(id)).build();
    }

    @GET
    @Path("/exists/{sessionID}")
    public Response entryExists(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final boolean exists = dao.entryExists(accountID, DateUtils.formatCurrDate());
            return Response.ok(new ResponseTemplate(true).put("exists", exists).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @POST
    @Path("/insert")
    public Response insert(@CookieParam("session") String sessionID, Mood mood) {
        final int score = mood.getScore();
        if(score >= 1 && score <= 5) {
            final int accountID = sessionHelper.getAccountID(sessionID);
            if(accountID != 0) {
                if(!dao.entryExists(accountID, DateUtils.formatCurrDate())) {
                    dao.insert(accountID, mood.getScore());
                    return Response.ok(new ResponseTemplate(true).build()).build();
                }
            }
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}