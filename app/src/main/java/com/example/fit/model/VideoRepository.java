package com.example.fit.model;

public class VideoRepository {
   private static VideoRepository videoRepositoryInstance;
    private Video video ;

    private VideoRepository() {
    }

    public static VideoRepository getInstance(){
        if (videoRepositoryInstance == null){
            videoRepositoryInstance = new VideoRepository();
        }
        return videoRepositoryInstance;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
