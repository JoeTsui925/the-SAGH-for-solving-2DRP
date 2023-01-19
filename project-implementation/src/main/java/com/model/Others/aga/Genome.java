package com.model.Others.aga;

import com.entity.Item;
import com.entity.Solution;
import com.model.skyline.SkyLinePacking;

public class Genome {

    //The width of the bin
    private double W;
    //the height of the bin
    private double H;
    // A rectangle items array
    private Item[] items;
    //Is it rotatable or not
    private boolean isRotateEnable;

    //Gene sequence
    private int[] genomeArray;
    //Fitness function value (in the case of a two-dimensional packing problem, it is actually the space utilization)
    private double fitness;
    //The loading result corresponding to the sequence
    private Solution solution;

    /**
     * @param w bin width
     * @param h bin height
     * @param items rectangle items collection
     * @param isRotateEnable is rotatable
     * @param genomeArray gene sequence
     * @Description constructors of gene objects
     */
    public Genome(double w, double h, Item[] items, boolean isRotateEnable, int[] genomeArray) {
        W = w;
        H = h;
        this.items = items;
        this.isRotateEnable = isRotateEnable;
        this.genomeArray = genomeArray;
    }

    /**
     * @Description Get the adaptation value and path length
     */
    public void updateFitnessAndSolution() {
        Item[] items = new Item[this.items.length];
        for (int i = 0; i < genomeArray.length; i++) {
            items[i] = this.items[genomeArray[i]];
        }
        solution = new SkyLinePacking(isRotateEnable, W, H, items).packing();
        fitness = solution.getRate();
    }

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

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public boolean isRotateEnable() {
        return isRotateEnable;
    }

    public void setRotateEnable(boolean rotateEnable) {
        isRotateEnable = rotateEnable;
    }

    public int[] getGenomeArray() {
        return genomeArray;
    }

    public void setGenomeArray(int[] genomeArray) {
        this.genomeArray = genomeArray;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}

