package com.ciaocollect.trendmovies.ui.movie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ciaocollect.trendmovies.R;
import com.ciaocollect.trendmovies.Watch;

import java.util.ArrayList;

public class WatchAdapterList extends RecyclerView.Adapter<WatchAdapterList.WatchViewHolder> {
    private Context context;
    private ArrayList<Watch> listWatch = new ArrayList<>();

    public WatchAdapterList(Context context) {
        this.context = context;
    }

    public void setListWatch(ArrayList<Watch> listWatch) {
        Log.e("irin", "setListWatch");
        this.listWatch = listWatch;
    }

    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_layout, viewGroup, false);
        return new WatchViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder watchViewHolder, int position) {
        Log.e("irin", "onBindViewHolder");
        watchViewHolder.bind(listWatch.get(position));
    }

    @Override
    public int getItemCount() {
        return listWatch.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle;
        TextView tvScore;
        TextView tvSynopsis;

        public WatchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.txt_title);
            tvScore = itemView.findViewById(R.id.txt_score);
            tvSynopsis = itemView.findViewById(R.id.txt_overview);
        }

        public void bind(Watch watch) {
            Log.e("irin", "bind");
            Glide.with(context)
                    .asBitmap()
                    .load(watch.getPhotoList())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                    .into(imgPhoto);
            tvTitle.setText(watch.getTitleList());
            Log.e("irin", "bind::watch.getTitleList() " + watch.getTitleList());
            tvScore.setText(watch.getScoreList());
            Log.e("irin", "bind::watch.getScoreList() " + watch.getScoreList());
            tvSynopsis.setText(watch.getSynopsisList());
            Log.e("irin", "bind::watch.getSynopsisList() " + watch.getSynopsisList());
        }
    }
}
