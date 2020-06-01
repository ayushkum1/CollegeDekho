package com.example.collegefirebase.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegefirebase.Model.YoutubeVideoModel;
import com.example.collegefirebase.R;
import com.example.collegefirebase.Utils.YoutubeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YoutubeVideoList extends Fragment {

    private String id;
    List<YoutubeVideoModel> vids;
    YoutubeAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    public YoutubeVideoList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        if(args != null){
            //getting the playlist id from bundle passsed from previous activity
            id = args.getString("listid");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // we have to find views in the layout, we cannot use conatainer as its the Viewgroup. so we define "v" which will be used to identify the views.
            View v = inflater.inflate(R.layout.fragment_youtube_video_list, container, false);
            //recycler view to display the list of videos. we can also change it to grid by only displaying the thumbnail.
            recyclerView = v.findViewById(R.id.vidReclycer);
            //in fragment we need getActivity or getContext instead of "this" or "YoutubeVideoList.this"
            manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(false);
            final String yturl = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBmISPZAjsrku2_yKLcTW4Y6qq6aqlht-0&playlistId=" + id + "&part=snippet&maxResults=36";
            RequestQueue queue = Volley.newRequestQueue(getContext());
            // Volley request using url with the playlistid in it
            StringRequest request = new StringRequest(Request.Method.GET, yturl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            vids = new ArrayList<>();
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                JSONArray itemArray = (JSONArray) mainObject.get("items");
                                for (int i = 0; i < itemArray.length(); i++) {
                                    //extracting 3 things : title, thumbnail, videoid(need it to play video in the player activity).
                                    String title = itemArray.getJSONObject(i).getJSONObject("snippet").getString("title");
                                    String url = itemArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("maxres").getString("url");
                                    String vidid = itemArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                                    YoutubeVideoModel vid = new YoutubeVideoModel(title, url, vidid);
                                    // add all the details to the list
                                    vids.add(vid);
                                }
                                adapter = new YoutubeAdapter(getActivity(), vids);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                Log.e("Error in request", error.getMessage());
                }
            });
            queue.add(request);
        return v;
    }
}


