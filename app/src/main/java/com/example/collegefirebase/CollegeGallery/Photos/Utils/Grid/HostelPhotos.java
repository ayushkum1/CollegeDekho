package com.example.collegefirebase.CollegeGallery.Photos.Utils.Grid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegefirebase.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HostelPhotos extends BaseAdapter {
    private Context c;
    private List<String> collegelist;

    public HostelPhotos(Context c, List<String> collegelist) {
        this.c = c;
        this.collegelist = collegelist;
    }

    @Override
    public int getCount() {
        return collegelist.size();
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
            grid = inflater.inflate(R.layout.activity_hostel_photos,null);
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

//            gridImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent in = new Intent(c, SlideImage.class);
//                    in.putExtra("college_id", collegelist.get(position));
//                    c.startActivity(in);
//                    Toast.makeText(c, "Clicked", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
        else {
            grid = (View) convertView;
        }
        return grid;
    }
}
