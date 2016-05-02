package com.still.andrew.cub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity {

    Button addButton;
    Button directionsButton;
    String firebaseGlobalRef;
    Firebase globalRef;
    AuthData currentUser;
    String userID;
    String firebaseRef;
    Firebase ref;
    ListView listView;
    ArrayList<String> locationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addButton = (Button) findViewById(R.id.addButton);
        directionsButton = (Button) findViewById(R.id.directionsButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, NewScheduleActivity.class);
                startActivity(intent);
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        firebaseGlobalRef = "https://glaring-heat-9011.firebaseio.com";

        globalRef = new Firebase(firebaseGlobalRef);

        currentUser = globalRef.getAuth();
        userID = currentUser.getUid();
        System.out.println(userID);

        firebaseRef = "https://glaring-heat-9011.firebaseio.com/user_schedule/" + userID;
        ref = new Firebase(firebaseRef);
        Firebase.setAndroidContext(this);
        locationList = new ArrayList<String>();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String listItem = dataSnapshot.child("itemName").getValue().toString() + " - "
                        + dataSnapshot.child("itemLocation").getValue().toString();
                adapter.add(listItem);
                locationList.add(dataSnapshot.child("itemLocation").getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.remove(dataSnapshot.child("itemName").getValue().toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*directionsButton = (Button) findViewById(R.id.directionsButton);
        directionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                generateDirections();
            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String selectedFromList = (String) listView.getItemAtPosition(position);
                String selectedFromList = (String) locationList.get(position);
                System.out.println(selectedFromList);
                Intent intent = new Intent(ScheduleActivity.this, BuildingActivity.class);
                intent.putExtra("selectedBuildingName", selectedFromList);
                startActivity(intent);
            }
        });

    }


    public void generateDirections() {
        final String buildingUrl = "https://glaring-heat-9011.firebaseio.com/building";
        final Firebase buildingRef = new Firebase(buildingUrl);
        String[] locationArray = new String[listView.getAdapter().getCount()];
        for (int i = 0; i < Array.getLength(locationArray); i++) {
            locationArray[i] = buildingRef.child(locationList.get(i)).child("building_coordinates").toString();
        }
        final String directionsUrl = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + locationList.get(0)
                + "pa&destination="
                + locationList.get(3)
                + "pa&waypoints=via"
                + locationList.get(1)
                + "pa&waypoints=via"
                + locationList.get(2);

    }
}
