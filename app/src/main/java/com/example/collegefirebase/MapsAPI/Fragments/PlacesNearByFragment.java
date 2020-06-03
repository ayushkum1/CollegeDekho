package com.example.collegefirebase.MapsAPI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.collegefirebase.MapsAPI.Models.NearByPlacesModel;
import com.example.collegefirebase.R;
import com.example.collegefirebase.MapsAPI.Utils.PlacesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlacesNearByFragment extends Fragment {

    List<NearByPlacesModel> placelist;
    PlacesAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    private String lat, lng, type, heading;


    public PlacesNearByFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lat = getArguments().getString("lat");
            lng = getArguments().getString("lng");
            type = getArguments().getString("type");
            heading = getArguments().getString("heading");//not using it currently
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_by_places, container, false);

        recyclerView = view.findViewById(R.id.nearby_recycler);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=3000&types="+type+"&key=AIzaSyAbWUx3SB5w5WpVKg7FfwsZlLyhxWnVpWo";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        placelist = new ArrayList<>();
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONArray itemArray = (JSONArray) mainObject.get("results");
                            for (int i = 0; i < itemArray.length(); i++) {
                                String name = itemArray.getJSONObject(i).getString("name");
                                String rating = "4"; // check for error
                                String id = itemArray.getJSONObject(i).getString("id");
                                String address = itemArray.getJSONObject(i).getString("vicinity");
                                NearByPlacesModel mod = new NearByPlacesModel(name, address, rating, id);
                                placelist.add(mod);
                            }
                            adapter = new PlacesAdapter(getContext(), placelist);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error in request", error.getMessage());
            }
        });
        queue.add(request);
        return  view;
        }
}
