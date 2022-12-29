package vttp.PAFWS.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Edit {
    private String comment;
    private Integer rating;
    private String posted;

    public static Edit createEdit(Document doc){
        Edit edit = new Edit();
        edit.setComment(doc.getString("comment"));
        edit.setRating(doc.getInteger("rating"));
        edit.setPosted(doc.getDate("posted").toString());
        return edit;
    }

    public static JsonObject createJSON(Edit edit){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("comment", edit.getComment());
        job.add("rating", edit.getRating());
        job.add("posted", edit.getPosted());
        return job.build();
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getPosted() {
        return posted;
    }
    public void setPosted(String posted) {
        this.posted = posted;
    }
}
