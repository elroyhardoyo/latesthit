package com.example.aplikasimobile.latesthit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by elroy on 2/23/16.
 */
public class SongItemAdapter extends BaseAdapter {

    //1. inisiasi global variable
    //2. kontainer untuk Context
    Context mContext;
    //3. kontainer untuk inflater
    LayoutInflater mInflater;
    //4.kontainer untuk jsonArray
    JSONArray mJsonArray;

    JSONObject mJsonObject;

    private int lastPosition = -1;

    //5. buat konstruktor
    public SongItemAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int counter, View convertView, ViewGroup parent) {

        SongItem song;

        // 8. kalau belum ada
        //inflate baru
        JSONObject jsonObject = (JSONObject) getItem(counter);

        if(mJsonObject.optString("layout").equalsIgnoreCase("instagram") ){
            convertView = mInflater.inflate(R.layout.layout_instagram, null);
        }
        else{
            convertView = mInflater.inflate(R.layout.songitem, null);
        }



        //9. buat clubholder baru dengan inisiasi semua elemen view nya
        song = new SongItem();
        song.pic = (ImageView) convertView.findViewById(R.id.pic);
        song.singer = (TextView) convertView.findViewById(R.id.singer);
        song.song = (TextView) convertView.findViewById(R.id.song);



        song.singer.setText(jsonObject.optString("singer"));
        song.song.setText(jsonObject.optString("song"));

        Picasso.with(mContext).load(jsonObject.optString("pic")).into(song.pic);


        Animation animation = AnimationUtils.loadAnimation(mContext, (counter > lastPosition) ? R.anim.up_to_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = counter;


        return convertView;
    }

    void masukin(JSONArray arr, JSONObject json){
        mJsonArray = arr;
        mJsonObject = json;

        //spy tahu kalau di tengah2 isinya berubah
        notifyDataSetChanged();
    }

    private static class SongItem {
        public ImageView pic;
        public TextView singer;
        public TextView song;
    }
}
