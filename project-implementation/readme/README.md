<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">A hybrid algorithms to solve the 2DRP</h1>
## Introduction to the project

This project uses four algorithms: the Skyline Heuristic, the Improved Skyline Heuristic, the Adaptive Genetic Algorithm and the Skyline Adaptive Genetic Hybrid Algorithm to solve the two-dimensional rectangular packing problem. The results of the test on the selected datasets show the time solver and space utilization of the four algorithms.

## Project preparation

### Environment preparation

```text
JDK >= 1.8 
Mysql >= 5.7.0 
Maven >= 3.6
IDEA >= 2020.3
```

### Run the project

1、Go to GitHub download page and download and extract it to the working directory

2、Import to`IDEA ` ，select the menu  `File` -> `Open`，to import the project.

3、Enter the com.model.skyline.run class, right-click and select run "run. main" to run

## Project structure

```text
com     
├ – data		 // Test data including N instances, C instances and some self-determined random data
├ – entity 		// instance objects
├ - Model 		// Algorithm implementation
│ └──Others		// Implementation of AGA (the adaptive genetic algorithm) and SAGH (skyline adaptive genetic hybrid algorithm)
│ └—skyline		 // Implementation of skyline heuristic and the improved Skyline heuristic
├ ─ util 		// Tools for reading data
```

## Project description

### data

```text


The format requirements for the randomdata.txt are as follows:
Width of the rectangular bin Height of the rectangular bin Whether rotation is allowed (1 is allowed if allowed, 0 is written otherwise)
Rectangular item width Rectangular item height
Rectangular item width Rectangular item height
Rectangular item width Rectangular item height
Rectangular item width Rectangular item height
...
Rectangular item width Rectangular item height
Note: You can modify the test data by modifying the value of the randomdata .txt
```

### entity

-  Instance
   - Instance objects with information about the entire problem (including boundary dimensions, the rectangular item collection, etc.)
-  Item
   - Rectangular items, including the width, height, and name of the item
-  PlaceItem
   - Placed rectangle items, including coordinate information, whether it rotates, width and height information of the placed rectangular item

-  SkyLine
   - Skyline objects, including the coordinates of the left endpoint of the skyline, length information for skyline segments
-  Solution
   - Result objects, including list of placed rectangles, total area of placed rectangles, space utilization information

### model

- Others
  - SAGH
    - The Skyline Adaptive Genetic Hybrid Algoprithm to solve two-dimensional rectangular packing problems
  - AGA
    - The Adaptive Genetic Algorithm to solve two-dimensional rectangular packing problems
- Skyline
  - Skyline Heuristic and the improved Skyline Heuristic to solve two-dimensional rectangular packing problems

### util

- ReadDataUtil

  - Read Data tool class

    

## Project demo

![](IMG\Snipaste_2023-01-09_09-48-35.png)



![image-20221203204631184](IMG\image-20221203204631184.png)



## Project error resolution

**java: Invalid target distribution: 11**

- **step 1**

![](IMG\image-20221204174626803.png)

![image-20221204174726570](IMG\image-20221204174726570.png)

- **Step 2**

![image-20221204174820080](IMG\image-20221204174820080.png)

![image-20221204175048358](IMG\image-20221204175048358.png)

![image-20221204175122777](IMG\image-20221204175122777.png)

