package com.dev.myapplication.objet;


public class favoris {

    private String name;
    private int duration,id;
    private String desc,img;
    private int guest;

    public favoris() {
    }

    public favoris(int id, String img,String name, int duration, String desc, int guest) {
        this.id=id;
        this.img=img;
        this.name=name;
        this.duration=duration;
        this.desc=desc;
        this.guest=guest;
    }
}
