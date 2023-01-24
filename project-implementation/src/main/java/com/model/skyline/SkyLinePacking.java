package com.model.skyline;

import com.entity.Item;
import com.entity.PlaceItem;
import com.entity.SkyLine;
import com.entity.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * The skyline heuristic solves the two-dimensional rectangular packing problem
 */
public class SkyLinePacking {

   // The width of the bin
    private double W;
   // The height of the bin
    private double H;
    //A rectangular items array
    private Item[] items;
    //Is it rotatable or not
    private boolean isRotateEnable;
    //Heap-optimized skyline priority queue (PriorityBlockingQueue) is thread-safe, and the bottom layer is implemented based on the minimum binary heap, with efficient dynamic sorting capabilities
    private PriorityBlockingQueue<SkyLine> skyLineQueue = new PriorityBlockingQueue<>();

    /**
     * @param isRotateEnable whether rectangle item rotation is allowed
     * @param W bin width
     * @param H bin height
     * @param items rectangular items collection
     * @Description constructor
     */
    public SkyLinePacking(boolean isRotateEnable, double W, double H, Item[] items) {
        this.isRotateEnable = isRotateEnable;
        this.W = W;
        this.H = H;
        this.items = items;
    }

    /**
     * @return List of placed rectangular items
     * @Description Skyline heuristic bin packing main function
     */
    public Solution packing() {

       // Used to store rectangle items that have been placed
        List<PlaceItem> placeItemList = new ArrayList<>();
        //Used to record the total area of the rectangle items that have been placed
        double totalS = 0d;

       // Get the initial skyline (bottom of bin)
        skyLineQueue.add(new SkyLine(0, 0, W));

        //Record the rectangle item that has been placed
        boolean[] used = new boolean[items.length];

        // Start the skyline heuristic iteration
        while (!skyLineQueue.isEmpty() && placeItemList.size() < items.length) {
            //Get the current bottommost leftmost skyline (take out the leader)
            SkyLine skyLine = skyLineQueue.poll();
           // Initialize HL and HR
            double hl = H - skyLine.getY();
            double hr = H - skyLine.getY();
            //Jump out of the counter in advance (if HL and HR get it, you can jump out in advance to save time)
            int c = 0;
          //  Traverse the skyline queue sequentially, getting HL and HR based on Skyline and Skyline queues
            for (SkyLine line : skyLineQueue) {
                //Since skyLine is the leaderboard element, its Y must be the smallest, so line.getY() - skyLine.getY() must both be greater than or equal to 0
                if (compareDouble(line.getX() + line.getLen(), skyLine.getX()) == 0) {
                    //The tail head is connected and is HL
                    hl = line.getY() - skyLine.getY();
                    c++;
                } else if (compareDouble(line.getX(), skyLine.getX() + skyLine.getLen()) == 0) {
                   // Head to end, is HR
                    hr = line.getY() - skyLine.getY();
                    c++;
                }
                // HL and HR have been obtained, there is no need to continue to traverse, you can jump out in advance to save time
                if (c == 2) {
                    break;
                }
            }
           // The index of the largest scoring rectangle item that records
            int maxItemIndex = -1;
            //Records whether the rectangle item with the maximum score is rotated
            boolean isRotate = false;
            //Record the maximum rating
            int maxScore = -1;
            //Iterate through each rectangle and choose the one with the highest score to place it
            for (int i = 0; i < items.length; i++) {
               // The rectangle item has not been placed before proceeding with the rest of the process
                if (!used[i]) {
                    //Cases that do not rotate
                    int score = score(items[i].getW(), items[i].getH(), skyLine, hl, hr);
                    if (score > maxScore) {
                        // Update the maximum rating
                        maxScore = score;
                        maxItemIndex = i;
                        isRotate = false;
                    }
                    // Rotation case (the rectangle item width and height swapped)
                    if (isRotateEnable) {
                        int rotateScore = score(items[i].getH(), items[i].getW(), skyLine, hl, hr);
                        if (rotateScore > maxScore) {
                            // Update the maximum rating
                            maxScore = rotateScore;
                            maxItemIndex = i;
                            isRotate = true;
                        }
                    }
                }
            }
            //If the current maximum score is greater than or equal to 0, there is a rectangle item to place, and it is placed according to the rules
            if (maxScore >= 0) {
                //The left wall is higher than or equal to the right wall
                if (hl >= hr) {
                    //When the score is 2, the rectangle item is placed to the right of the skyline, otherwise it is placed to the left of the skyline
                    if (maxScore == 2) {
                        placeItemList.add(placeRight(items[maxItemIndex], skyLine, isRotate));
                    } else {
                        placeItemList.add(placeLeft(items[maxItemIndex], skyLine, isRotate));
                    }
                } else {
                    //The left wall is lower than the right wall
                    //On a rating of 4 or 0, the rectangle item is placed to the right of the skyline, otherwise it is placed to the left of the skyline
                    if (maxScore == 4 || maxScore == 0) {
                        placeItemList.add(placeRight(items[maxItemIndex], skyLine, isRotate));
                    } else {
                        placeItemList.add(placeLeft(items[maxItemIndex], skyLine, isRotate));
                    }
                }
                //Set the rectangle item to the rectangle that has already been placed based on the index
                used[maxItemIndex] = true;
                //Append the rectangular area to totalS
                totalS += (items[maxItemIndex].getW() * items[maxItemIndex].getH());
            } else {
               // If the current skyline doesn't fit a rectangle, move the skyline up and merge with the rest of the skyline
                combineSkylines(skyLine);
            }
        }
        // Returns the solution result
        return new Solution(placeItemList, totalS, totalS / (W * H));
    }


