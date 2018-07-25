package com.example.themichalkozak.news3;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by themichalkozak on 13/07/2018.
 */

public class NewsAPIQueryUtils {

    public static final String LOG_TAG = NewsAPIQueryUtils.class.getSimpleName();

    public NewsAPIQueryUtils() {
    }

    public static List<NewsAPI> fetchEarthQuakeData(String requestUrl) {


        URL url1 = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url1);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<NewsAPI> newsAPIList = extractFeaureFormJson(jsonResponse);

        return newsAPIList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<NewsAPI> extractFeaureFormJson(String newsApiJson) {

        if (TextUtils.isEmpty(newsApiJson)) {
            return null;
        }

        List<NewsAPI> newsAPIArrayList = new ArrayList<>();

        try {
            String author = "";
            JSONObject basicNewsApi = new JSONObject(newsApiJson);
            JSONObject respose = basicNewsApi.getJSONObject("response");
            JSONArray results = respose.getJSONArray("results");
            for(int i=0;i<results.length();i++) {
                JSONObject c = results.getJSONObject(i);
                String sectionName = c.getString("sectionName");
                String webTitle = c.getString( "webTitle");
                String webPublicationDate = c.getString("webPublicationDate");
                String url= c.getString("webUrl");
                JSONArray tags = c.getJSONArray("tags");
                if(tags!=null){
                    JSONObject authorJSON = tags.getJSONObject(0);
                    author = authorJSON.getString("webTitle") + " ";
                    Log.i("Author","istneije");
                }

                newsAPIArrayList.add(new NewsAPI(sectionName,webTitle,webPublicationDate,url,author));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem with parssing Json");
        }
        return newsAPIArrayList;
    }

}
