package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {
    private ArrayList<Earthquake> mEarthquakes;
    private LayoutInflater mInflater;
    private static final String LOCATION_SEPARATOR = " of ";
    private Context mContext;


    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mEarthquakes = earthquakes;
    }


    @NonNull
    @Override
    public EarthquakeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //uses the card view widget as a view holder for data in ArrayList
        View view = mInflater.inflate(R.layout.earthquake_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeAdapter.ViewHolder holder, final int position) {
        //uses the getter methods from Earthquake class to populate ArrayList
        Earthquake currentEarthquake = mEarthquakes.get(position);
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        GradientDrawable magnitudeCircle = (GradientDrawable) holder.eMagnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // Display the magnitude of the current earthquake in that TextView
        holder.eMagnitude.setText(formattedMagnitude);

        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = mContext.getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        holder.primaryLocationView.setText(primaryLocation);
        holder.locationOffsetView.setText(locationOffset);
        String formattedDate = formatDate(dateObject);
        holder.eDate.setText(formattedDate);
        String formattedTime = formatTime(dateObject);
        holder.eTime.setText(formattedTime);

        //since RecyclerView does not have an OnClickListener, I implemented my own
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Earthquake currentEarthquake = mEarthquakes.get(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                mContext.startActivity(websiteIntent);
            }
        });

    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    @Override
    public int getItemCount() {
        return mEarthquakes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView eMagnitude;
        TextView primaryLocationView;
        TextView locationOffsetView;
        TextView eDate;
        TextView eTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eMagnitude = itemView.findViewById(R.id.magnitude);
            primaryLocationView = itemView.findViewById(R.id.primary_location);
            locationOffsetView = itemView.findViewById(R.id.location_offset);
            eDate = itemView.findViewById(R.id.date);
            eTime = itemView.findViewById(R.id.time);

        }

    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(mContext, magnitudeColorResourceId);
    }
}
