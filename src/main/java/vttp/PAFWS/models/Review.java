package vttp.PAFWS.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Review {
//     user: <name form field>,
// rating: <rating form field>,
// comment: <comment form field>,
// ID: <game id form field>,
// posted: <date>,
// name: <The board game’s name as per ID>,
// edited: [
// { comment: ..., rating: ..., posted: ... },
// { comment: ..., rating: ..., posted: ... },
// { comment: ..., rating: ..., posted: ... }
// ]
    private String user;
    private Integer rating;
    private String comment;
    private Integer id;
    private String date;
    private String name;
    private List<Edit> edits = new LinkedList<>();
    
    public static Review createReview(Document doc){
        Review review = new Review();
        review.setComment(doc.getString("comment"));
        review.setDate(doc.getDate("posted").toString());
        review.setId(doc.getInteger("id"));
        review.setName(doc.getString("name"));
        review.setRating(doc.getInteger("rating"));
        review.setUser(doc.getString("user"));
        List<Edit> edits = new LinkedList<>();
        if(null!=doc.getList("edited", Document.class)){
            for(Document d : doc.getList("edited", Document.class)){
                edits.add(Edit.createEdit(d));
            }
        }
        review.setEdits(edits);
        return review;
    }

    public static JsonObject createJSON(Review review){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("user", review.getUser());
        job.add("rating", review.getRating());
        job.add("comment", review.getComment());
        job.add("id", review.getId());
        job.add("posted", review.getDate());
        job.add("name", review.getName());
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Edit e: review.getEdits()){
            jab.add(Edit.createJSON(e));
        }
        job.add("edited", jab.build());
        job.add("timestamp", new Date().toString());
        return job.build();
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Edit> getEdits() {
        return edits;
    }
    public void setEdits(List<Edit> edits) {
        this.edits = edits;
    }

}
