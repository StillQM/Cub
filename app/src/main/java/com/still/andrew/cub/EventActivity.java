package com.still.andrew.cub;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventActivity extends AppCompatActivity {


    private String firebaseURL;
    Event event;
    Intent intent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        final TextView eventTitle = (TextView) findViewById(R.id.title_text);
        final TextView eventDescription = (TextView) findViewById(R.id.event_description);

        final String newString;
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString = null;
            } else {
                newString = extras.getString(("ITEM_ID"));
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("ITEM_ID");
        }

        firebaseURL = ("https://glaring-heat-9011.firebaseio.com/eventItems/February2016/10/" );



        new Firebase(firebaseURL)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot){
                        System.out.println(snapshot.getValue());

                        for(DataSnapshot eventSnapshot: snapshot.getChildren()){
                            event = eventSnapshot.getValue(Event.class);
                            System.out.println(event.toString());
                            System.out.println(event.getEventID());
                            System.out.println(newString);
                            if(event.getEventID().equals(newString)){
                                eventTitle.setText(event.getEventName());
                                eventDescription.setText(event.getEventDescription());
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError){


                    }

                    /*public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        System.out.println(dataSnapshot.getValue());
                        event = dataSnapshot.getValue(Event.class);
                        //event = new Event((String)dataSnapshot.child("eventName").getValue());
                        //for(DataSnapshot eventSnapshot : dataSnapshot.getChildren()){
                        //    event = eventSnapshot.getValue(Event.class);
                        //}
                        //event = dataSnapshot.getValue(Event.class);
                        //System.out.println(" Debug: " + dataSnapshot.getValue());
                        eventTitle.setText(event.getEventName());


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        //System.out.println(dataSnapshot.getValue());

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }*/




                });







    }

}