    /**
     *  item The rectangle item object to place
     *  skyLine where the skyLine rectangle item is placed
     *  whether the isRotate rectangle is rotated
     * @return Returns a PlaceItem object
     * @Description Place the rectangle item to the left
     */
    private PlaceItem placeLeft(Item item, SkyLine skyLine, boolean isRotate) {
        // Generate a PlaceItem object
        PlaceItem placeItem = null;
        if (!isRotate) {
            placeItem = new PlaceItem(item.getName(), skyLine.getX(), skyLine.getY(), item.getW(), item.getH(), isRotate);
        } else {
            placeItem = new PlaceItem(item.getName(), skyLine.getX(), skyLine.getY(), item.getH(), item.getW(), isRotate);
        }
        // Add the new skyline to the queue
        addSkyLineInQueue(skyLine.getX(), skyLine.getY() + placeItem.getH(), placeItem.getW());
        addSkyLineInQueue(skyLine.getX() + placeItem.getW(), skyLine.getY(), skyLine.getLen() - placeItem.getW());
        // Returns a PlaceItem object
        return placeItem;
    }

    /**
     *  item The rectangle item to place
     *  skyline where the skyLine rectangle item is placed
     *  whether the isRotate rectangle item is rotated
     * @return Returns a PlaceItem object
     * @Description Place the rectangle item to the right
     */
    private PlaceItem placeRight(Item item, SkyLine skyLine, boolean isRotate) {
        // Generate a PlaceItem object
        PlaceItem placeItem = null;
        if (!isRotate) {
            placeItem = new PlaceItem(item.getName(), skyLine.getX() + skyLine.getLen() - item.getW(), skyLine.getY(), item.getW(), item.getH(), isRotate);
        } else {
            placeItem = new PlaceItem(item.getName(), skyLine.getX() + skyLine.getLen() - item.getH(), skyLine.getY(), item.getH(), item.getW(), isRotate);
        }
        // Add the new skyline to the queue
        addSkyLineInQueue(skyLine.getX(), skyLine.getY(), skyLine.getLen() - placeItem.getW());
        addSkyLineInQueue(placeItem.getX(), skyLine.getY() + placeItem.getH(), placeItem.getW());
        // Returns a PlaceItem object
        return placeItem;
    }

    /**
     * @param x new skyline x coordinates
     * @param y new skyline y coordinates
     * @param len new skyline length
     * @return void
     * @Description Adds the skyline of the specified attribute to the skyline queue
     */
    private void addSkyLineInQueue(double x, double y, double len) {
        // The new skyline is added only if the length is greater than 0
        if (compareDouble(len, 0.0) == 1) {
            skyLineQueue.add(new SkyLine(x, y, len));
        }
    }

