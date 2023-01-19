# the-SAGH-for-solving-2DRP
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">A hybrid algorithm to solve the 2DRP</h1>


## Introduction to the project

This project is used to realize the implementation of heap optimization skyline heuristic algorithm, combined skyline heuristic genetic algorithm, and skyline adaptive genetic algorithm to solve two-dimensional rectangular binning problem。

## Project preparation

### Environment preparation

```text
JDK >= 1.8 
Mysql >= 5.7.0 
Maven >= 3.6
IDEA >= 2020.3
```

### Run the project

1、Go to the Github download page and download and extract it to the working directory

2、Import to`IDEA ` ，select the menu  `File` -> `Open`，to import the project.

3、进入com.model.skyline.run类中，点击右键选择run "run.main"即可运行

## Project structure

```text
com     
├ – data		 // Test data
├ – entity 		// instance object
├ - Model 		// Algorithm implementation
│ └── ga 		// Implementation of the skyline heuristic genetic algorithm and the skyline adaptive genetic algorithm
│ └—skyline		 // Implementation of skyline heuristic algorithm based on heap optimization
├ ─ util 		// Tools for reading data
```

## Project description

### data

```text


The format requirements for the data .txt are as follows:
Width of the border Height of the border Whether rotation is allowed (1 is allowed if allowed, 0 is written otherwise)
Rectangle width Rectangle height
Rectangle width Rectangle height
Rectangle width Rectangle height
Rectangle width Rectangle height
...
Rectangle width Rectangle height
Note: You can modify the test data by modifying the value of the data .txt
```

### entity

-  Instance
   - Instance object with information about the entire problem (including boundary dimensions, rectangular collections, etc.)
-  Item
   - Rectangle objects, including the width, height, and name of the rectangle
-  PlaceItem
   - Placed rectangle object, including coordinate information, whether it rotates, width and height information of the placed rectangle

-  SkyLine
   - Skyline objects, including the coordinates of the left endpoint of the skyline, length information for skyline segments
-  Solution
   - Result object, including list of placed rectangles, total area of placed rectangles, load utilization information

### model

- ga
  - aga
    - Adaptive genetic algorithm combined with skyline heuristic algorithm to solve two-dimensional rectangular boxing problems
  - ga
    - Genetic algorithm combined with skyline heuristic algorithm to solve two-dimensional rectangular boxing problem
- skyline
  - Skyline heuristic solves two-dimensional rectangular boxing problems

### util

- ReadDataUtil

  - Read Data tool class

    

## Project demo

![](C:\Users\empathyzz\AppData\Roaming\Typora\typora-user-images\image-20221204000644225.png)

![image-20221203204631184](C:\Users\empathyzz\AppData\Roaming\Typora\typora-user-images\image-20221203204631184.png)



## Project error resolution

**java: Invalid target distribution: 11**

- **step 1**

![](\IMG\image-20221204174626803.png)

![image-20221204174726570](\IMG\image-20221204174726570.png)

- **Step 2**

![image-20221204174820080](\IMG\image-20221204174820080.png)

![image-20221204175048358](\IMG\image-20221204175048358.png)

![image-20221204175122777](\IMG\image-20221204175122777.png)

