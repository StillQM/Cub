package com.still.andrew.cub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

public class DiningAreaActivity extends AppCompatActivity {

    String firebaseURL;
    DiningArea diningArea;
    Intent intent = getIntent();
    Button mapButton;
    LatLng diningAreaCoordinates;
    String diningAreaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);



        //<editor-fold desc="Toolbar">
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //</editor-fold>

        final String selectedDiningAreaName = getPassedData(savedInstanceState);

        final TextView buildingAreaView = (TextView) findViewById(R.id.dining_area_location);


        //<editor-fold desc="Firebase">
        firebaseURL = ("https://glaring-heat-9011.firebaseio.com/dining_area" );

        new Firebase(firebaseURL)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println(snapshot.getValue());
                        for (DataSnapshot diningAreaSnapshot : snapshot.getChildren()) {
                            /*
                            buildingName = (String) buildingSnapshot.child("building_name").getValue();
                            String buildingID = (String) buildingSnapshot.child("building_id").getValue();
                            String buildingCoordinates = (String) buildingSnapshot.child("building_coordinates").getValue();
                            convertLatLng(buildingCoordinates);
                            String buildingArea = (String) buildingSnapshot.child("building_area").getValue();
                            building = new Building(buildingArea, buildingCoordinates, buildingID, buildingName);
                            System.out.println(building.toString());
                            */
                            diningAreaName = (String) diningAreaSnapshot.child("dining_area_name").getValue();
                            String diningAreaID = (String) diningAreaSnapshot.child("dining_area_id").getValue();
                            String diningAreaCoordinates = (String) diningAreaSnapshot.child("dining_area_coordinates").getValue();
                            convertLatLng(diningAreaCoordinates);
                            String diningAreaLocation = (String) diningAreaSnapshot.child("dining_area_location").getValue();
                            diningArea = new DiningArea(diningAreaCoordinates, diningAreaID, diningAreaLocation, diningAreaName);
                            System.out.println(diningArea.toString());
                            if (diningArea.getDiningAreaName().equals(selectedDiningAreaName)) {
                                getSupportActionBar().setTitle(diningArea.getDiningAreaName());
                                buildingAreaView.setText(diningArea.getDiningAreaLocation());
                                return;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {


                    }


                });
        //</editor-fold>

        //<editor-fold desc="Pass values to Google Map">
        mapButton = (Button) findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DiningAreaActivity.this, MapsActivity.class);
                Bundle args = new Bundle();
                args.putParcelable("diningAreaCoordinates", diningAreaCoordinates);
                intent.putExtra("bundle", args);
                intent.putExtra("diningAreaName", diningAreaName);
                startActivity(intent);
            }
        });
        //</editor-fold>

    }

    //<editor-fold desc="External Methods">
    public String getPassedData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                return null;
            } else {
                return extras.getString(("selectedDiningAreaName"));
            }
        } else {
            return (String) savedInstanceState.getSerializable("selectedDiningAreaName");
        }
    }

    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public void convertLatLng(String coordinates){
        String[] latLng = coordinates.split(",");
        double latitude = Double.parseDouble(latLng[0]);
        double longitude = Double.parseDouble(latLng[1]);
        diningAreaCoordinates = new LatLng(latitude, longitude);
    }


    //</editor-fold>
}
