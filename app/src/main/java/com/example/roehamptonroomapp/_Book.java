package com.example.roehamptonroomapp;

public class _Book {

    private String book_title, book_author, book_category, book_edition, book_ISBN, book_URL, book_reserved_user;
    private Boolean book_reserve;

    public _Book() {
    }

    public _Book(String book_title, String book_author, String book_category, String book_edition, String book_ISBN, String book_URL, String book_reserved_user, Boolean book_reserve) {
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_category = book_category;
        this.book_edition = book_edition;
        this.book_ISBN = book_ISBN;
        this.book_URL = book_URL;
        this.book_reserved_user = book_reserved_user;
        this.book_reserve = book_reserve;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_category() {
        return book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }

    public String getBook_edition() {
        return book_edition;
    }

    public void setBook_edition(String book_edition) {
        this.book_edition = book_edition;
    }

    public String getBook_ISBN() {
        return book_ISBN;
    }

    public void setBook_ISBN(String book_ISBN) {
        this.book_ISBN = book_ISBN;
    }

    public String getBook_URL() {
        return book_URL;
    }

    public void setBook_URL(String book_URL) {
        this.book_URL = book_URL;
    }

    public String getBook_reserved_user() {
        return book_reserved_user;
    }

    public void setBook_reserved_user(String book_reserved_user) {
        this.book_reserved_user = book_reserved_user;
    }

    public Boolean getBook_reserve() {
        return book_reserve;
    }

    public void setBook_reserve(Boolean book_reserve) {
        this.book_reserve = book_reserve;
    }
}