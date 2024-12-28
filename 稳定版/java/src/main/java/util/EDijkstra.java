package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class EDijkstra {
    static final int INF = 99999999; // Define infinity to represent unreachable nodes
    static double[][] dis; // Distance matrix to store the graph's adjacency matrix (distance between nodes)

    static int[] prev; // Array to track the predecessor of each node in the shortest path
    static List<Integer> list = new ArrayList<>(); // List to store the final path from start to end

    /**
     * Recursive method to reconstruct the shortest path from the start node to the end node.
     * This method traces back from the end node to the start node using the `prev` array
     * and adds the nodes to the path list.
     * 
     * @param start The starting node (1-based index)
     * @param end   The ending node (1-based index)
     */
    public static void getPath(int start, int end) {
        // If the start is not the same as the end, recursively trace back
        if (start != end) {
            getPath(start, prev[end]);
        }
        // Add the current node to the path list (1-based index)
        list.add(end + 1);
    }

    /**
     * Performs the Dijkstra algorithm to find the shortest path from `start` to `end`.
     * 
     * @param start The starting node (1-based index)
     * @param end   The ending node (1-based index)
     * @return A list of nodes representing the shortest path from `start` to `end`
     */
    public static List<Integer> dijkstra(int start, int end) {
        int vertex = 556; // Total number of nodes (vertices) in the graph
        WholeFloorReadSheet3 readSheet = new WholeFloorReadSheet3(); // Helper class to read the graph data
        dis = readSheet.read(); // Read the graph data (distance matrix)

        // Initialize distance array (dist) and predecessor array (prev)
        double[] dist = new double[vertex];
        prev = new int[vertex];
        boolean[] visited = new boolean[vertex]; // Track if a node has been visited

        Arrays.fill(dist, INF); // Set all distances to infinity initially
        Arrays.fill(prev, -1); // Set all predecessors to -1 (indicating no predecessor)
        dist[start - 1] = 0; // The distance from the start node to itself is 0

        // Priority queue to store nodes by their distance (min-heap)
        // Nodes with smaller distances will be processed first
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(a.dist, b.dist));
        pq.offer(new Node(start - 1, 0)); // Add the start node with distance 0 to the queue

        // Main loop of the Dijkstra algorithm
        while (!pq.isEmpty()) {
            Node current = pq.poll(); // Get the node with the smallest distance
            int u = current.id;

            if (visited[u]) continue; // Skip if this node has already been processed
            visited[u] = true; // Mark the node as visited

            // Traverse all neighbors of the current node
            for (int v = 0; v < vertex; v++) {
                if (!visited[v] && dis[u][v] != INF) { // Only process unvisited nodes and reachable neighbors
                    double newDist = dist[u] + dis[u][v]; // Calculate the new potential distance to v
                    if (newDist < dist[v]) { // If the new distance is smaller, update the distance and predecessor
                        dist[v] = newDist;
                        prev[v] = u; // Update the predecessor of node v to be u
                        pq.offer(new Node(v, dist[v])); // Add the updated node to the priority queue
                    }
                }
            }
        }

        // Clear the previous path list before reconstructing the path
        list.clear();
        getPath(start - 1, end - 1); // Reconstruct the path from start to end
        return list; // Return the path as a list of nodes
    }

    /**
     * Helper class to represent a node in the graph with its associated distance.
     * This class is used in the priority queue to store nodes and their distances.
     */
    static class Node {
        int id;    // ID of the node (0-based index)
        double dist; // Distance from the source node

        // Constructor to initialize the node with its ID and distance
        Node(int id, double dist) {
            this.id = id;
            this.dist = dist;
        }
    }
}
