// this is the model class for my database.
package com.example.collegefirebase.Model;

import java.util.List;

public class College {
    private String id, name, address, departments, about, placements, image, rating, latitude, longitude;
    private List<String> imageurls, videourls;


    public College(String id, String name, String address, String departments, String about, String placements, String image, String rating, List<String> imageurls, List<String> videourls) {
        this.name = name;
        this.address = address;
        this.departments = departments;
        this.about = about;
        this.placements = placements;
        this.image = image;
        this.rating = rating;
        this.id = id;
        this.imageurls = imageurls;
        this.videourls = videourls;
    }

    public College() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<String> imageurls) {
        this.imageurls = imageurls;
    }

    public String getName() {
        return name;
    }

    public List<String> getVideourls() {
        return videourls;
    }

    public void setVideourls(List<String> videourls) {
        this.videourls = videourls;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPlacements() {
        return placements;
    }

    public void setPlacements(String placements) {
        this.placements = placements;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
