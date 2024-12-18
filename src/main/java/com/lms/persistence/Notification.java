package com.lms.persistence;

public class Notification {
    private int id;
    private String userId;
    private String message;
    private boolean isRead;

    public Notification(int id, String userId, String message, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
    }

    public int getId() { return id; }
    public String getUserId() { return userId; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
