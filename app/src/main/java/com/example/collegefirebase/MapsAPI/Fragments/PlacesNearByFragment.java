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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public class PlacesNearByFragment extends Fragment implements OnMapReadyCallback {

    private List<NearByPlacesModel> placelist;
    private PlacesAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private GoogleMap mMap;
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

        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.nearby_location_map);
        mMapFragment.getMapAsync(this);

        recyclerView = view.findViewById(R.id.nearby_recycler);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=3000&types="+type+"&key=AIzaSyB1S9UmNAPR4ZaPfRddgkcmFzm2gdtT3d0";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        placelist = new ArrayList<>();
                        String photoref = "";
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            JSONArray itemArray = (JSONArray) mainObject.get("results");
                            for (int i = 0; i < itemArray.length(); i++) {
                                String name = itemArray.getJSONObject(i).getString("name");
                                String rating = "4"; // check for error
                                String id = itemArray.getJSONObject(i).getString("id");
                                String address = itemArray.getJSONObject(i).getString("vicinity");
                                String lat = itemArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                                String lng = itemArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                                //calling function for adding markers
                                addMarkers(lat,lng,name);
                                NearByPlacesModel mod = new NearByPlacesModel(name, address, rating, id, lat, lng);
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


    public void addMarkers(String lt, String ln, String name){
        LatLng nearbyloc = new LatLng(Double.parseDouble(lt), Double.parseDouble(ln));
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.addMarker(markerOptions.position(nearbyloc).title("Marker in " +name));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float default_zoom;
        mMap = googleMap;
        LatLng college = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        default_zoom = 16.0f;
        mMap.addMarker(new MarkerOptions().position(college).title("Marker in " ));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(college,default_zoom));
    }
}
