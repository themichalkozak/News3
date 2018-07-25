package com.example.themichalkozak.news3;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NewsAPIActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsAPI>> {

    @Override
    public Loader<List<NewsAPI>> onCreateLoader(int id, Bundle args) {
        return new NewsAsyncTaskLoader(NewsAPIActivity.this, JSON_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsAPI>> loader, List<NewsAPI> data) {

        mAdapter.clear();

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }else{
            ImageView emptyView = findViewById(R.id.empty_list_image_view);
            emptyView.setImageDrawable(getDrawable(R.drawable.no_news));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsAPI>> loader) {
        mAdapter.clear();
    }


    public static final String JSON_STRING = "https://content.guardianapis.com/search?show-tags=contribution&page-size=50&q=Manchester%20United&api-key=16b71131-4db2-4146-bb76-8e337ff98098";
    public static final int NEWS_API_LOADER_ID = 1;
    com.example.themichalkozak.news3.NewsApiAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_api);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork !=null && activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_API_LOADER_ID, null, this);
        }else{
            ImageView emptyView = findViewById(R.id.empty_list_image_view);
            emptyView.setImageDrawable(getDrawable(R.drawable.no_internet));
        }

        ListView listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty_list_image_view));
        mAdapter = new com.example.themichalkozak.news3.NewsApiAdapter(this, new ArrayList<NewsAPI>());
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.example.themichalkozak.news3.NewsAPI currentNewsApi = mAdapter.getItem(position);

                Uri uri = Uri.parse(currentNewsApi.getmUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(webIntent);
            }
        });
    }

}