package com.example.yogeshkohli.personalbook;

import java.util.Date;

/**
 * Created by yogeshkohli on 4/28/18.
 */

public class Note {

        public String chapterName;
        public String noteContent;
        public String noteType;
        public String noteID;
        public String password;
        public String currentDate;

        public Note() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        //constructor
        public Note(String chapterName, String noteContent, String noteType, String noteID, String password, String currentDate) {
            this.chapterName = chapterName;
            this.noteContent = noteContent;
            this.noteType = noteType;
            this.noteID = noteID;
            this.password = password;
            this.currentDate = currentDate;
        }
}

