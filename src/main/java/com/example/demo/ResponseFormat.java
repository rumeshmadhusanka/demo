package com.example.demo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ResponseFormat implements Serializable {
    private String status;
    private String message;
    private Timestamp timestamp;
    private ArrayList<Object> data;

    public ResponseFormat(String status, String message, ArrayList data) {
        this.status = status;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
