package group.csed.api.mood;

import group.csed.api.DateUtils;
import group.csed.api.ResponseTemplate;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.mood.average.month.MoodMonthAverageDao;
import group.csed.api.mood.average.week.MoodWeekAverageDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/mood")
@Produces(MediaType.APPLICATION_JSON)
public class MoodResource {

    private final MoodDao moodDao;
    private final MoodMonthAverageDao monthAverageDao;
    private final MoodWeekAverageDao weekAverageDao;

    private final SessionHelper sessionHelper;

    public MoodResource(MoodDao moodDao, MoodMonthAverageDao monthAverageDao, MoodWeekAverageDao weekAverageDao, SessionHelper sessionHelper) {
        this.moodDao = moodDao;
        this.monthAverageDao = monthAverageDao;
        this.weekAverageDao = weekAverageDao;
        this.sessionHelper = sessionHelper;
    }

    @GET
    @Path("/{sessionID}")
    public Response getAll(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final List<Mood> entries = moodDao.getAll(accountID);
            if(entries != null && entries.size() > 0) {
                return Response.ok(new ResponseTemplate(true)
                        .put("current", moodDao.getAll(accountID))
                        .put("monthAverages", monthAverageDao.getAverages(accountID))
                        .put("weekAverages", weekAverageDao.getAverages(accountID))
                        .build())
                        .build();
            }
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @GET
    @Path("/exists/{sessionID}")
    public Response entryExists(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final boolean exists = moodDao.entryExists(accountID, DateUtils.formatCurrDate());
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
                if(!moodDao.entryExists(accountID, DateUtils.formatCurrDate())) {
                    moodDao.insert(accountID, mood.getScore());
                    return Response.ok(new ResponseTemplate(true).build()).build();
                }
            }
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}