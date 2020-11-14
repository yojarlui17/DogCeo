package com.example.dogceo.Entity;


public class Breed {
    private String raza;
    private String subraza;
    private String images;

    public Breed(){}
    public Breed(String images){
        this.images=images;
    }
    public Breed(String raza,String images) {
        this.raza=raza; this.images=images;
    }

    public Breed(String raza, String subraza,String images) {
        this.raza = raza;
        this.subraza = subraza;
        this.images=images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSubraza() {
        return subraza;
    }

    public void setSubraza(String subraza) {
        this.subraza = subraza;
    }
}
