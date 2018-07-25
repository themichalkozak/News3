package com.example.themichalkozak.news3;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by themichalkozak on 13/07/2018.
 */

public class NewsAsyncTaskLoader extends AsyncTaskLoader<List<NewsAPI>> {

    private String mUrl;

    public NewsAsyncTaskLoader(Context context, String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }

    @Override
    public List<NewsAPI> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        List<NewsAPI> newsAPIList = NewsAPIQueryUtils.fetchEarthQuakeData(mUrl);
        return newsAPIList;
    }

}
