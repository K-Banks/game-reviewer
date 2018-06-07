package dao;

import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {
    private Sql2oReviewDao reviewDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void getAll_returnsEmptyListWhenNoReviews_0() {
        assertEquals(0, reviewDao.getAll().size());
    }

    @Test
    public void add_setsId_1() {
        Review review = setupReview();
        reviewDao.add(review);
        assertEquals(1, review.getId());
    }

    @Test
    public void getAll_returnsAllReviews_2() {
        Review review = setupReview();
        Review review2 = setupReview();
        reviewDao.add(review);
        reviewDao.add(review2);
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void findById_returnsCorrectReview() {
        Review review = setupReview();
        reviewDao.add(review);
        Review foundReview = reviewDao.findById(review.getId());
        assertEquals(review.getComment(), foundReview.getComment());
    }

    @Test
    public void update_changesComment() {
        Review review = setupReview();
        reviewDao.add(review);
        reviewDao.update(review.getId(), "This game sucks", 4, 1);
        Review updatedReview = reviewDao.findById(review.getId());
        assertNotEquals(review.getComment(), updatedReview.getComment());
    }

    @Test
    public void deleteById_deletesCorrectReview() {
        Review review = setupReview();
        Review review2 = new Review("This game sucks", 4, 1);
        reviewDao.add(review);
        reviewDao.add(review2);
        reviewDao.deleteById(1);
        assertEquals(1, reviewDao.getAll().size());
        assertEquals(2, reviewDao.getAll().get(0).getId());
    }

    @Test
    public void clearAllReviews_deletesAllReviews() {
        Review review = setupReview();
        Review review2 = new Review("This game sucks", 4, 1);
        reviewDao.add(review);
        reviewDao.add(review2);
        reviewDao.clearAllReviews();
        assertEquals(0, reviewDao.getAll().size());
    }

    @Test
    public void getReviewsByGameId_getAllReviewsByDev() {
        Review review1 = setupReview();
        Review review2 = setupReview();
        Review review3 = new Review("SUCK IT", 2, 2);
        reviewDao.add(review1);
        reviewDao.add(review2);
        reviewDao.add(review3);
        List<Review> reviews = reviewDao.getReviewsByGameId(1);
        assertEquals(2, reviews.size());
    }

    @Test
    public void getRatingsByGameId() {
        Review review1 = setupReview();
        Review review2 = setupReview();
        Review review3 = new Review("SUCK IT", 2, 2);
        reviewDao.add(review1);
        reviewDao.add(review2);
        reviewDao.add(review3);
        List<Integer> ratings = reviewDao.getRatingsByGameId(1);
        System.out.print(ratings);
        assertEquals(2, ratings.size());
    }

    public Review setupReview(){
        return new Review("This game rules!", 4, 1);
    }
}