package com.example.fit.ui.video_list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fit.R;
import com.example.fit.databinding.FragmentVideosListBindingImpl;
import com.example.fit.model.Video;
import com.example.fit.model.VideoRepository;
import com.example.fit.ui.video_details.VideoDetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVideosList extends Fragment implements VideosListAdapter.CardClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentVideosListBindingImpl mFragmentVideosListBinding;

    private FirebaseDatabase database;
    private VideosListAdapter videosListAdapter ;
    private final List<Video> videosList = new ArrayList<>();

    public FragmentVideosList() {
        // Required empty public constructor
    }

    private static final String TAG = "FragmentVideosList";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentVideosListBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_videos_list, container, false);
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this);

        initRecyclerView();
        initFirebaseDatabase();
        getInitVideoData();

        return mFragmentVideosListBinding.getRoot() ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(requireContext()).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void initFirebaseDatabase(){ database = FirebaseDatabase.getInstance(); }

    private void getInitVideoData() {
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
       String Gender = sharedPreferences.getString(getString(R.string.GENDER),"");

        if (Gender.equals(getString(R.string.MALE))){
            getVideoData(getString(R.string.MaleWorkOutVideosPath));
        } else if (Gender.equals(getString(R.string.FEMALE))){
            getVideoData(getString(R.string.FemaleWorkoutVideosPath));
        } else {
            getVideoData(getString(R.string.MaleWorkOutVideosPath));
        }
    }

    private void initRecyclerView(){
        mFragmentVideosListBinding.VideosListRecyclerView.setHasFixedSize(true);
        mFragmentVideosListBinding.VideosListRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void OnCardClickListener(Video selectedVideo, TextView mTextView, ImageView mImageView) {
        VideoRepository.getInstance().setVideo(selectedVideo);
        Pair<View, String> p1 = Pair.create(mTextView, "VideoTitle");
        Pair<View, String> p2 = Pair.create(mImageView, "VideoImg");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), p1, p2);
        Intent intent = new Intent(requireActivity(), VideoDetailsActivity.class);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(getString(R.string.GENDER))){
            String Gender = sharedPreferences.getString(getString(R.string.GENDER), "");
            if (Gender.equals(getString(R.string.MALE))){
                getVideoData(getString(R.string.MaleWorkOutVideosPath));
            } else {
                getVideoData(getString(R.string.FemaleWorkoutVideosPath));
            }
        }
    }

    private void getVideoData(String Path){
        DatabaseReference databaseReference = database.getReference(Path);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videosList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    videosList.add(postSnapshot.getValue(Video.class));
                }
                if (videosListAdapter == null) {
                    videosListAdapter = new VideosListAdapter(videosList, requireContext(), FragmentVideosList.this);
                    mFragmentVideosListBinding.VideosListRecyclerView.setAdapter(videosListAdapter);
                } else {
                    videosListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: "+databaseError );
            }
        });
    }
}
