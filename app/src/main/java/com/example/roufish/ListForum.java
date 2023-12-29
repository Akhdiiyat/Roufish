package com.example.roufish;
public class ListForum {

    private String username;
    private String timestamp;
    private String comment;

    // Constructor
    public ListForum(String username, String timestamp, String comment) {
        this.username = username;
        this.timestamp = timestamp;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getComment() {
        return comment;
    }

    // Setter methods (if needed)

}