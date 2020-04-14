package group.csed.api.pillTracker;

import group.csed.api.ResponseTemplate;
import group.csed.api.account.session.SessionHelper;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/pill-tracker")
@Produces(MediaType.APPLICATION_JSON)
public class PillTrackerResource {

    private final PillTrackerDao dao;
    private final SessionHelper sessionHelper;

    public PillTrackerResource(PillTrackerDao dao, SessionHelper sessionHelper) {
        this.dao = dao;
        this.sessionHelper = sessionHelper;
    }

    private boolean hasTaken(int accountID) {
        final String mostRecent = dao.getMostRecent(accountID);
        if(mostRecent != null) {
            try {
                final Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mostRecent);
                final long millis = date.getTime();
                return (System.currentTimeMillis() - millis) <= 86400000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @GET
    @Path("/has-taken")
    public Response hasTaken(@CookieParam("session") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            return Response.ok(new ResponseTemplate(true).put("taken", hasTaken(accountID)).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}