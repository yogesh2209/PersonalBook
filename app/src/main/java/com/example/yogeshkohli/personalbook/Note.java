package com.example.yogeshkohli.personalbook;

/**
 * Created by yogeshkohli on 4/28/18.
 */

public class Note {

        public String chapterName;
        public String noteContent;
        public String noteType;
        public String noteID;
        public String password;

        public Note() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Note(String chapterName, String noteContent, String noteType, String noteID, String password) {
            this.chapterName = chapterName;
            this.noteContent = noteContent;
            this.noteType = noteType;
            this.noteID = noteID;
            this.password = password;
        }
}

