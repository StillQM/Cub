package com.still.andrew.cub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
        final String selectedEventName = getPassedData(savedInstanceState);

        //<editor-fold desc="Tooldbar">
        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //</editor-fold>

        //<editor-fold desc="Init XML Componenets">
        final TextView eventTitle = (TextView) findViewById(R.id.title_text);
        final TextView eventDescription = (TextView) findViewById(R.id.event_description);
        final TextView eventVenue = (TextView) findViewById(R.id.event_venue);
        final TextView eventDate = (TextView) findViewById(R.id.event_date);
        final TextView eventTime = (TextView) findViewById(R.id.event_time);
        final ImageView eventPhoto = (ImageView) findViewById(R.id.event_photo);
        //</editor-fold>

        //<editor-fold desc="Firebase">
        firebaseURL = ("https://glaring-heat-9011.firebaseio.com/eventItems/February2016/10/" );

        new Firebase(firebaseURL)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot){
                        System.out.println(snapshot.getValue());

                        for(DataSnapshot eventSnapshot: snapshot.getChildren()){
                            event = eventSnapshot.getValue(Event.class);
                            /*
                            System.out.println(event.toString());
                            System.out.println(event.getEventID());
                            System.out.println(eventID);
                            */
                            if(event.getEventName().equals(selectedEventName)){
                                getSupportActionBar().setTitle(event.getEventName());
                                //eventTitle.setText(event.getEventName());
                                eventDate.setText(event.getEventDate());
                                eventTime.setText(event.getEventTime());
                                eventVenue.setText(event.getEventVenue());
                                eventDescription.setText(event.getEventDescription());
                                eventPhoto.setImageBitmap(base64ToBitmap(event.getEventPhoto()));
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError){


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
                return extras.getString(("selectedEventName"));
            }
        } else {
            return (String) savedInstanceState.getSerializable("selectedEventName");
        }
    }

    public Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
    //</editor-fold>




}