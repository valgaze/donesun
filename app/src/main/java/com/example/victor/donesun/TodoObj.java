package com.example.victor.donesun;

import java.io.Serializable;

/**
 * Created by victor on 6/22/16.
 */
public class TodoObj implements Serializable {
    private int position;
    private String content;

    public TodoObj(int position, String content) {
        this.position = position;
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
