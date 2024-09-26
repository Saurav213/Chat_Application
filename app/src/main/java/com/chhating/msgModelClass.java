package com.chhating;

public class msgModelClass {

    String message;
    String senderid;
    long timeStapm;

    public msgModelClass() {
    }

    public msgModelClass(String message, String senderid, long timeStapm) {
        this.message = message;
        this.senderid = senderid;
        this.timeStapm = timeStapm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimeStapm() {
        return timeStapm;
    }

    public void setTimeStapm(long timeStapm) {
        this.timeStapm = timeStapm;
    }
}
