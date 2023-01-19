package com.entity;

/**
 * Result item: Includes list of placed rectangular items, total area of placed rectangles, space utilization information
 */

import java.util.List;

public class Solution {

    //The rectangular item is placed
    private List<PlaceItem> placeItemList;
   //Place the total area
    private double totalS;
    //utilization rate
    private double rate;

    //Constructor
    public Solution(List<PlaceItem> placeItemList, double totalS, double rate) {
        this.placeItemList = placeItemList;
        this.totalS = totalS;
        this.rate = rate;
    }

    public List<PlaceItem> getPlaceItemList() {
        return placeItemList;
    }

    public void setPlaceItemList(List<PlaceItem> placeItemList) {
        this.placeItemList = placeItemList;
    }

    public double getTotalS() {
        return totalS;
    }

    public void setTotalS(double totalS) {
        this.totalS = totalS;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

