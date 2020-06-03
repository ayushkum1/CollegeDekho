package com.example.collegefirebase.CollegeGallery.YouTubeAPI.Model;

public class YoutubeVideoModel {
    private String title;
    private String url;
    private String videoId;
    private String desc;

    public YoutubeVideoModel() {
    }

    public YoutubeVideoModel(String title, String url, String videoId, String desc) {
        this.title = title;
        this.url = url;
        this.videoId = videoId;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "YoutubeVideoModel{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", videoId='" + videoId + '\'' +
                ", desc='" + desc + '\'' +
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
