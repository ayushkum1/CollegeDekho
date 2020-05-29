package com.example.collegefirebase.Model;

public class YoutubeVideoModel {
    private String title;
    private String url;
    private String videoId;

    public YoutubeVideoModel() {
    }

    public YoutubeVideoModel(String title, String url, String videoId) {
        this.title = title;
        this.url = url;
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "YoutubeVideoModel{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
