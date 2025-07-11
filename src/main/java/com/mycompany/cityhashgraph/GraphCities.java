
package com.mycompany.cityhashgraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class GraphCities {
    private HashTable hashTable;
    private int[][] adjacencyMatrix; 

    public GraphCities() {
        hashTable = new HashTable();
        int initialSize = 700; 
        adjacencyMatrix = new int[initialSize][initialSize];
    }
    

    public void readGraphFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int currentIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" -> ");
                String vertex = parts[0].trim();
                hashTable.insert(vertex);
                
                String[] edges = parts[1].split(", ");
                for (String edge : edges) {
                    String[] edgeParts = edge.split(":");

                    String neighbor = edgeParts[0].trim();
                    int weight = Integer.parseInt(edgeParts[1].trim());
                    hashTable.insert(neighbor);

                    int row = hashTable.search(vertex);
                    if (row == -1) {
                        row = currentIndex;
                        currentIndex++;
                    }
                    int col = hashTable.search(neighbor);
                    if (col == -1) {
                        col = currentIndex;
                        currentIndex++;
                    }

                    if (adjacencyMatrix == null) {
                        adjacencyMatrix = new int[currentIndex][currentIndex];
                    }
                    adjacencyMatrix[row][col] = weight;
                }
            }
            System.out.println("Successfully readed.");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    

    public boolean isThereAPath(String v1, String v2) {

        int start = hashTable.insert(v1);
        int end = hashTable.insert(v2);
        

        if (start == -1 || end == -1) {
            return false;
        }

        Stack stack = new Stack(adjacencyMatrix.length);
        boolean[] visited = new boolean[adjacencyMatrix.length];


        stack.push(start);
        while (!stack.isEmpty()) {

            int current = stack.peek();
            if (current == end) {
                System.out.println("Path found: " + getPath(stack));
                return true;
            }

            boolean foundUnvisitedNeighbor = false;
            for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
                if (adjacencyMatrix[current][neighbor] != 0 && !visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;
                    foundUnvisitedNeighbor = true;
                    System.out.println("Pushed: " + neighbor);
                    break;
                }
            }
            

            if (!foundUnvisitedNeighbor) {
                stack.pop();
                System.out.println("Popped");
            }
        }

        return false;
    }

    private String getPath(Stack stack) {
        StringBuilder path = new StringBuilder();

        for (int i = 0; i <= stack.top; i++) {
            path.append(stack.array[i]).append(" ");
        }

        return path.toString();
    }
    
    public void BFSfromTo(String v1, String v2) {
        int start = hashTable.search(v1);
        int end = hashTable.search(v2);

        if (start == -1 || end == -1) {
            throw new IllegalArgumentException("Vertices not found.");
        }

        boolean[] visited = new boolean[adjacencyMatrix.length];
        Queue<BFSNode> queue = new Queue<>();
        queue.enqueue(new BFSNode(start, v1));

        while (!queue.isEmpty()) {
            BFSNode current = queue.dequeue();
            int currentVertex = current.vertex;
            String currentPath = current.path;
            if (currentVertex == end) {
                System.out.println("Sequence of vertices and edges: " + currentPath);
                return;
            }
            visited[currentVertex] = true;

            for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
                if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    String neighborKey = hashTable.searchKey(neighbor);
                    if (neighborKey != null) {
                        String edge = " -> " + neighborKey + ": " + adjacencyMatrix[currentVertex][neighbor];
                        queue.enqueue(new BFSNode(neighbor, currentPath + edge));
                        visited[neighbor] = true;
                    }
                }
            }
        }

        System.out.println("No path found between " + v1 + " and " + v2);
    }

    public void DFSfromTo(String v1, String v2) {
        int start = hashTable.search(v1);
        int end = hashTable.search(v2);

        if (start == -1 || end == -1) {
            System.out.println("Vertices not found.");
            return;
        }

        boolean[] visited = new boolean[adjacencyMatrix.length];

        System.out.println("Sequence of vertices and edges:");
        dfs(start, end, visited, v1);
    }

    private void dfs(int current, int end, boolean[] visited, String path) {
        System.out.println(path);

        if (current == end) {
            return;
        }

        visited[current] = true;

        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[current][neighbor] != 0 && !visited[neighbor]) {
                String edge = " -> " + hashTable.searchKey(neighbor) + ": " + adjacencyMatrix[current][neighbor];
                dfs(neighbor, end, visited, path + edge);
            }
        }
    }
    
    public int WhatIsShortestPathLength(String v1, String v2) {
        int start = hashTable.search(v1);
        int end = hashTable.search(v2);
        if (start == -1 || end == -1) {
            System.out.println("Vertices not found.");
            return -1;
        }
        boolean[] visited = new boolean[adjacencyMatrix.length];
        int[] distances = new int[adjacencyMatrix.length];
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(start);
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int currentVertex = queue.dequeue();
            for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {

                if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                    queue.enqueue(neighbor);
                    visited[neighbor] = true;
                    distances[neighbor] = distances[currentVertex] + 1;
 
                    if (neighbor == end) {
                        System.out.println(v1 + " --" + distances[end] + "-- " + v2);
                        return distances[end];
                    }
                }
            }
        }

        System.out.println(v1 + " --x-- " + v2);
        return -1;
    }
    

    public int NumberOfSimplePaths(String v1, String v2) {
        int start = hashTable.search(v1);
        int end = hashTable.search(v2);
        if (start == -1 || end == -1) {
            System.out.println("Vertices not found.");
            return 0;
        }
        boolean[] visited = new boolean[adjacencyMatrix.length];
        int[] pathCount = {0};
        dfsCountPaths(start, end, visited, pathCount);
        return pathCount[0];
    }

    private void dfsCountPaths(int currentVertex, int end, boolean[] visited, int[] pathCount) {
    visited[currentVertex] = true;

    if (currentVertex == end) {
        pathCount[0]++;
    } else {
        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                boolean[] newVisited = Arrays.copyOf(visited, visited.length);
                dfsCountPaths(neighbor, end, newVisited, pathCount);
            }
        }
    }
}


    public String Neighbors(String v1) {
        int vertexIndex = hashTable.search(v1);
        if (vertexIndex == -1) {
            return "Vertex not found.";
        }

        StringBuilder result = new StringBuilder("Names of the neighbors are: ");
        boolean firstNeighbor = true;
        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[vertexIndex][neighbor] != 0) {
                String neighborName = hashTable.searchKey(neighbor);
                if (!firstNeighbor) {
                    result.append(", ");
                }
                result.append(neighborName);
                firstNeighbor = false;
            }
        }
        return result.toString();
    }
    

    public void HighestDegree() {
        int maxDegree = 0;
        List<String> verticesWithMaxDegree = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int degree = calculateDegree(i);
            if (degree > maxDegree) {
                maxDegree = degree;
                verticesWithMaxDegree.clear();
                verticesWithMaxDegree.add(hashTable.searchKey(i));
            } else if (degree == maxDegree) {
                verticesWithMaxDegree.add(hashTable.searchKey(i));
            }
        }

        if (!verticesWithMaxDegree.isEmpty()) {
            System.out.println("Highest degree is " + maxDegree + " for vertex/vertices: " + verticesWithMaxDegree);
        } else {
            System.out.println("No vertices found.");
        }
    }

    private int calculateDegree(int vertexIndex) {
        int degree = 0;

        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (adjacencyMatrix[j][vertexIndex] != 0) {
                degree++;
            }
        }

        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (adjacencyMatrix[vertexIndex][j] != 0) {
                degree++;
            }
        }
        return degree;
    }
    

    public boolean IsDirected() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != adjacencyMatrix[j][i]) {
                    return true;
                }
            }
        }

        return false;
    }
    

    public boolean AreTheyAdjacent(String v1, String v2) {
        int indexV1 = hashTable.search(v1);
        int indexV2 = hashTable.search(v2);
        if (indexV1 == -1 || indexV2 == -1) {
            System.out.println("One or both vertices not found.");
            return false;
        }

        return adjacencyMatrix[indexV1][indexV2] != 0;
    }
    

    public boolean IsThereACycle(String v1) {
        int start = hashTable.search(v1);
        if (start == -1) {
            System.out.println("Vertex not found.");
            return false;
        }

        boolean[] visited = new boolean[adjacencyMatrix.length];
        return dfsCheckCycle(start, visited);
    }

    private boolean dfsCheckCycle(int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;
        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[currentVertex][neighbor] != 0) {
                if (!visited[neighbor]) {
                    if (dfsCheckCycle(neighbor, visited)) {
                        return true;
                    }
                } else {

                    return true;
                }
            }
        }

        return false;
    }


    public int NumberOfVerticesInComponent(String v1) {
        int start = hashTable.search(v1);
        if (start == -1) {
            System.out.println("Vertex not found.");
            return 0;
        }
        boolean[] visited = new boolean[adjacencyMatrix.length];

        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
        return dfsCountVerticesInComponent(start, visited);
    }

    private int dfsCountVerticesInComponent(int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;
        int count = 1;
        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[currentVertex][neighbor] != 0 && !visited[neighbor]) {
                count += dfsCountVerticesInComponent(neighbor, visited);
            }
        }
        return count;
    }

    //------------------------------------------------------------------
    public static void main(String[] args) {
        GraphCities graphCities = new GraphCities();
        Scanner scn = new Scanner(System.in);
        boolean key = true;
        while (key) {
            System.out.println("**********************");
            System.out.println("** Select an option **");
            System.out.println("**********************");
            menu();
            int option = scn.nextInt();
            switch (option) {
                case 1:
                    graphCities.readGraphFromFile("graph.txt");
                    break;
                case 2:
                    System.out.println("Please enter two city names ");
                    scn.nextLine();
                    String v1 = scn.nextLine().trim();
                    String v2 = scn.nextLine().trim();
                    System.out.println("Is there a path: " + graphCities.isThereAPath(v1, v2));
                    break;
                case 3:
                    System.out.println("Please enter two city names ");
                    scn.nextLine();
                    String V1 = scn.nextLine().trim();
                    String V2 = scn.nextLine().trim();
                    graphCities.BFSfromTo(V1, V2);
                    break;
                case 4:
                    System.out.println("Please enter two city names ");
                    scn.nextLine();
                    String vx1 = scn.nextLine().trim();
                    String vx2 = scn.nextLine().trim();
                    graphCities.DFSfromTo(vx1, vx2);
                    break;
                case 5:
                    System.out.println("Please enter two city names ");
                    scn.nextLine();
                    String VX1 = scn.nextLine().trim();
                    String VX2 = scn.nextLine().trim();
                    System.out.println("Shortest path length is: " + graphCities.WhatIsShortestPathLength(VX1, VX2));
                    break;
                case 6:
                    System.out.println("Please enter two city names ");
                    scn.nextLine();
                    String vtx1 = scn.nextLine().trim();
                    String vtx2 = scn.nextLine().trim();
                    System.out.println("Number of simple paths is: " + graphCities.NumberOfSimplePaths(vtx1, vtx2));
                    break;
                case 7:
                    System.out.println("Enter a city name ");
                    scn.nextLine();
                    String v = scn.nextLine().trim();
                    System.out.println("Names of the neighbors are: " + graphCities.Neighbors(v));
                    break;
                case 8:
                    System.out.println(" Highest degree is ");
                    graphCities.HighestDegree();
                    break;
                case 9:
                    System.out.println("Is graph directed: " + graphCities.IsDirected());
                    break;
                case 10:
                    System.out.println("Please enter two city names ");
                    scn.nextLine();
                    String vetx1 = scn.nextLine().trim();
                    String vetx2 = scn.nextLine().trim();
                    System.out.println("Are they adjacent: " + graphCities.AreTheyAdjacent(vetx1, vetx2));
                    break;
                case 11:
                    System.out.println("Enter a city name ");
                    scn.nextLine();
                    String V = scn.nextLine().trim();
                    System.out.println("Is there a cycle: " + graphCities.IsThereACycle(V));
                    break;
                case 12:
                    System.out.println("Enter a city name ");
                    scn.nextLine();
                    String vx = scn.nextLine().trim();
                    System.out.println("Number of verticles in component is " + graphCities.NumberOfVerticesInComponent(vx));
                    break;
                default:
                    key = false;

            }
        }
    }
    public static void menu() {
        System.out.println("[1 : Read graph from file]");
        System.out.println("[2 : Is there a path]");
        System.out.println("[3 : BFS from to]");
        System.out.println("[4 : DFS from to]");
        System.out.println("[5 : What is the shortest path length]");
        System.out.println("[6 : Number of simple paths]");
        System.out.println("[7 : Names of the neighbors]");
        System.out.println("[8 : Highest degreed vertex]");
        System.out.println("[9 : Is graph directed]");
        System.out.println("[10 : Are they adjacent]");
        System.out.println("[11 : Is there a cycle]");
        System.out.println("[12 : Number of verticles in component]");
        System.out.println("[13 : Terminate the program]");
    }
    
    public int getVertexCount() {
        return hashTable.getVertexCount();
    }
}