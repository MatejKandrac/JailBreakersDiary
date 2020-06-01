package org.jailbreakers.obj;

public class User {
    private String uid;
    private String email;
    private String pass;
    //private Note[] notes;
    private Note note;

    public User(String uid, String email, String pass, Note note){
        this.uid = uid;
        this.email = email;
        this.pass = pass;
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public Note getNote() {
        return note;
    }

    public String getPass() {
        return pass;
    }

    public String getUid() {
        return uid;
    }
}
