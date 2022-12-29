package vttp.PAFWS.repositories;

import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class GameRepository {
    
    public static final String BG_C_GAMES = "games";
    public static final String BG_C_COMMENTS = "comments";
    public static final String BG_C_REVIEWS = "reviews";

    @Autowired
    MongoTemplate mongoTemplate;
    
    //db.games.find({});
    public List<Document> findAllGames(Integer skip, Integer limit){
        Criteria c = Criteria.where("");
        Query q = Query.query(c).skip(skip).limit(limit);
        List<Document> res = mongoTemplate.find(q, Document.class, BG_C_GAMES);
        return res;
    }

    // db.games.find({}).sort({ranking:1}).skip(2).limit(2)
    public List<Document> findAllGamesByRank(Integer skip, Integer limit){
        Criteria c = Criteria.where("");
        Query q = Query.query(c).with(Sort.by(Direction.ASC, "ranking")).skip(skip).limit(limit);
        List<Document> res = mongoTemplate.find(q, Document.class, BG_C_GAMES);
        return res;
    } 

    //db.games.find({}).count();
    public Integer countGames(){
        Query q = Query.query(Criteria.where(""));
        Integer count = (int) mongoTemplate.count(q, Document.class, BG_C_GAMES);
        return count;
    }

    //db.games.find({gid:3});
    public Document getGameByGid(Integer gid){
        Criteria c = Criteria.where("gid").is(gid);
        Query q = Query.query(c);
        return mongoTemplate.find(q, Document.class, BG_C_GAMES).get(0);
    }

    //db.games.find({gid:3}).count();
    public Integer countGameByGid(Integer gid){
        Query q = Query.query(Criteria.where("gid").is(gid));
        Integer count = (int) mongoTemplate.count(q, Document.class, BG_C_GAMES);
        return count;
    }

    //db.reviews.insert({user:"qas",rating:2,comment:"new comment", id:3,posted:new Date, name:"Samurai"})
    public void postReview(String user, Integer rating, String comment, Integer gid, String name){
        Document toInsert = new Document()
                                .append("user", user).append("rating", rating)
                                .append("comment", comment).append("id", gid)
                                .append("posted", new Date()).append("name", name);
        mongoTemplate.insert(toInsert, BG_C_REVIEWS);
    }

    //db.reviews.find({_id:ObjectId("63abe5797a94b28e9996b269")}).count()
    public Integer countReviewById(String id){
        ObjectId oid = new ObjectId(id);
        Query q = Query.query(Criteria.where("_id").is(oid));
        Integer count = (int) mongoTemplate.count(q, Document.class, BG_C_REVIEWS);
        return count;
    }

    /*db.reviews.update({_id:ObjectId("63abe5797a94b28e9996b269")},
        {$push:{edited:{comment:"new comment 4", rating:3, posted:new Date()}}})
    */
    public Integer updateReview(String review_id, String comment, Integer rating){
        ObjectId oid = new ObjectId(review_id);
        Query q = Query.query(Criteria.where("_id").is(oid));
        Document update = new Document().append("comment", comment).append("rating", rating).append("posted", new Date());
        Update updateOps = new Update().push("edited", update);
        UpdateResult result = mongoTemplate.upsert(q, updateOps, Document.class, BG_C_REVIEWS);
        return (int) result.getModifiedCount();
    }

    // db.reviews.find({_id:ObjectId("63abe5797a94b28e9996b269")})
    public Document findReview(String review_id){
        ObjectId oid = new ObjectId(review_id);//TODO need try catch for object id conversions from string
        Query q = Query.query(Criteria.where("_id").is(oid)); //what if review_id incorrect, where less than required characters
        Document res = mongoTemplate.find(q, Document.class, BG_C_REVIEWS).get(0);
        return res;
    }

    //db.games.aggregate([{$match:{gid:3}},{$lookup:{from:"reviews",foreignField:"id",localField:"gid",as:"reviews"}}]);
    public Document lookupGameWithReviews(Integer gid) {
        MatchOperation match = Aggregation.match(Criteria.where("gid").is(gid));
        LookupOperation lookup = Aggregation.lookup(BG_C_REVIEWS, "gid", "id", "reviews");
        Aggregation pipeline = Aggregation.newAggregation(match, lookup);
        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, BG_C_GAMES, Document.class);
        return results.getMappedResults().get(0);
    }

}
