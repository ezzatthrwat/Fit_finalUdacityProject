package com.example.fit.ui.video_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.fit.R;
import com.example.fit.databinding.VideosItemListBinding;
import com.example.fit.model.Video;

import java.util.List;

public class VideosListAdapter extends RecyclerView.Adapter<VideosListAdapter.VideosListViewHolder> {

    private final List<Video> videosList;
    private final Context context;

    public interface CardClickListener{
        void OnCardClickListener(Video selectedVideo, TextView mTextView, ImageView mImageView);
    }
    private final CardClickListener mCardClickListener ;

    public VideosListAdapter(List<Video> videosList, Context context, CardClickListener mCardClickListener) {
        this.videosList = videosList;
        this.context = context;
        this.mCardClickListener = mCardClickListener;
    }

    @NonNull
    @Override
    public VideosListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        VideosItemListBinding mVideosItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(context) ,
                R.layout.videos_item_list , parent , false);

        return new VideosListViewHolder(mVideosItemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosListViewHolder holder, int position) {

        holder.videosItemListBinding.setVideoItem(videosList.get(position));

        Transformation<Bitmap> circleCrop = new FitCenter();
        Glide.with(context)
                .load(videosList.get(position).getVideoImg())
                .optionalTransform(WebpDrawable.class, new WebpDrawableTransformation(circleCrop))
                .into(holder.videosItemListBinding.VideoImage);
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    class VideosListViewHolder extends RecyclerView.ViewHolder {

        private final VideosItemListBinding videosItemListBinding;

        VideosListViewHolder(@NonNull VideosItemListBinding itemView) {
            super(itemView.getRoot());
            this.videosItemListBinding = itemView;
            itemView.getRoot().setOnClickListener(v -> mCardClickListener.OnCardClickListener(videosList.get(getAdapterPosition()),
                    videosItemListBinding.TextViewTitle, videosItemListBinding.VideoImage));
        }
    }
}
