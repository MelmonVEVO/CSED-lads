package group.csed.api.tracker;

import group.csed.api.ResponseTemplate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/period-tracker")
@Produces(MediaType.APPLICATION_JSON)
public class TrackerResource {

    private final TrackerDao dao;

    public TrackerResource(TrackerDao dao) {
        this.dao = dao;
    }

    @POST
    @Path("/create")
    public Response insertData(PeriodData data) {
        if(dao.entryExists(data.getId())) {
            return Response.ok(new ResponseTemplate(false).build()).build();
        }
        dao.insert(data.getId(), data.getStarted(), data.getLasted(), data.getCycleLength());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }

    @POST
    @Path("/update")
    public Response updateData(PeriodData data) {
        dao.update(data.getId(), data.getStarted(), data.getLasted(), data.getCycleLength());
        return Response.ok(new ResponseTemplate(true).build()).build();
    }

    @GET
    @Path("/{id}")
    public Response getData(@PathParam("id") int id) {
        return Response.ok(dao.getData(id)).build();
    }
}