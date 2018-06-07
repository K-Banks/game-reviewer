package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    @Test
    public void newReview_instantiatesCorrectly_true() {
        Review review = setupReview();
        assertTrue(review instanceof Review);
    }

    @Test
    public void newReview_instantiatesWithComment() {
        Review review = setupReview();
        assertEquals("This game rules!", review.getComment());
    }

    @Test
    public void newReview_instantiatesWithRating() {
        Review review = setupReview();
        assertEquals(4, review.getRating());
    }

    @Test
    public void newReview_instantiatesWithGameId() {
        Review review = setupReview();
        assertEquals(1, review.getGameId());
    }

    @Test
    public void setId_setsId() {
        Review review = setupReview();
        review.setId(1);
        assertEquals(1, review.getGameId());
    }

    public Review setupReview(){
        return new Review("This game rules!", 4, 1);
    }
}