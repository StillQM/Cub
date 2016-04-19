package com.still.andrew.cub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiningAreaListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_area_list);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolBar.setTitle("Cub");
        setSupportActionBar(myToolBar);

        //<editor-fold desc="Spinner">
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(spinner.getSelectedItem().toString());
                if (spinner.getSelectedItem().toString().equals("Buildings")) {
                    Intent intent = new Intent(DiningAreaListActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
                if (spinner.getSelectedItem().toString().equals("Dining Areas")) {


                }
                if (spinner.getSelectedItem().toString().equals("Events")) {
                    Intent intent = new Intent(DiningAreaListActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //</editor-fold>

        //<editor-fold desc="Listview and click listener">
        //Get ListView object
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) listView.getItemAtPosition(position);
                System.out.println(selectedFromList);
                Intent intent = new Intent(DiningAreaListActivity.this, DiningAreaActivity.class);
                //intent.putExtra("ITEM_ID", String.valueOf(id + 1));
                intent.putExtra("selectedDiningAreaName", selectedFromList);
                startActivity(intent);
            }
        });
        //</editor-fold>

        //<editor-fold desc="List searcher">
        final EditText buildingSearch = (EditText) findViewById(R.id.diningAreaSearch);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        //Create new adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);


        //Assign adapter
        listView.setAdapter(adapter);




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
        //</editor-fold>

        //<editor-fold desc="Firebase">
        //Use firebase
        Firebase.setAndroidContext(this);

        Firebase ref = new Firebase("https://glaring-heat-9011.firebaseio.com/dining_area");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add((String) dataSnapshot.child("dining_area_name").getValue());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.remove((String) dataSnapshot.child("dining_area_name").getValue());
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

        //</editor-fold>



    }

}
