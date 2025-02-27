package com.example.collegefirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CollegeImageGrid extends BaseAdapter {

    private Context c;
    private List<String> collegelist; //for storing the list of urls in imageurls, it isa list of multiple strings in it.

    public CollegeImageGrid(Context c, List<String> collegelist) {
        this.c = c;
        this.collegelist = collegelist;
    }

    @Override
    public int getCount() {
        //troubleshoot when null found
        return collegelist.size(); //returns the size of the list
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        //inflate the grid layout when called
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            grid = new View(c);
            grid = inflater.inflate(R.layout.collegeimagegrid,null);
            ImageView gridImage = grid.findViewById(R.id.image_grid_id);
            //load images to image view with picasso. there will be no getImage() in this statement
            Picasso.with(c).load(collegelist.get(position)).into(gridImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Toast.makeText(c, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            grid = (View) convertView;
        }
        return grid;
    }

}
