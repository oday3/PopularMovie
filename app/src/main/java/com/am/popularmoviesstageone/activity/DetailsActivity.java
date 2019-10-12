package com.am.popularmoviesstageone.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.am.popularmoviesstageone.R;
import com.am.popularmoviesstageone.adapter.MoviesReviewsAdapter;
import com.am.popularmoviesstageone.adapter.MoviesTrailersAdapter;
import com.am.popularmoviesstageone.databinding.ActivityDetailsBinding;
import com.am.popularmoviesstageone.databinding.ContentDetailsBinding;
import com.am.popularmoviesstageone.model.MovieReviewsEntity;
import com.am.popularmoviesstageone.model.MovieVideosEntity;
import com.am.popularmoviesstageone.model.moviedetails.MovieDetails;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.am.popularmoviesstageone.util.CONST.BASE_BACKGROUND_IMAGE_URL;
import static com.am.popularmoviesstageone.util.CONST.BASE_POSTERS_URL;
import static com.am.popularmoviesstageone.util.CONST.EXTRA_MOVIE_ID;
import static com.am.popularmoviesstageone.util.IntentsUtill.watchYoutubeVideo;


public class DetailsActivity extends BaseActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    private MoviesTrailersAdapter mTrailersAdapter;
    private MoviesReviewsAdapter mReviewsAdapter;

    private ActivityDetailsBinding mLayout;
    private ContentDetailsBinding mContentLayout;
    private MovieDetails movieDetails;
    private Integer movieId;
    private boolean isFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = DataBindingUtil.setContentView(this, R.layout.activity_details);
        setSupportActionBar(mLayout.toolbar);
        movieId = getIntent().getExtras().getInt(EXTRA_MOVIE_ID);
        mContentLayout = DataBindingUtil.setContentView(this,R.layout.content_details);

        getMovieDetails(movieId);

        mContentLayout.trailersRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTrailersAdapter = new MoviesTrailersAdapter(this,
                trailer -> watchYoutubeVideo(DetailsActivity.this, trailer.getKey()));
        mContentLayout.trailersRv.setAdapter(mTrailersAdapter);
        mContentLayout.trailersRv.setNestedScrollingEnabled(false);
        mReviewsAdapter = new MoviesReviewsAdapter(this);

        mContentLayout.reviewsRv.setNestedScrollingEnabled(false);
        mContentLayout.reviewsRv.setLayoutManager(new LinearLayoutManager(this));
        mContentLayout.reviewsRv.setAdapter(mReviewsAdapter);
        mContentLayout.reviewsRv.setNestedScrollingEnabled(false);

        mLayout.fab.setOnClickListener(view -> {
            if (!isFavourite) {
                Snackbar.make(view, "Added to favorite ", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                mLayout.fab.setImageResource(R.drawable.ic_star_fill_24dp);
                isFavourite = true;
            } else {
                Snackbar.make(view, "Removed from favorite ", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                mLayout.fab.setImageResource(R.drawable.ic_star_empty_24dp);
                isFavourite = false;

            }

        });

    }


    private void getMovieDetails(int movieId) {
        mApiService.getMovieDetails(movieId + "").enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                movieDetails = response.body();

                mContentLayout.movieTitleTv.setText(movieDetails.getOriginalTitle());
                mContentLayout.durationTv.setText("Duration - " + movieDetails.getRuntime() + " min");
                mContentLayout.ratingTv.setText("Rating - " + movieDetails.getVoteAverage());
                mContentLayout.overviewTv.setText(movieDetails.getOverview());
                Glide.with(DetailsActivity.this).load(BASE_POSTERS_URL + movieDetails.getPosterPath()).into(mContentLayout.imageView);
                Glide.with(DetailsActivity.this).load(BASE_BACKGROUND_IMAGE_URL + movieDetails.getBackdropPath()).into(mLayout.movieBackdropIv);
                mContentLayout.relaseDateTv.setText(movieDetails.getReleaseDate() + " (" + movieDetails.getStatus() + ")");
                getMovieVideos(movieId);
                getMovieReviews(movieId);
                mLayout.toolbar.setTitle(movieDetails.getTitle());
                setSupportActionBar(mLayout.toolbar);

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(DetailsActivity.this, "Error Loading Movie Details", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getMovieVideos(int movieId) {
        mApiService.getMovieVideos(movieId + "").enqueue(new Callback<MovieVideosEntity>() {
            @Override
            public void onResponse(Call<MovieVideosEntity> call, Response<MovieVideosEntity> response) {
                final MovieVideosEntity movieVideosEntity = response.body();
                mTrailersAdapter.addAll(movieVideosEntity.getTrailers());
            }

            @Override
            public void onFailure(Call<MovieVideosEntity> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(DetailsActivity.this, "Error Loading Trailers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMovieReviews(int movieId) {
        mApiService.getMovieReviews(movieId + "").enqueue(new Callback<MovieReviewsEntity>() {
            @Override
            public void onResponse(Call<MovieReviewsEntity> call, Response<MovieReviewsEntity> response) {
                final MovieReviewsEntity movieReviewsEntity = response.body();
                mReviewsAdapter.addAll(movieReviewsEntity.getReviewList());

            }

            @Override
            public void onFailure(Call<MovieReviewsEntity> call, Throwable t) {
                Log.e(TAG, t.toString());
                Toast.makeText(DetailsActivity.this, "Error Loading Reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
