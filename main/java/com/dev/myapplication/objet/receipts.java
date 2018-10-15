package com.dev.myapplication.objet;

import java.io.Serializable;

public class receipts implements Serializable{

        private String name;
        private int duration,id;
        private String desc,img;
        private int guest;
        private String name2;
        private String qtity;

        public receipts() {
        }

        public receipts(String name,int duration,String desc,int guest) {

            this.name=name;
            this.duration=duration;
            this.desc=desc;
            this.guest=guest;
        }

    public receipts( String name, int duration, int guest) {
        this.name=name;
        this.duration=duration;
        this.guest=guest;
    }
    public receipts(String name, int duration, int id, String desc, String img, int guest) {
        this.name = name;
        this.duration = duration;
        this.id = id;
        this.desc = desc;
        this.img = img;
        this.guest = guest;
    }

    public receipts(String name, int duration, int id, String desc, String img, int guest,String name2,String qtity) {
        this.name = name;
        this.duration = duration;
        this.id = id;
        this.desc = desc;
        this.img = img;
        this.guest = guest;
        this.name2=name2;
        this.qtity=qtity;
    }


    public String getQtity() {
        return qtity;
    }

    public void setQtity(String qtity) {
        this.qtity = qtity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
