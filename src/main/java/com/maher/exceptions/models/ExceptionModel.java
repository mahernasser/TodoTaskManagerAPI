package com.maher.exceptions.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ExceptionModel {
    private String msg;
    private String uri;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy : hh:mm:ss")
    private Date timeStamp;

    public ExceptionModel() {
        this.timeStamp = new Date();
    }

    public ExceptionModel(String msg, String uri) {
        this();
        this.msg = msg;
        this.uri = uri;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}
