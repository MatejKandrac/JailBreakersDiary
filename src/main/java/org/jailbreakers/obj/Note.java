package org.jailbreakers.obj;

public class Note {
    private String uid;
    private String content;

    public Note(String uid, String content){
        this.uid = uid;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
