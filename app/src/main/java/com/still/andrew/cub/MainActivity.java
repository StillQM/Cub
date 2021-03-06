package com.still.andrew.cub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    Bitmap eventPhoto;
    ArrayList photoArray;

    EditText eventSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);

        photoArray = new ArrayList();

        //<editor-fold desc="Listview with click listener">
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                intent.putExtra("selectedEventName", selectedFromList);
                startActivity(intent);
            }
        });
        //</editor-fold>

        //<editor-fold desc="Spinner">
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(spinner.getSelectedItem().toString());
                if (spinner.getSelectedItem().toString().equals("Buildings")) {
                    Intent intent = new Intent(MainActivity.this, BuildingListActivity.class);
                    startActivity(intent);
                    System.out.println("matched");
                }
                if (spinner.getSelectedItem().toString().equals("Dining Areas")) {
                    Intent intent = new Intent(MainActivity.this, DiningAreaListActivity.class);
                    finish();
                    startActivity(intent);

                }
                if (spinner.getSelectedItem().toString() == "Events") {

                }
                if (spinner.getSelectedItem().toString().equals("Schedule")) {
                    Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //</editor-fold>

        //<editor-fold desc="Adapter and list for event List View">
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        //Create new adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.my_list, R.id.Itemname);
        final ArrayAdapter<Bitmap> adapter2 = new ArrayAdapter<>(this, R.layout.my_list, R.id.icon);

        //Assign adapter
        listView.setAdapter(adapter);
        //</editor-fold>

        //<editor-fold desc="List Searcher">
        //Searcher EditText
        eventSearch = (EditText) findViewById(R.id.eventSearch);
        eventSearch.addTextChangedListener(new TextWatcher() {
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

        //<editor-fold desc="Firebase declaration and methods">
        //Use firebase
        Firebase.setAndroidContext(this);


        final Firebase ref = new Firebase("https://glaring-heat-9011.firebaseio.com/eventItems/February2016/10");
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        adapter.add((String) dataSnapshot.child("eventName").getValue());
                        eventPhoto = base64ToBitmap(dataSnapshot.child("eventPhoto").getValue().toString());
                        //adapter2.add((Bitmap)eventPhoto);
                        photoArray.add(eventPhoto);

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        adapter.remove((String) dataSnapshot.child("eventName").getValue());
                        adapter2.remove((Bitmap) dataSnapshot.child("eventPhoto").getValue());
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

    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


}
