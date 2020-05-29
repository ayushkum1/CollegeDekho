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
import android.widget.Button;
import android.widget.Toast;

import com.example.collegefirebase.Activites.CollegeGallery;
import com.example.collegefirebase.Model.YoutubeVideoModel;
import com.example.collegefirebase.R;
import com.example.collegefirebase.Utils.YoutubeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class YoutubeVideoList extends Fragment {

    private static String ARG_Param1;
    private static String id;
    List<YoutubeVideoModel> vids;
    Button btn;
    YoutubeAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    String mparam1;

    public YoutubeVideoList() {
    }

    //retrieving playlist id from the previous activity
    public static YoutubeVideoList newInstance(String id) {
        YoutubeVideoList yt = new YoutubeVideoList();
        Bundle args = new Bundle();
        args.putString(ARG_Param1, id);
        yt.setArguments(args);
        return yt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mparam1 = getArguments().getString(ARG_Param1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_youtube_video_list, container, false);
    }

    @Override
    public  void onViewCreated(View container, Bundle savedInstanceState) {
        super.onViewCreated(container, savedInstanceState);
        recyclerView = container.findViewById(R.id.vidReclycer);
        manager = new LinearLayoutManager(getActivity());
//        adapter = new YoutubeAdapter(getContext(),vids);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);
//        recyclerView.setAdapter(adapter);

        //String url = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBmISPZAjsrku2_yKLcTW4Y6qq6aqlht-0&playlistId="+id+"&part=snippet&maxResults=26";

        String id = mparam1;
        //right here, id has the playlist id
        System.out.println("this is the playlist id------------------->"+id);
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBmISPZAjsrku2_yKLcTW4Y6qq6aqlht-0&playlistId="+id+"&part=snippet&maxResults=36";
        //even url has the value but the list is not shown and id changes to null
        System.out.println(url);
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        vids = new ArrayList<>();
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONArray itemArray = (JSONArray) mainObject.get("items");
                            for (int i = 0; i < itemArray.length(); i++) {
                                String title = itemArray.getJSONObject(i).getJSONObject("snippet").getString("title");
//                                System.out.println(title);
                                String url = itemArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("maxres").getString("url");
                                String vidid = itemArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                                YoutubeVideoModel vid = new YoutubeVideoModel(title, url, vidid);
                                vids.add(vid);
                                for (YoutubeVideoModel y : vids) {
//                                        System.out.println("ID OF THE VVVVV" + y.toString());
                                }
                            }
                            adapter = new YoutubeAdapter(getContext(), vids);
                            recyclerView.setAdapter(adapter);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("Error in request", error.getMessage());
            }
        });
        queue.add(request);
        }
    }


