// this is the model class for my database.
package com.example.collegefirebase.Model;

import java.util.List;

public class College {
    private String id, name, address, departments, about, placements, image, rating, latitude, longitude,videourls;
    private List<String> infra_imageurls, hostel_url_list, extra_url_list, labs_url_list;

    public College(String id, String name, String address, String departments, String about, String placements, String image, String rating, String latitude, String longitude, String videourls, List<String> infra_imageurls, List<String> hostel_url_list, List<String> extra_url_list, List<String> labs_url_list) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.departments = departments;
        this.about = about;
        this.placements = placements;
        this.image = image;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.videourls = videourls;
        this.infra_imageurls = infra_imageurls;
        this.hostel_url_list = hostel_url_list;
        this.extra_url_list = extra_url_list;
        this.labs_url_list = labs_url_list;
    }

    public College() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getInfra_imageurls() {
        return infra_imageurls;
    }

    public void setInfra_imageurls(List<String> infra_imageurls) {
        this.infra_imageurls = infra_imageurls;
    }

    public List<String> getHostel_url_list() {
        return hostel_url_list;
    }

    public void setHostel_url_list(List<String> hostel_url_list) {
        this.hostel_url_list = hostel_url_list;
    }

    public List<String> getExtra_url_list() {
        return extra_url_list;
    }

    public void setExtra_url_list(List<String> extra_url_list) {
        this.extra_url_list = extra_url_list;
    }

    public List<String> getLabs_url_list() {
        return labs_url_list;
    }

    public void setLabs_url_list(List<String> labs_url_list) {
        this.labs_url_list = labs_url_list;
    }

    public String getName() {
        return name;
    }

    public String getVideourls() {
        return videourls;
    }

    public void setVideourls(String videourls) {
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
