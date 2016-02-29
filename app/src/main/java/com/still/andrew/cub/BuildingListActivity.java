package com.still.andrew.cub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aes5638 on 2/28/16.
 */
public class BuildingListActivity extends AppCompatActivity {
    private GoogleApiClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        //Get ListView object
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(BuildingListActivity.this, BuildingActivity.class);
                intent.putExtra("ITEM_ID", String.valueOf(id + 1));
                startActivity(intent);
            }
        });

        /*final Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        ArrayAdapter<String>spinnerAdapter;
        List<String> spinnerList;

        spinnerList = new ArrayList<String>();
        spinnerList.add("Events");
        spinnerList.add("Buildings");
        spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, spinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString() == "Events") {
                    Intent intent = new Intent(BuildingListActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                if (spinner.getSelectedItem().toString() == "Buildings") {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/




        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        final EditText buildingSearch = (EditText) findViewById(R.id.buildingSearch);



        //Create new adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);


        //Assign adapter
        listView.setAdapter(adapter);

        //Use firebase
        Firebase.setAndroidContext(this);

        //Searcher EditText

        buildingSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Firebase ref = new Firebase("https://glaring-heat-9011.firebaseio.com/building");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add((String) dataSnapshot.child("building_name").getValue());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.remove((String) dataSnapshot.child("building_name").getValue());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });





    }
}
