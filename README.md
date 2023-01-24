<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">A hybrid algorithm to solve the 2DRP</h1>
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

[Output screenshot](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/d4e8ec931a6a691c6c5249fe4b4b0900f09bb8c8/project-implementation/readme/IMG/Output%20screenshot.jpg)



[Visualization example](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/b45546a994a07e74e3d4d9503e12933e2f48854c/project-implementation/readme/IMG/visualization%20example.png)



## Project error resolution

**java: Invalid target distribution: 11**

- **step 1**

[Open Setting (preferences for mac)](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/2991b3040e50c8ea3fb0fcb3315412fc4484d489/project-implementation/readme/IMG/Open%20Setting.png)

[JavaCompiler Setting](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/010725656615bb5729277d0971e49e18a0ad34e6/project-implementation/readme/IMG/JavaCompiler%20Setting.png)

- **Step 2**

[Project Structure](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/2991b3040e50c8ea3fb0fcb3315412fc4484d489/project-implementation/readme/IMG/Project%20Structure.png)

[Project Setting](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/2991b3040e50c8ea3fb0fcb3315412fc4484d489/project-implementation/readme/IMG/Project%20Setting.png)

[Modules Setting](https://github.com/JoeTsui925/the-SAGH-for-solving-2DRP/blob/2991b3040e50c8ea3fb0fcb3315412fc4484d489/project-implementation/readme/IMG/Modules%20Setting.png)

