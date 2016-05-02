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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewScheduleActivity extends AppCompatActivity {


    TextView selectedBuilding;
    Button addButton;
    String selected;
    String className;
    EditText classNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedBuilding = (TextView) findViewById(R.id.locationText);

        //<editor-fold desc="Class Name Edit Text"
        classNameEditText = (EditText) findViewById(R.id.classInput);
        className = classNameEditText.getText().toString();
        //</editor-fold>

        //<editor-fold desc="Listview and click listener">
        //Get ListView object
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) listView.getItemAtPosition(position);
                System.out.println(selected);
                selectedBuilding.setText("Location: " + selected);
            }
        });
        //</editor-fold>

        //<editor-fold desc="List searcher">
        final EditText buildingSearch = (EditText) findViewById(R.id.buildingSearch);

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

        //</editor-fold>

        //<editor-fold desc="Add button">
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClassToList(selected, className);
            }
        });
        //</editor-fold>

    }

    public void addClassToList(String selectedBuilding, String className){
        final String firebaseGlobalRef = "https://glaring-heat-9011.firebaseio.com";
        final Firebase globalRef = new Firebase(firebaseGlobalRef);
        final AuthData currentUser = globalRef.getAuth();
        final String currentUserID = currentUser.getUid();
        final String firebaseScheduleRef = "https://glaring-heat-9011.firebaseio.com/user_schedule/" + currentUserID;
        final Firebase ref = new Firebase(firebaseScheduleRef);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
