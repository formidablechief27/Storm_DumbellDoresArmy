package com.example.storm.Controller;

public class VideoMetadata {

    public String name;

    public VideoMetadata() {
        // Default constructor required by Firebase
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}