    /**
     * @param skyLine A skyline that cannot fit an arbitrary rectangle
     * @return void
     * @Description Pass in a skyline that can fit no less than an arbitrary rectangle item, move it up, and merge with other skylines
     */
    private void combineSkylines(SkyLine skyLine) {
        boolean b = false;
        for (SkyLine line : skyLineQueue) {
            if (compareDouble(skyLine.getY(), line.getY()) != 1) {
                // Head to end
                if (compareDouble(skyLine.getX(), line.getX() + line.getLen()) == 0) {
                    skyLineQueue.remove(line);
                    b = true;
                    skyLine.setX(line.getX());
                    skyLine.setY(line.getY());
                    skyLine.setLen(line.getLen() + skyLine.getLen());
                    break;
                }
                // The tails are connected
                if (compareDouble(skyLine.getX() + skyLine.getLen(), line.getX()) == 0) {
                    skyLineQueue.remove(line);
                    b = true;
                    skyLine.setY(line.getY());
                    skyLine.setLen(line.getLen() + skyLine.getLen());
                    break;
                }
            }
        }
        // Join only if there is a merge
        if (b) {
            // Add the last merged skyline to the skyline queue
            skyLineQueue.add(skyLine);
        }
    }

    /**
     * @param w the width of the rectangle item to be placed
     * @param h The height of the rectangle item currently to be placed
     * @param skyLine The skyline object
     * @param hl The left wall of this skyline
     * @param hr The right wall of this skyline
     * @return Rating of rectangular blocks, if rated -1, the rectangle item cannot be placed on the skyline
     * @Description Score rectangles
     */
    private int score(double w, double h, SkyLine skyLine, double hl, double hr) {
        // The current skyline length is less than the current rectangle item's width and cannot be placed
        if (compareDouble(skyLine.getLen(), w) == -1) {
            return -1;
        }
        // If it exceeds the upper bound, it cannot be put either
        if (compareDouble(skyLine.getY() + h, H) == 1) {
            return -1;
        }
        int score = -1;
        // The left wall is higher than or equal to the right wall
        if (hl >= hr) {
            if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hl) == 0) {
                score = 7;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hr) == 0) {
                score = 6;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hl) == 1) {
                score = 5;
            } else if (compareDouble(w, skyLine.getLen()) == -1 && compareDouble(h, hl) == 0) {
                score = 4;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hl) == -1 && compareDouble(h, hr) == 1) {
                score = 3;
            } else if (compareDouble(w, skyLine.getLen()) == -1 && compareDouble(h, hr) == 0) {
                //  Keep to the right
                score = 2;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hr) == -1) {
                score = 1;
            } else if (compareDouble(w, skyLine.getLen()) == -1 && compareDouble(h, hl) != 0) {
                score = 0;
            } else {
                throw new RuntimeException("w = " + w + " , h = " + h + " , hl = " + hl + " , hr = " + hr + " , skyline = " + skyLine);
            }
        } else {
            if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hr) == 0) {
                score = 7;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hl) == 0) {
                score = 6;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hr) == 1) {
                score = 5;
            } else if (compareDouble(w, skyLine.getLen()) == -1 && compareDouble(h, hr) == 0) {
                //  Keep to the right
                score = 4;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hr) == -1 && compareDouble(h, hl) == 1) {
                score = 3;
            } else if (compareDouble(w, skyLine.getLen()) == -1 && compareDouble(h, hl) == 0) {
                score = 2;
            } else if (compareDouble(w, skyLine.getLen()) == 0 && compareDouble(h, hl) == -1) {
                score = 1;
            } else if (compareDouble(w, skyLine.getLen()) == -1 && compareDouble(h, hr) != 0) {
                // Keep to the right
                score = 0;
            } else {
                throw new RuntimeException("w = " + w + " , h = " + h + " , hl = " + hl + " , hr = " + hr + " , skyline = " + skyLine);
            }
        }
        return score;
    }

    /**
     * @param d1 double-precision floating-point variable 1
     * @param d2 double-precision floating-point variable 2
     * @return Return 0 means that two numbers are equal, return 1 means that the former is greater than the latter, and return -1 means that the former is less than the latter,
     * @Description Determine the size relationship between two double-precision floating-point variables
     */
    private int compareDouble(double d1, double d2) {
        //Defines a margin of error where two numbers are considered equal if they differ less than this error 1e-06 = 0.000001
        double error = 1e-06;
        if (Math.abs(d1 - d2) < error) {
            return 0;
        } else if (d1 < d2) {
            return -1;
        } else if (d1 > d2) {
            return 1;
        } else {
            throw new RuntimeException("d1 = " + d1 + " , d2 = " + d2);
        }
    }

}
