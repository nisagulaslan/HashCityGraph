# 🌐 Graph Modeling with Hashing 
> 🗓️ Created during my **Second Year** of university.

## 📌 Project Overview
This project models a weighted graph using city names and implements custom hashing techniques to manage vertex indexing. All operations are handled via **custom data structures and algorithms**, avoiding the use of Java’s built-in graph-related libraries.

> 🔒 Note:
> Implemented as a **two-person collaborative assignment**.
> Usage of known algorithms (like Dijkstra) or Java’s built-in graph libraries was forbidden.

---

## 🔧 Core Concepts

- Graph is built from a custom-formatted file: `graph.txt`
- Cities (vertices) are strings, mapped to integers via a **custom hash table**
- Edge weights represent fictional distances
- Collision handling is required in the hash table
- The graph is represented via an **adjacency matrix**
- All traversal and search algorithms are implemented manually

---

## 📂 File Format Details

Each row defines the connections for a city, using this format:

```
CityA -> CityB:3,CityC:5,...
```

- `CityA` is the source
- `CityB:3` means an edge from CityA to CityB with weight 3

---

## 🧠 Implemented Methods

- `ReadGraphFromFile()`: Parses the input file and builds the hash table and adjacency matrix.
- `IsThereAPath(String v1, String v2)`: Checks if a path exists between two cities.
- `BFSfromTo(String v1, String v2)`: Performs BFS and prints the path and edge weights from v1 to v2.
- `DFSfromTo(String v1, String v2)`: Performs DFS and prints the path and edge weights.
- `WhatIsShortestPathLength(String v1, String v2)`: Returns shortest path length from v1 to v2 (custom logic only).
- `NumberOfSimplePaths(String v1, String v2)`: Counts number of simple (no repeated vertex) paths.
- `Neighbors(String v1)`: Lists neighboring vertices.
- `HighestDegree()`: Returns the name(s) of vertex/vertices with the highest degree.
- `IsDirected()`: Checks if the graph is directed.
- `AreTheyAdjacent(String v1, String v2)`: Checks if two cities are directly connected.
- `IsThereACycle(String v1)`: Detects if there’s a cycle that starts and ends at v1.
- `NumberOfVerticesInComponent(String v1)`: Returns size of the connected component of v1.

---

## 🧾 Hashing Requirements

- Create a custom hash table to store and map city names to vertex indices.
- Input names are read from the graph file.
- Provide constant-time search and insertion (with collision handling).
- Must NOT use linear search.

---

## 🖥️ User Interface

The program features a **menu-driven main method** where:

- Users can call any implemented method
- Inputs are taken interactively
- Results are printed clearly to the console

