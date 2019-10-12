package com.am.popularmoviesstageone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.am.popularmoviesstageone.R;
import com.am.popularmoviesstageone.model.Movie;
import com.am.popularmoviesstageone.model.MovieTrailerEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.am.popularmoviesstageone.util.CONST.BASE_TRAILERS_URL;

public class MoviesTrailersAdapter extends RecyclerView.Adapter<MoviesTrailersAdapter.TrailerHolder> {

    private Context mContext;
    private List<MovieTrailerEntity> mTrailerList;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    public MoviesTrailersAdapter(Context context, OnItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mTrailerList = new ArrayList<>();
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_trailer, parent, false);
        return new TrailerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        MovieTrailerEntity trailer = mTrailerList.get(position);
        holder.bindData(trailer);
    }

    @Override
    public int getItemCount() {
        return mTrailerList == null ? 0 : mTrailerList.size();
    }

    public void add(MovieTrailerEntity item) {
        mTrailerList.add(item);
        notifyItemInserted(mTrailerList.size() - 1);
    }

    public void addAll(List<MovieTrailerEntity> appendedItemList) {
        if (appendedItemList == null || appendedItemList.size() <= 0) {
            return;
        }
        if (this.mTrailerList == null) {
            this.mTrailerList = new ArrayList<>();
        }
        this.mTrailerList.addAll(appendedItemList);
        notifyDataSetChanged();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_trailer_thump)
        ImageView mThumpImageView;
        @BindView(R.id.iv_play_icon)
        ImageView mPlayIconImageView;
        @BindView(R.id.tv_trailer_name)
        TextView mTrailerNameTextView;



        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClicked(mTrailerList.get(getAdapterPosition()));
            }
        };

        public TrailerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(onClickListener);
            mThumpImageView.setOnClickListener(onClickListener);
            mTrailerNameTextView.setOnClickListener(onClickListener);
            mPlayIconImageView.setOnClickListener(onClickListener);
        }


        @SuppressLint("SetTextI18n")
        private void bindData(MovieTrailerEntity trailer) {
            mTrailerNameTextView.setText(trailer.getName());
            Glide.with(mContext).load(BASE_TRAILERS_URL.replace("VIDEO_ID" , trailer.getKey())).into(mThumpImageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(MovieTrailerEntity trailer);
    }

}
