package vttp.PAFWS.controllers;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.PAFWS.models.Game;
import vttp.PAFWS.models.Review;
import vttp.PAFWS.repositories.GameRepository;
import vttp.PAFWS.services.GameService;

@RestController
@EnableWebMvc
public class GamesRESTController {
    
    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    @GetMapping(path = "/games")
    public ResponseEntity<String> getAllGames(@RequestBody MultiValueMap<String, String> body){
        Integer limit = Integer.parseInt(body.getFirst("limit"));
        Integer skip = Integer.parseInt(body.getFirst("skip"));
        List<Game> games = gameService.findAllGames(skip, limit);
        Integer count = gameRepository.countGames();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Game g : games){
            jab.add(Game.toJSON(g));
        }
        JsonArray jsonArray = jab.build();
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("games", jsonArray);
        job.add("offset", skip);
        job.add("limit", limit);
        job.add("total", count);
        job.add("timestamp", new Date().toString());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(job.build().toString());
    }

    @GetMapping(path = "/games/rank")
    public ResponseEntity<String> getAllGamesByRank(@RequestBody MultiValueMap<String, String> body){
        Integer limit = Integer.parseInt(body.getFirst("limit"));
        Integer skip = Integer.parseInt(body.getFirst("skip"));
        List<Game> games = gameService.findAllGamesByRank(skip, limit);
        Integer count = gameRepository.countGames();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Game g : games){
            jab.add(Game.toJSON(g));
        }
        JsonArray jsonArray = jab.build();
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("games", jsonArray);
        job.add("offset", skip);
        job.add("limit", limit);
        job.add("total", count);
        job.add("timestamp", new Date().toString());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(job.build().toString());
    }

    @GetMapping(path = "/game/{gid}")
    public ResponseEntity<String> findGameByGid(@PathVariable Integer gid){
        Game game = gameService.findGameByGid(gid);
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("gid", game.getGid());
        job.add("name", game.getName());
        job.add("year", game.getYear());
        job.add("ranking", game.getRanking());
        job.add("users_rated", game.getUsers_rated());
        job.add("url", game.getUrl());
        job.add("thumbnail", game.getImage());
        job.add("timestamp", game.getTimestamp());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(job.build().toString());
    }

    @PostMapping(path = "/review")
    public ResponseEntity<String> postReview(@RequestBody MultiValueMap<String, String> form){
        String user = form.getFirst("user");
        String comment = form.getFirst("comment");
        String name = form.getFirst("name");
        System.out.println("HELLOO>>>");
        Integer rating = 0;
        Integer id = 0;
        try {
            rating = Integer.parseInt(form.getFirst("rating"));
            id = Integer.parseInt(form.getFirst("id"));
            System.out.println(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body("invalid rating or id");
        }
        if(gameService.postReview(user, rating, comment, id, name)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("review added");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body("game with given id does not exist");
        }
    }

    @PutMapping(path = "/review/{review_id}") //63abe5797a94b28e9996b269
    public ResponseEntity<String> updateReview(@PathVariable String review_id, @RequestBody MultiValueMap<String, String> form){
        String comment = form.getFirst("comment");
        Integer rating = 0;
        try {
            rating = Integer.parseInt(form.getFirst("rating"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body("invalid rating");
        }
        if(gameService.updateReview(review_id, comment, rating)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("review updated");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body("game with given id does not exist");
        }
    }

    @GetMapping(path = "/review/{review_id}")
    public ResponseEntity<String> findReview(@PathVariable String review_id){
        Review res = gameService.findReview(review_id);
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("user", res.getUser());
        job.add("rating", res.getRating());
        job.add("comment", res.getComment());
        job.add("id", res.getId());
        job.add("posted", res.getDate());
        job.add("name", res.getName());
        job.add("edited", res.getEdits().size()>0 ? true:false);
        job.add("timestamp", new Date().toString());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(job.build().toString());
    }

    @GetMapping(path = "/review/{review_id}/history")
    public ResponseEntity<String> findReviewHistory(@PathVariable String review_id){
        Review res = gameService.findReview(review_id);
        JsonObject result = Review.createJSON(res);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result.toString());
    }

    @GetMapping(path = "/game/{gid}/reviews")
    public ResponseEntity<String> findGameReviews(@PathVariable Integer gid){
        Game game = gameService.findGameReviews(gid);
        System.out.println(game.getReviews().get(0).getName());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Game.toJSONWithReview(game).toString());
    }

    @GetMapping(path = "/game/highest")
    public ResponseEntity<String> highest(){
        List<Document> docs = gameService.getHighest();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Document doc: docs){
            System.out.println(doc);
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("_id", doc.getInteger("_id"));
            job.add("name", doc.getString("name"));
            job.add("rating", doc.getInteger("rating", 0));
            job.add("user", doc.getString("user"));
            job.add("comment", doc.getString("comments"));
            jab.add(job.build());
        }
        JsonObjectBuilder res = Json.createObjectBuilder();
        res.add("rating", "highest");
        res.add("games", jab.build());
        res.add("timestamp", new Date().toString());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(res.build().toString());
    }

    @GetMapping(path = "/game/lowest")
    public ResponseEntity<String> lowest(){
        List<Document> docs = gameService.getLowest();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for(Document doc: docs){
            System.out.println(doc);
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("_id", doc.getInteger("_id"));
            job.add("name", doc.getString("name"));
            job.add("rating", doc.getInteger("rating", 0));
            job.add("user", doc.getString("user"));
            job.add("comment", doc.getString("comments"));
            jab.add(job.build());
        }
        JsonObjectBuilder res = Json.createObjectBuilder();
        res.add("rating", "lowest");
        res.add("games", jab.build());
        res.add("timestamp", new Date().toString());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(res.build().toString());
    }


}
