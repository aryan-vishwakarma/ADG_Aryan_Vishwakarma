package com.example.task4;

import android.media.Image;

public class ImgUrl {
    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
    public ImgUrl(String message, String status) {
        this.message = message;
        this.status = status;
    }
}
