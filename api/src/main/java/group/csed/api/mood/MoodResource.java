package group.csed.api.mood;

import group.csed.api.ResponseTemplate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/mood")
@Produces(MediaType.APPLICATION_JSON)
public class MoodResource {

    private final MoodDao dao;

    public MoodResource(MoodDao dao) {
        this.dao = dao;
    }

    @GET
    @Path("/{id}")
    public Response getAll(@PathParam("id") int id) {
        return Response.ok(dao.getAll(id)).build();
    }

    @POST
    public Response insert(Mood mood) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String currentDate = dateFormat.format(new Date());
        // Allow mood to be tracked once per-day only
        if(dao.entryExists(currentDate)) {
            return Response.ok(new ResponseTemplate(false).build()).build();
        }
        dao.insert(mood.getId(), mood.getDescription());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }
}