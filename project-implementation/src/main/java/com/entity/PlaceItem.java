package com.entity;

/**
 * Placed rectangular item: includes coordinate information of the placed rectangular item, whether it rotates, and width and height information
 */
public class PlaceItem {

    //name
    private String name;
    //X-coordinate
    private double x;
   // Y coordinate
    private double y;
   // Width (after considering rotation)
    private double w;
    //Height (after considering rotation)
    private double h;
   // Whether to rotate or not
    private boolean isRotate;

    //Constructor
    public PlaceItem(String name, double x, double y, double w, double h, boolean isRotate) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.isRotate = isRotate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public boolean isRotate() {
        return isRotate;
    }

    public void setRotate(boolean rotate) {
        isRotate = rotate;
    }
}
