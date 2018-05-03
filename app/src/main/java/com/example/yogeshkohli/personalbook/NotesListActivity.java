package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class NotesListActivity extends AppCompatActivity {

    Map<String, Object> snapshotFirebaseData;
    ArrayList<String> chapterArrayList = new ArrayList<String>();
    ArrayList<String> passwordArrayList = new ArrayList<String>();
    ArrayList<String> noteIdArrayList = new ArrayList<String>();
    ArrayList<String> noteContentArrayList = new ArrayList<String>();
    ArrayList<String> dateArrayList = new ArrayList<String>();
    ArrayList<String> noteTypeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        //Get datasnapshot at your "users" root node
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("notes");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        snapshotFirebaseData = (Map) dataSnapshot.getValue();

                        if (snapshotFirebaseData == null) {
                            return;
                        }

                        for (Map.Entry<String,Object> entry : snapshotFirebaseData.entrySet()) {
                            System.out.printf("Key is %s -> %s%n", entry.getKey(), entry.getValue());
                            Map<String, String> mapRow = (Map) entry.getValue();
                            chapterArrayList.add(mapRow.get("chapterName"));
                            passwordArrayList.add(mapRow.get("password"));
                            noteContentArrayList.add(mapRow.get("noteContent"));
                            dateArrayList.add(mapRow.get("currentDate"));
                            noteTypeList.add(mapRow.get("noteType"));
                            noteIdArrayList.add(entry.getKey());
                        }

                        if (chapterArrayList.size() != 0) {
                            setupListView(chapterArrayList);
                        }
                        else{
                            showToast(Constants.NO_SAVED_NOTES);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        showToast(Constants.SOMETHING_WRONG_MESSAGE);
                    }
                });


    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //List view setup
    public void setupListView(final ArrayList<String> chapters) {
        ListView listView = (ListView) findViewById(R.id.listViewNotesList);
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chapters);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                //take course id and pass it to next screen
                                                fireIntent(position);
                                            }
                                        }
        );
    }

    //Fire Intent
    public void fireIntent(int position) {
        //No password entered - take him to direct note detail screen
        if (passwordArrayList.get(position).matches("")) {
            Intent i = new Intent(this, NoteDetailsScreenActivity.class);
            i.putExtra("password","");
            i.putExtra("content", noteContentArrayList.get(position));
            i.putExtra("chapterName", chapterArrayList.get(position));
            i.putExtra("noteId", noteIdArrayList.get(position));
            i.putExtra("currentDate", dateArrayList.get(position));
            i.putExtra("type", noteTypeList.get(position));
            startActivity(i);
        }
        //Take him to password entering screen
        else{
            Intent i = new Intent(this, PasswordEnterActivity.class);
            i.putExtra("content", noteContentArrayList.get(position));
            i.putExtra("password",passwordArrayList.get(position));
            i.putExtra("noteId", noteIdArrayList.get(position));
            i.putExtra("chapterName", chapterArrayList.get(position));
            i.putExtra("currentDate", dateArrayList.get(position));
            i.putExtra("type", noteTypeList.get(position));
            startActivity(i);
        }
    }
}
