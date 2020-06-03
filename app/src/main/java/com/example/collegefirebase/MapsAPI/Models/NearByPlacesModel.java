package com.example.collegefirebase.MapsAPI.Models;

public class NearByPlacesModel {
    String name, address, rating, id;

    public NearByPlacesModel() {
    }

    public NearByPlacesModel(String name, String address, String rating, String id) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.id = id;
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

    @Override
    public String toString() {
        return "NearByPlacesModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rating='" + rating + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
