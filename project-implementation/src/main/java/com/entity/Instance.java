package com.entity;

import java.util.List;

/**
 * Description：
 * Instance object: inside is information about the entire problem (including the bin information, rectangular item collections, etc.)
 */
public class Instance {




    //The width of the bin
    private double W;
    //The height of the bin
    private double H;
    //Rectangular items list
    private List<Item> itemList;
    //Whether to allow items to rotate
    private boolean isRotateEnable;

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public boolean isRotateEnable() {
        return isRotateEnable;
    }

    public void setRotateEnable(boolean rotateEnable) {
        isRotateEnable = rotateEnable;
    }
}
