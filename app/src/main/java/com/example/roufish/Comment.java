package com.example.roufish;

import java.util.Date;

public class Comment {

    private String commentText;
    private String userId;
    private String username;
    private Date timestamp;
    private String forumId;

    public Comment() {
        // Required empty constructor for Firestore
    }


    public Comment(String commentText, String userId, String forumId, Date timestamp) {
        this.commentText = commentText;
        this.userId = userId;
        this.forumId = forumId;
        this.timestamp = timestamp;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }
}
