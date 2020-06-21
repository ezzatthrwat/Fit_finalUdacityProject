package com.example.fit.ui.video_details;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.fit.R;
import com.example.fit.model.Video;
import com.example.fit.model.VideoRepository;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

public class VideoDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private Video videoModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.fit.databinding.ActivityVideoDetailsBinding mActivityVideoDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_video_details);

        mActivityVideoDetailsBinding.youTubePlayerView.initialize(getString(R.string.Api_Key), this);
        videoModel = VideoRepository.getInstance().getVideo();
        mActivityVideoDetailsBinding.setVideo(videoModel);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b){
            youTubePlayer.cueVideo(videoModel.getVideoUrl());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

}
