package com.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * * Rectangle object: Includes the width, height, and name of the rectangular items
 */
public class Item {

    // name
    private String name;
    // width of items
    private double w;
    // height
    private double h;

    // Constructor
    public Item(String name, double w, double h) {
        this.name = name;
        this.w = w;
        this.h = h;
    }

    // Copy a single Item
    public static Item copy(Item item) {
        return new Item(item.name, item.w, item.h);
    }

    //
    public static Item[] copy(Item[] items) {
        Item[] newItems = new Item[items.length];
        for (int i = 0; i < items.length; i++) {
            newItems[i] = copy(items[i]);
        }
        return newItems;
    }

    //Copy the Item array
    public static List<Item> copy(List<Item> items) {
        List<Item> newItems = new ArrayList<>();
        for (Item item : items) {
            newItems.add(copy(item));
        }
        return newItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

