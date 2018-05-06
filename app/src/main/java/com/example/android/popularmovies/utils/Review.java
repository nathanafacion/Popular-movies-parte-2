package com.example.android.popularmovies.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class Review  implements Parcelable{

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String author;
    private String content;
    private String url;

    public Review (String author, String content, String url){
        this.author = author;
        this.content = content;
        this.url = url;
    }

    protected Review(Parcel in) {
        String[] data = in.createStringArray();
        this.author = data[0];
        this.content = data[1];
        this.url = data[2];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.author,
                this.content,
                this.url,
        });
    }
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}

