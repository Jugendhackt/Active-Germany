package com.tomaskostadinov.activegermany;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Tomas on 02.08.2015
 */
// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> implements View.OnClickListener {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public TextView tvDescription;
        public ImageView ivPhoto;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.title);
            this.tvDescription = (TextView) itemView.findViewById(R.id.descripton);
            this.ivPhoto = (ImageView) itemView.findViewById(R.id.activity_photo);
        }
    }

    private ArrayList<Activity> array_activities;
    // Store the context for later use
    private Context context;

    // Pass in the context and users array into the constructor
    public OverviewAdapter(Context context, ArrayList<Activity> array_activities) {
        this.array_activities = array_activities;
        this.context = context;
    }

    @Override
    public OverviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        final View itemView = LayoutInflater.from(context).inflate(R.layout.card_activity, parent, false);
        //itemView.setOnClickListener(mCLick());
        // Return a new holder instance
        return new OverviewAdapter.ViewHolder(itemView);
    }

    // Involves populating data into the item through holder

    @Override
    public void onBindViewHolder(OverviewAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Activity activities = array_activities.get(position);
        // Set item views based on the data model
        holder.tvDescription.setText(activities.description);
        holder.tvTitle.setText(activities.title);
        Glide.with(holder.ivPhoto.getContext()).load(activities.url)
                .fitCenter()
                .into(holder.ivPhoto);
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return array_activities.size();
    }

    @Override
    public void onClick(View v){
        Log.i("onClick", "on View: " + v.toString());
        Intent detail = new Intent(context, AddActivity.class);
        detail.putExtra("id", id); //Optional parameters
        context.startActivity(detail);
    }
}

