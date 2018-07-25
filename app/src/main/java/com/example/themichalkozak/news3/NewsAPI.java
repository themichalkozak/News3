package com.example.themichalkozak.news3;

/**
 * Created by themichalkozak on 13/07/2018.
 */

public class NewsAPI {

    private String mSectionName;
    private String mWebTitle;
    private String mWebPublicationDate;
    private String mUrl;
    private String mAuthor;

    public NewsAPI(String mSectionName, String mWebTitle, String mWebPublicationDate, String mUrl, String mAuthor) {
        this.mSectionName = mSectionName;
        this.mWebTitle = mWebTitle;
        this.mWebPublicationDate = mWebPublicationDate;
        this.mUrl = mUrl;
        this.mAuthor = mAuthor;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}
