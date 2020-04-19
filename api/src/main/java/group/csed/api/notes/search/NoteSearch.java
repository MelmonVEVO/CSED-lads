package group.csed.api.notes.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class NoteSearch {

    @JsonProperty private int id;
    @JsonProperty private String title;
    @JsonProperty private Date created, edited;

    public NoteSearch(int id, String title, Date created, Date edited) {
        this.id = id;
        this.title = title;
        this.created = created;
        this.edited = edited;
    }

    public NoteSearch() {}

    public String getTitle() {
        return title;
    }

    public Date getCreated() {
        return created;
    }

    public Date getEdited() {
        return edited;
    }
}