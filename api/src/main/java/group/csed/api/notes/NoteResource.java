package group.csed.api.notes;

import group.csed.api.ResponseTemplate;
import group.csed.api.account.session.SessionHelper;
import group.csed.api.notes.search.NoteSearchDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/notes")
@Produces(MediaType.APPLICATION_JSON)
public class NoteResource {

    private final NoteDao noteDao;
    private final NoteSearchDao noteSearchDao;

    private final SessionHelper sessionHelper;

    public NoteResource(NoteDao noteDao, NoteSearchDao noteSearchDao, SessionHelper sessionHelper) {
        this.noteDao = noteDao;
        this.noteSearchDao = noteSearchDao;
        this.sessionHelper = sessionHelper;
    }

    @POST
    @Path("/create")
    public Response create(@CookieParam("session") String sessionID, Note note) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            noteDao.create(accountID, note.getTitle(), note.getContent());
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @POST
    @Path("/update")
    public Response update(@CookieParam("session") String sessionID, Note note) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            noteDao.update(note.getId(), accountID, note.getTitle(), note.getContent());
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @GET
    @Path("/{sessionID}")
    public Response get(@PathParam("sessionID") String sessionID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            return Response.ok(new ResponseTemplate(true).put("notes", noteSearchDao.get(accountID)).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @GET
    @Path("/{sessionID}/{noteID}")
    public Response getNote(@PathParam("sessionID") String sessionID, @PathParam("noteID") int noteID) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            final Note note = noteDao.get(noteID, accountID);
            return Response.ok(new ResponseTemplate(true).put("note", note).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }

    @POST
    @Path("/delete")
    public Response delete(@CookieParam("session") String sessionID, Note note) {
        final int accountID = sessionHelper.getAccountID(sessionID);
        if(accountID != 0) {
            noteDao.delete(note.getId(), accountID);
            return Response.ok(new ResponseTemplate(true).build()).build();
        }
        return Response.ok(new ResponseTemplate(false).build()).build();
    }
}