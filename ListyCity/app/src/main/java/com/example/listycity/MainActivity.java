package com.example.listycity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declares variables that we use later
    ListView cityList;
    CityArrayAdapter cityAdapter;
    ArrayList<City> dataList;
    Button button_add, button_delete, button_edit; // Initializes the buttons
    int cityPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initializes items
        cityList = findViewById(R.id.city_list);
        button_add = findViewById(R.id.buttonAdd);
        button_delete = findViewById(R.id.buttonDelete);
        button_edit = findViewById(R.id.buttonEdit);

        // Define Array - Starting list
        String []cities = {"Edmonton", "Vancouver", "Calgary"};
        String []provinces = {"AB", "BC", "AB"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CityArrayAdapter(this,  dataList);
        cityList.setAdapter(cityAdapter);

        // When user selects a city, this remembers which one was picked.
        // Used for "Delete City".
        cityList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent,
                                    android.view.View view, int position, long id) {
                cityPos = position;
                City selectedCity = dataList.get(position);  // Gets city name at respective position
                // When a city is selected, small pop-up box confirms which one.
                Toast.makeText(MainActivity.this, "Selected: " + selectedCity.getName()
                               + ", " + selectedCity.getProvince(), Toast.LENGTH_SHORT).show();
            }
        });

        // Functionality of "Add City" button
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // onClick - When user taps button, this runs

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder
                                                          (MainActivity.this);
                builder.setTitle("Add City");

                // Creates layout to hold multiple UI elements (city and province)
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Creates text boxes for city and province names
                final EditText cityInput = new EditText(MainActivity.this);
                cityInput.setHint("City");
                layout.addView(cityInput);

                final EditText provinceInput = new EditText(MainActivity.this);
                provinceInput.setHint("Province");
                layout.addView(provinceInput);

                builder.setView(layout);

                // Adds "Ok" button to confirm edits
                builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {

                        String name = cityInput.getText().toString();
                        String province = provinceInput.getText().toString();

                        dataList.add(new City(name, province));
                        cityAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        // Functionality of "Delete City" button
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityPos != -1 && cityPos < dataList.size()) {
                    // Remove city at selected position
                    City deletedCity = dataList.remove(cityPos);
                    cityAdapter.notifyDataSetChanged();  // Refresh list
                    cityPos = -1; // Reset selection
                }
                else { // If "Delete city" button pressed, but none selected.
                    Toast.makeText(MainActivity.this, "Please select a city first",
                                   Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Functionality of "Edit City" button
        button_edit.setOnClickListener(v -> {
            if (cityPos == -1) {
                Toast.makeText(this, "Please select a city to edit", Toast.LENGTH_SHORT).show();
                return;
            }

            City city = dataList.get(cityPos);

            LinearLayout layout = new LinearLayout(MainActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText cityInput = new EditText(MainActivity.this);
            cityInput.setText(city.getName());
            layout.addView(cityInput);

            final EditText provinceInput = new EditText(MainActivity.this);
            provinceInput.setText(city.getProvince());
            layout.addView(provinceInput);

            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Edit City")
                    .setView(layout)
                    .setPositiveButton("OK", (dialog, which) -> {
                        city.setName(cityInput.getText().toString());
                        city.setProvince(provinceInput.getText().toString());
                        cityAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });



    }
}