package dev.ks.movies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service

public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review creativeReview(String reviewBody,String imdbId){
        Review review=new Review();
        //return review;
        reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(Movies.class)
                .matching(Criteria.where("Imdb").is(imdbId))
                .apply(new Update() .push("reviewIds").value(review))
                .first();

        return review;
    }
}
