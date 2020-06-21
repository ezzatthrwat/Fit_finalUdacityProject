package com.example.fit.model;

import androidx.annotation.NonNull;

public class Video {

    private String VideoUrl ;
    private String VideoDes;
    private String VideoImg;
    private String VideoTitle;

    public Video() {
    }

    public Video(String videoUrl, String videoDes, String videoImg, String videotitl) {
        VideoUrl = videoUrl;
        VideoDes = videoDes;
        VideoImg = videoImg;
        VideoTitle = videotitl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public String getVideoDes() {
        return VideoDes;
    }

    public String getVideoImg() {
        return VideoImg;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    @NonNull
    @Override
    public String toString() {
        return "Video{" +
                "VideoUrl='" + VideoUrl + '\'' +
                ", VideoDescription='" + VideoDes + '\'' +
                ", VideoIMG='" + VideoImg + '\'' +
                ", VideoTitle='" + VideoTitle + '\'' +
                '}';
    }
}
