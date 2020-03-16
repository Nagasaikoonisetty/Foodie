package com.dileep.myapplication.pojos;

import java.io.Serializable;

public class OrderListPojo implements Serializable {
    String image;
    String Name;
    String cost;

    public OrderListPojo(String image, String name, String cost) {
        this.image = image;
        Name = name;
        this.cost = cost;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return Name;
    }

    public String getCost() {
        return cost;
    }
}
