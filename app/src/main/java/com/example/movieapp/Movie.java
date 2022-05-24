package com.example.movieapp;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Movie implements Parcelable {
    private String title;
    private String date;
    private String imageId;
    private String director;
    private Boolean isReserved;

    public Movie(String title, String date, String imageId, String director, Boolean isReserved) {
        this.title = title;
        this.date = date;
        this.imageId = imageId;
        this.director = director;
        this.isReserved = isReserved;
    }

//    public Movie() {
//        this.title = "";
//        this.date = "";
//        this.imageId = "";
//        this.director = "";
//        this.isReserved = false;
//    }

    protected Movie(Parcel in) {
        title = in.readString();
        date = in.readString();
        imageId = in.readString();
        director = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isReserved = in.readBoolean();
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public Boolean getReserved() {
        return isReserved;
    }

    public void setReserved(Boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(imageId);
        dest.writeString(director);
        dest.writeBoolean(isReserved);

    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", imageId='" + imageId + '\'' +
                ", director='" + director + '\'' +
                ", isReserved=" + isReserved +
                '}';
    }
}
