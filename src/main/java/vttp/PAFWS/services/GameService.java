package vttp.PAFWS.services;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.PAFWS.models.Game;
import vttp.PAFWS.models.Review;
import vttp.PAFWS.repositories.GameRepository;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepo;
    

    public List<Game> findAllGames(Integer skip, Integer limit){
        List<Document> docs = gameRepo.findAllGames(skip, limit);
        List<Game> res = new LinkedList<>();
        for(Document d: docs){
            res.add(Game.createGame(d));
        }
        return res;
    }

    public List<Game> findAllGamesByRank(Integer skip, Integer limit){
        List<Document> docs = gameRepo.findAllGamesByRank(skip, limit);
        List<Game> res = new LinkedList<>();
        for(Document d: docs){
            res.add(Game.createGame(d));
        }
        return res;
    }

    public Game findGameByGid(Integer gid) {
        Document doc = gameRepo.getGameByGid(gid);
        return Game.createGame(doc);
    }

    public boolean postReview(String user, Integer rating, String comment, Integer gid, String name ){
        Integer check = gameRepo.countGameByGid(gid);
        if(check<1){
            return false;
        }
        gameRepo.postReview(user, rating, comment, gid, name);
        return true;
    }

    public boolean updateReview(String review_id, String comment, Integer rating){
        if(gameRepo.countReviewById(review_id)<1){
            return false;
        }
        gameRepo.updateReview(review_id, comment, rating);
        return true;
    }

    public Review findReview(String review_id){
        Document doc = gameRepo.findReview(review_id);
        Review res = Review.createReview(doc);
        return res;
    }

    public Game findGameReviews(Integer gid){
        Document doc = gameRepo.lookupGameWithReviews(gid);
        Game game = Game.createGameWithReview(doc);
        return game;
    }

}
