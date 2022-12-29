package vttp.PAFWS.models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Game {
    private String name;
    private Integer gid;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String image;
    private String timestamp;
    private String url;
    private List<Review> reviews;
    
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsers_rated() {
        return users_rated;
    }
    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }

    /*
     * year: <Year field>,
ranking: <Rank field>,
average: <Average field>,
users_rated: <Users rated field>,
url: <URL field>,
thumbnail: <Thumbnail field>,
timestamp: <result timestamp>
     */
    
    public static Game createGame(Document doc){
        Game game = new Game();
        game.setGid(doc.getInteger("gid"));
        game.setImage(doc.getString("image"));
        game.setName(doc.getString("name"));
        game.setRanking(doc.getInteger("ranking"));
        game.setTimestamp(new Date().toString());
        game.setUrl(doc.getString("url"));
        game.setUsers_rated(doc.getInteger("users_rated"));
        game.setYear(doc.getInteger("year"));
        return game;
    }

    public static Game createGameWithReview(Document doc){
        Game game = new Game();
        game.setGid(doc.getInteger("gid"));
        game.setImage(doc.getString("image"));
        game.setName(doc.getString("name"));
        game.setRanking(doc.getInteger("ranking"));
        game.setTimestamp(new Date().toString());
        game.setUrl(doc.getString("url"));
        game.setUsers_rated(doc.getInteger("users_rated"));
        game.setYear(doc.getInteger("year"));
        List<Document> reviewsDoc = doc.getList("reviews", Document.class);
        List<Review> reviews = new LinkedList<>();
        if(null!=reviewsDoc){
            for(Document review: reviewsDoc){
                reviews.add(Review.createReview(review));
            }
        }
        game.setReviews(reviews);
        return game;
    }

    public static JsonObject toJSON(Game game){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("gid", game.getGid());
        job.add("name", game.getName());
        job.add("image", game.getImage());
        job.add("ranking", game.getRanking());
        job.add("timestamp", game.getTimestamp());
        job.add("url", game.getUrl());
        job.add("users_rated", game.getUsers_rated());
        job.add("year", game.getYear());
        return job.build();
        
    }

    public static JsonObject toJSONWithReview(Game game){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("gid", game.getGid());
        job.add("name", game.getName());
        job.add("image", game.getImage());
        job.add("ranking", game.getRanking());
        job.add("timestamp", game.getTimestamp());
        job.add("url", game.getUrl());
        job.add("users_rated", game.getUsers_rated());
        job.add("year", game.getYear());
        JsonArrayBuilder jab = Json.createArrayBuilder();
        if(game.getReviews().size()>0){
            for(Review r: game.getReviews()){
                jab.add(Review.createJSON(r));
            }
        }
        job.add("reviews", jab.build());
        return job.build();
        
    }
    
}
