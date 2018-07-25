package com.example.themichalkozak.news3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * Created by themichalkozak on 13/07/2018.
 */

public class NewsApiAdapter extends ArrayAdapter<NewsAPI> {


    public NewsApiAdapter(@NonNull Context context, @NonNull List<NewsAPI> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        NewsAPI theGuardian = getItem(position);

        TextView sectionName = listItemView.findViewById(R.id.section_name);
        sectionName.setText(theGuardian.getmSectionName());


        String webTitile = theGuardian.getmWebTitle();

        String author = theGuardian.getmAuthor();

        if(author!=null){

            TextView authorTv = listItemView.findViewById(R.id.author);
            authorTv.setText(author);
        }

        TextView webTitleTv = listItemView.findViewById(R.id.web_title);
        webTitleTv.setText(webTitile);


        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date postRealisedDate = simpleDateFormat.parse(theGuardian.getmWebPublicationDate());

            Date currentTime = Calendar.getInstance().getTime();
            Log.i("current Time", " " + currentTime);
            Log.i("post Time", " " + postRealisedDate);

            long difference = currentTime.getTime() - postRealisedDate.getTime();
            long timeFormRelease;
            String descriptionTime;
            long seconds = difference / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60 ;
            long days = hours /24;
            long month = days / 30;
            long years = month / 12;

            if(seconds < 60){
                timeFormRelease = seconds;
                descriptionTime = getContext().getResources().getString(R.string.second);
            }else if(minutes < 60 ){
                timeFormRelease = minutes;
                descriptionTime = getContext().getResources().getString(R.string.minute);
            }else if( hours < 24){
                timeFormRelease = hours;
                descriptionTime = getContext().getResources().getString(R.string.hour);
            }else if(days < 31){
                timeFormRelease = days;
                descriptionTime = getContext().getResources().getString(R.string.day);
            }else if(month < 12){
                timeFormRelease = month;
                descriptionTime = getContext().getResources().getString(R.string.month);
            }else{
                timeFormRelease = years;
                descriptionTime = getContext().getResources().getString(R.string.year);
            }

            Log.i("Difference", " "+ seconds+" "+ minutes+" "+hours+ " "+ days+ " "+ month+" "+ years);

            Date differenceTime = new Date(timeFormRelease);
            Log.i("Difference Time", " " + differenceTime);
            TextView webPublicationTime = listItemView.findViewById(R.id.web_publication_time);
            webPublicationTime.setText(timeFormRelease + " " + descriptionTime + " " + getContext().getResources().getString(R.string.ago));

        } catch (ParseException e) {
            Log.e("LOG", "Fatal error date convert", e);
        }

        return listItemView;

    }

}
