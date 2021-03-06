package com.example.litepaldemo;

import org.litepal.crud.LitePalSupport;

public class Song extends LitePalSupport {

    private long id;
    private String name;
    private String singer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
