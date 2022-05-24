package com.example.movieapp;

public class Movie {
    private String title;
    private String date;
    private String imageId;
    private String director;

    public Movie(String title, String date, String imageId, String director) {
        this.title = title;
        this.date = date;
        this.imageId = imageId;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", imageId='" + imageId + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}
