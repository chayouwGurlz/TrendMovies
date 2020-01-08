package com.ciaocollect.trendmovies.ui.movie;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ciaocollect.trendmovies.BuildConfig;
import com.ciaocollect.trendmovies.R;
import com.ciaocollect.trendmovies.Watch;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieFragment extends Fragment {
    private String API_KEY = BuildConfig.TMDB_API_KEY;
    private String SIZE_PHOTO = "w185";
    String[] listIdMovie;
    ArrayList<Watch> listWatchMovie = new ArrayList<>();
    private RecyclerView rvWatch;
    private WatchAdapterList watchAdapterList;
    private ProgressBar progressBarList;
    LinearLayoutManager linearLayoutManager;
    DividerItemDecoration dividerItemDecor;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //final TextView textView = root.findViewById(R.id.text_movie);
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvWatch = view.findViewById(R.id.rv_watch_movie);
        progressBarList = view.findViewById(R.id.progressBarListMovie);
        rvWatch.setHasFixedSize(true);
        showRecyclerList();
    }

    public void showRecyclerList(){
        linearLayoutManager = new LinearLayoutManager(getContext());
        dividerItemDecor = new DividerItemDecoration(rvWatch.getContext(), linearLayoutManager.getOrientation());
        rvWatch.addItemDecoration(dividerItemDecor);
        rvWatch.setLayoutManager(linearLayoutManager);

        watchAdapterList = new WatchAdapterList(getContext());
        watchAdapterList.notifyDataSetChanged();
        SetListWatchMovie();
    }

    public void SetListWatchMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Watch> listWatches = new ArrayList<>();
        String listUrl = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language="+"en-US";
        client.get(listUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("irin", "onSuccess, statusCode " + statusCode);
                try{
                    String result = new String(responseBody);
                    Log.e("irin", "result " + result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    listIdMovie = new String[list.length()];
                    for(int i=0; i<list.length(); i++){
                        JSONObject watches = list.getJSONObject(i);
                        Watch watch = new Watch();
                        watch.watchListMovie(watches);
                        listWatchMovie.add(watch);
                        String urlPhoto = "https://image.tmdb.org/t/p/"+SIZE_PHOTO+watch.getPhotoPathList();
                        watch.setPhotoList(urlPhoto);
                        listIdMovie[i] = watch.getId();
                    }
                    if (listWatchMovie == null){
                        Log.e("irin", "listWatchMovie is NULL");
                    }
                    else {
                        Log.e("irin", "listWatchMovie is NOT NULL");
                    }
                    watchAdapterList.setListWatch(listWatchMovie);
                    rvWatch.setAdapter(watchAdapterList);
                } catch (Exception e){
                    listWatchMovie.add(null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listWatchMovie.add(null);
                Log.d("onFailure", error.getMessage());
            }
        });
    }
}