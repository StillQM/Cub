package com.still.andrew.cub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by aes5638 on 2/29/16.
 */
public class BuildingActivity extends AppCompatActivity {


    String firebaseURL;
    Building building;
    Intent intent = getIntent();

    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_building);

    Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(myToolBar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);


    final String buildingID = getPassedData(savedInstanceState);

    final TextView buildingAreaView = (TextView) findViewById(R.id.building_area);





    firebaseURL = ("https://glaring-heat-9011.firebaseio.com/building" );



    new Firebase(firebaseURL)
    .addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            System.out.println(snapshot.getValue());
            for(DataSnapshot buildingSnapshot: snapshot.getChildren()){
                String buildingName = (String) buildingSnapshot.child("building_name").getValue();
                String buildingID = (String) buildingSnapshot.child("building_id").getValue();
                String buildingCoordinates = (String) buildingSnapshot.child("building_coordinates").getValue();
                String buildingArea = (String) buildingSnapshot.child("building_area").getValue();
                building = new Building(buildingArea, buildingCoordinates, buildingID, buildingName);
                System.out.println(building.toString());
                if(building.getBuildingID() == buildingID){
                    getSupportActionBar().setTitle(building.getBuildingName());
                    buildingAreaView.setText(building.getBuildingArea());
                }
            }



            /*for(DataSnapshot buildingSnapshot: snapshot.getChildren()){
                building = buildingSnapshot.getValue(Building.class);
                System.out.println(building.toString());
                System.out.println(building.getBuildingID());
                System.out.println(buildingID);
                if(building.getBuildingID().equals(buildingID)){
                    getSupportActionBar().setTitle(building.getBuildingName());
                    //buildingTitle.setText(building.getEventName());
                    buildingArea.setText(building.getBuildingArea());
                    return;
                }
            }*/

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {


        }

    });







}

    public String getPassedData(Bundle savedInstanceState){
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                return null;
            } else {
                return extras.getString(("ITEM_ID"));
            }
        } else {
            return (String) savedInstanceState.getSerializable("ITEM_ID");
        }
    }

    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}


