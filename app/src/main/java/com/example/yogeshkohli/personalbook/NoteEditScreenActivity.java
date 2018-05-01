package com.example.yogeshkohli.personalbook;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class NoteEditScreenActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit_screen);
        setFocusOnTitleEditText();
        updateUI();
        setupFirebase();
    }

    /* Private Methods */

    public void setupFirebase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Boolean isOldEntryDeleted(){
        //Get datasnapshot at your "users" root node
        DatabaseReference ref = mDatabase.child("notes");
        if (getNoteIdFromPreviousScreen() != null){
            ref.child(getNoteIdFromPreviousScreen()).removeValue();
            return true;
        }
        return false;
    }

    public void updateFirebaseData(String noteID){
        writeNewNote(getEditTextTitle(), getEditTextNoteContent(), "Text", noteID, getEditTextPassword(), getCurrentDateTime());
    }

    public void writeNewNote(String chapterName, String noteContent, String noteType, String noteID, String password, String currentDate) {
        Note note = new Note(chapterName, noteContent, noteType, noteID, password, currentDate);
        mDatabase.child("notes").child(noteID).setValue(note);
        showToast(Constants.DATA_SAVED);
        fireIntent();
    }

    public void fireIntent(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public String generateRandomNoteID() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    //getting password from previous screen
    public String getPasswordFromPreviousScreen() {
        return getIntent().getStringExtra("password");
    }

    //getting noteId from previous screen
    public String getNoteIdFromPreviousScreen() {
        return getIntent().getStringExtra("noteId");
    }

    //getting Chapter Name from previous screen
    public String getChapterNameFromPreviousScreen() {
        return getIntent().getStringExtra("chapterName");
    }

    //getting Chapter content from previous screen
    public String getChapterContentFromPreviousScreen() {
        return getIntent().getStringExtra("content");
    }

    //getting Chapter content from previous screen
    public String getNoteTypeFromPreviousScreen() {
        return getIntent().getStringExtra("type");
    }

    //Setting the focus to first name edit text
    public void setFocusOnTitleEditText() {
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitleNoteEdit);
        titleEditText.requestFocus();
    }

    //Calling all set methods here
    public void updateUI(){
        setChapterName(getChapterNameFromPreviousScreen());
        setChapterContent(getChapterContentFromPreviousScreen());
        setPasswordField(getPasswordFromPreviousScreen());
    }

    //Show toast
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void validateForm(){
        if (getEditTextTitle().length() == 0 || getEditTextNoteContent().length() == 0){
            showToast(Constants.EMPTY_EDIT_TEXT);
            return;
        }

        if (isOldEntryDeleted()){
            updateFirebaseData(getNoteIdFromPreviousScreen());
        }
        else{
          updateFirebaseData(generateRandomNoteID());
        }
    }

    public String getCurrentDateTime() {
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        LocalDateTime localTime= LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return localTime.format(formatter);
    }

    /* Set data to textviews on the screen */

    public void setChapterName(String chapterName){
        if (chapterName != null) {
            EditText editText = (EditText)findViewById(R.id.editTextTitleNoteEdit);
            editText.setText(chapterName, TextView.BufferType.EDITABLE);
        }
        else{
            EditText editText = (EditText)findViewById(R.id.editTextTitleNoteEdit);
            editText.setText("", TextView.BufferType.EDITABLE);
        }
    }
    public void setChapterContent(String chapterContent){
        if (chapterContent != null) {
            EditText editText = (EditText)findViewById(R.id.editTextContentNoteEdit);
            editText.setText(chapterContent, TextView.BufferType.EDITABLE);
        }
        else {
            EditText editText = (EditText)findViewById(R.id.editTextContentNoteEdit);
            editText.setText("", TextView.BufferType.EDITABLE);
        }
    }

    public void setPasswordField(String passwordField){
        if (passwordField != null) {
            EditText editText = (EditText)findViewById(R.id.editTextPasswordNoteEdit);
            editText.setText(passwordField, TextView.BufferType.EDITABLE);
        }
        else{
            EditText editText = (EditText)findViewById(R.id.editTextPasswordNoteEdit);
            editText.setText("", TextView.BufferType.EDITABLE);
        }
    }


    /* -------- UIButton Actions METHODS -------------*/

    public void editNoteFinalButtonClicked(View button) {
        validateForm();

    }
    public void setReminderButtonClicked(View button) {
        if (getEditTextTitle().length() == 0 || getEditTextNoteContent().length() == 0) {
            showToast(Constants.EMPTY_EDIT_TEXT);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getCurrentDateTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,getCurrentDateTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.Events.TITLE, getEditTextTitle());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, getEditTextNoteContent());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Home");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        startActivity(intent);
    }

      /* -------- GET EDIT TEXT VALUE METHODS -------------*/

    public String getEditTextTitle(){
        EditText titleEditText = (EditText) findViewById(R.id.editTextTitleNoteEdit);
        String  titleString = titleEditText.getText().toString().trim();
        return titleString;
    }
    public String getEditTextNoteContent(){
        EditText noteContentEditText = (EditText) findViewById(R.id.editTextContentNoteEdit);
        String  noteContentString = noteContentEditText.getText().toString().trim();
        return noteContentString;
    }
    public String getEditTextPassword(){
        EditText pswdText = (EditText) findViewById(R.id.editTextPasswordNoteEdit);
        String  pswdString = pswdText.getText().toString().trim();
        return pswdString;
    }
}
