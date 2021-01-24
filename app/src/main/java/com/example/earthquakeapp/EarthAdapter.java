package com.example.earthquakeapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthAdapter extends ArrayAdapter<Earthquake> {

    public EarthAdapter(Context context, List<Earthquake> earthquakes)
    {
        super(context,0,earthquakes);

    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
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
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom,parent,false);
        TextView mag = view.findViewById(R.id.mag);
        TextView place = view.findViewById(R.id.place);
        TextView date = view.findViewById(R.id.date);
        TextView time = view.findViewById(R.id.time);
        Earthquake curr = getItem(position);
        TextView placelo = view.findViewById(R.id.placeloca);

        String formattedMagnitude = formatMagnitude(curr.getMagnitude());
        mag.setText(formattedMagnitude);

        String places[] = curr.getPlace().split("of ");
        if(places.length == 1)
        {
            place.setText(" ");
            placelo.setText(places[0]);
        }
        else
        {
            place.setText(places[0]);
            placelo.setText(places[1]);
        }


        GradientDrawable circle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(curr.getMagnitude());

        // Set the color on the magnitude circle
        circle.setColor(magnitudeColor);

        Date dateObject = new Date(curr.getTimeInMilliseconds());
        String formattedDate = formatDate(dateObject);
        date.setText(formattedDate);
        String formattedTime = formatTime(dateObject);
        time.setText(formattedTime);

        return view;

    }
}