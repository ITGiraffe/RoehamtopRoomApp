package com.example.roehamptonroomapp;

public class Review {

    private String book_PK, review_desc, review_timestamp;

    public Review() {
    }

    public Review(String book_PK, String review_desc, String review_timestamp) {
        this.book_PK = book_PK;
        this.review_desc = review_desc;
        this.review_timestamp = review_timestamp;
    }

    public String getBook_PK() {
        return book_PK;
    }

    public void setBook_PK(String book_PK) {
        this.book_PK = book_PK;
    }

    public String getReview_desc() {
        return review_desc;
    }

    public void setReview_desc(String review_desc) {
        this.review_desc = review_desc;
    }

    public String getReview_timestamp() {
        return review_timestamp;
    }

    public void setReview_timestamp(String review_timestamp) {
        this.review_timestamp = review_timestamp;
    }
}
