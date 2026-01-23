package com.example.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// Shows how to present each "city" object as a row on the screen
public class CityArrayAdapter extends ArrayAdapter<City> {

    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    // Called once for every row
    // Given a city a X position, return a view that represents it
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        // Reuses an old row instead of creating a new one, if possible
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.content, parent, false);
        }

        City city = getItem(position);

        TextView cityText = view.findViewById(R.id.city_text);
        TextView provinceText = view.findViewById(R.id.province_text);

        cityText.setText(city.getName());
        provinceText.setText(city.getProvince());

        return view;
    }
}
