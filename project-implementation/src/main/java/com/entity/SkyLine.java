package com.entity;

/**
 * Skyline：includes the coordinates of the left endpoint of the skyline and the length of the skyline line segment
 */
public class SkyLine implements Comparable<SkyLine> {

    //The x-coordinate of the left endpoint of the skyline
    private double x;
    //The y-coordinate of the left endpoint of the skyline
    private double y;
    //Skyline length
    private double len;

    // Constructor
    public SkyLine(double x, double y, double len) {
        this.x = x;
        this.y = y;
        this.len = len;
    }

    // The skyline collation rules, the smaller the y, the more preferred, and when y is the same, the smaller the x, the more preferred
    @Override
    public int compareTo(SkyLine o) {
        int c1 = Double.compare(y, o.y);
        return c1 == 0 ? Double.compare(x, o.x) : c1;
    }

    // Rewrite the ToString method for easy printing to view the skyline
    @Override
    public String toString() {
        return "SkyLine{" +
                "x=" + x +
                ", y=" + y +
                ", len=" + len +
                '}';
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

    public double getLen() {
        return len;
    }

    public void setLen(double len) {
        this.len = len;
    }

}

