package com.still.andrew.cub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aes5638 on 2/29/16.
 */
public class BuildingActivity extends AppCompatActivity {


    String firebaseURL;
    Building building;
    Intent intent = getIntent();
    Button mapButton;
    LatLng buildingCoordinates;
    String buildingName;

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_building);



    //<editor-fold desc="Toolbar">
    Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(myToolBar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    //</editor-fold>

    final String selectedBuildingName = getPassedData(savedInstanceState);

    final TextView buildingAreaView = (TextView) findViewById(R.id.building_area);


    //<editor-fold desc="Firebase">
    firebaseURL = ("https://glaring-heat-9011.firebaseio.com/building" );

    new Firebase(firebaseURL)
    .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            System.out.println(snapshot.getValue());
            for (DataSnapshot buildingSnapshot : snapshot.getChildren()) {
                buildingName = (String) buildingSnapshot.child("building_name").getValue();
                String buildingID = (String) buildingSnapshot.child("building_id").getValue();
                String buildingCoordinates = (String) buildingSnapshot.child("building_coordinates").getValue();
                convertLatLng(buildingCoordinates);
                String buildingArea = (String) buildingSnapshot.child("building_area").getValue();
                building = new Building(buildingArea, buildingCoordinates, buildingID, buildingName);
                System.out.println(building.toString());
                if (building.getBuildingName().equals(selectedBuildingName)) {
                    getSupportActionBar().setTitle(building.getBuildingName());
                    buildingAreaView.setText(building.getBuildingArea());
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
                Intent intent = new Intent(BuildingActivity.this, MapsActivity.class);
                Bundle args = new Bundle();
                args.putParcelable("buildingCoordinates", buildingCoordinates);
                intent.putExtra("bundle", args);
                intent.putExtra("buildingName", buildingName);
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
                return extras.getString(("selectedBuildingName"));
            }
        } else {
            return (String) savedInstanceState.getSerializable("selectedBuildingName");
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
        buildingCoordinates = new LatLng(latitude, longitude);
    }


    //</editor-fold>
}


