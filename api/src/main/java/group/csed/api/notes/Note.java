package group.csed.api.notes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Note {

    @JsonProperty private int id;
    @JsonProperty private String title, content;
    @JsonProperty private Date created, edited;

    public Note(int id, String title, String content, Date created, Date edited) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.created = created;
        this.edited = edited;
    }

    public Note() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreated() {
        return created;
    }

    public Date getEdited() {
        return edited;
    }
}