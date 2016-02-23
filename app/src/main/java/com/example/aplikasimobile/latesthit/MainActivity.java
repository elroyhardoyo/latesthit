package com.example.aplikasimobile.latesthit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    ListView latestHits;
    TextView judul;
    ImageView gambar;

    ArrayList mArrayList;
    ArrayAdapter mAdapter;

    SongItemAdapter mJSONAdapter;

    LinearLayout popup;


    TextView popup_song;
    TextView popup_singer;
    ImageView popup_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latestHits =  (ListView) findViewById(R.id.list1);

         judul = (TextView) findViewById(R.id.judul);
         gambar = (ImageView) findViewById(R.id.gambar);


        popup = (LinearLayout) findViewById(R.id.popup);

        popup_pic = (ImageView) findViewById(R.id.popup_pic);
        popup_singer = (TextView) findViewById(R.id.popup_singer);
        popup_song = (TextView) findViewById(R.id.popup_song);

//        //2.inisiasi ArrayList sebagai wadah dari List kita
//        mArrayList = new ArrayList();
//
//        //3.Inisiasi Adapter
//        mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,mArrayList);
//
//        //4.Set Adapter
//        latestHits.setAdapter(mAdapter);


        // 18. Tambahkan mJSONAdapter
        mJSONAdapter = new SongItemAdapter(this, getLayoutInflater());

        // 19. Set ListView untuk memakai JSONAdapter
        latestHits.setAdapter(mJSONAdapter);


        latestHits.setDivider(null);

        latestHits.setOnItemClickListener(this);

//        query_data();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.1.48:8888/latesthit.json",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        Toast.makeText(getApplicationContext(), jsonObject.optString("picture"), Toast.LENGTH_SHORT).show();
                        Log.d("aplikasi-mobile.com", jsonObject.toString());

                        // ambil judulnya dari JSON Object
                        judul.setText(jsonObject.optString("id"));

                        //ambil gambarnya dari JSON Object
                        Picasso.with(getApplicationContext()).load(jsonObject.optString("picture")).into(gambar);

                        //ambil listnya
                        JSONArray items = jsonObject.optJSONArray("items");

                        mJSONAdapter.masukin(items);

//                        String name;
//                        String song;
//                        for (int counter = 0; counter < items.length(); counter = counter +1) {
//
//                            try {
//
//                                //ambil object sesuai urutan di array
//                                JSONObject row = items.getJSONObject(counter);
//
//                                //ambil value dari attribute singer
//                                name = row.getString("singer");
//
//                                song = row.getString("song");
//
//                                mArrayList.add(name + " " + song);
//
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        // 15. Dismiss the ProgressDialog
//                        mDialog.dismiss();

                        //16. Keluarkan toast
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        //17. Print Log
                        Log.e("aplikasi-mobile.com", statusCode + " " + throwable.getMessage());

                    }
                });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("halo", Integer.toString(position));
         /*
        Intent intent = new Intent(this,SecondActivity.class);
        String txt = (String) mArrayList.get(position);
        intent.putExtra("name", txt);

        startActivity(intent);*/

        // 21. ambil data gambar
//        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
//        String pics = jsonObject.optString("picture","");
//
//        // 22. buat Intent untuk berpindah ke SecondActivity
//        Intent detailIntent = new Intent(this, SecondActivity.class);
//
//        // 23. Isi putExtra
//        detailIntent.putExtra("pics", pics);
//
//        String name = jsonObject.optString("name","");
//        detailIntent.putExtra("name", name);
//
//        String description_long = jsonObject.optString("description_long","");
//        detailIntent.putExtra("descr", description_long);
//
//        // start the next Activity using your prepared Intent
//        startActivity(detailIntent);

        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
        popup_song.setText(jsonObject.optString("song"));
        popup_singer.setText(jsonObject.optString("singer"));

        Picasso.with(getApplicationContext()).load(jsonObject.optString("pic")).into(popup_pic);

        popup.setVisibility(view.VISIBLE);
    }

    public void tutupPopup(View view){
        popup.setVisibility(view.INVISIBLE);
//        Toast.makeText(this, "Button1 clicked.", Toast.LENGTH_SHORT).show();
    }

}
