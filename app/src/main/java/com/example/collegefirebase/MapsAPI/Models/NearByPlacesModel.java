package com.example.collegefirebase.MapsAPI.Models;

public class NearByPlacesModel {
    String name, address, rating, id, lat, lng, photourl;

    public NearByPlacesModel() {
    }

    public NearByPlacesModel(String name, String address, String rating, String id, String lat, String lng) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    @Override
    public String toString() {
        return "NearByPlacesModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rating='" + rating + '\'' +
                ", id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", photourl='" + photourl + '\'' +
                '}';
    }
}
