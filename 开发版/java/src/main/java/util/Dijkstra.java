package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A utility class for finding the shortest path between nodes using Dijkstra's algorithm.
 */
public class Dijkstra {

    private static final int INF = 99999999; // Represents an unreachable state
    private static double[][] distanceMatrix; // Adjacency matrix of distances
    private static int[] predecessors; // Records the predecessor node in the shortest path
    private static final List<Integer> path = new ArrayList<>(); // Stores the final path

    /**
     * Builds the path from the start node to the end node using the predecessors array.
     *
     * @param start the start node index (0-based).
     * @param end   the end node index (0-based).
     */
    private static void buildPath(int start, int end) {
        if (start != end) {
            buildPath(start, predecessors[end]);
        }
        path.add(end + 1); // Add the node to the path (1-based index for clarity)
    }

    /**
     * Computes the shortest path between two nodes using Dijkstra's algorithm.
     *
     * @param start the start node index (1-based).
     * @param end   the end node index (1-based).
     * @return a list of nodes representing the shortest path (1-based indices).
     */
    public static List<Integer> findShortestPath(int start, int end) {
        int vertexCount = 54; // Total number of vertices
        ReadSheet5 readSheet5 = new ReadSheet5();
        distanceMatrix = readSheet5.read();

        // Initialize distances and predecessors
        double[] distances = new double[vertexCount];
        predecessors = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];

        Arrays.fill(distances, INF); // Set all distances to infinity
        Arrays.fill(predecessors, -1); // Set all predecessors to -1
        distances[start - 1] = 0; // Distance to the start node is 0

        // Priority queue for unvisited nodes, sorted by distance
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(a.distance, b.distance));
        pq.offer(new Node(start - 1, 0));

        // Main Dijkstra loop
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentNode = current.id;

            if (visited[currentNode]) continue; // Skip if already visited
            visited[currentNode] = true;

            // Update distances for neighboring nodes
            for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
                if (!visited[neighbor] && distanceMatrix[currentNode][neighbor] != INF) {
                    double newDistance = distances[currentNode] + distanceMatrix[currentNode][neighbor];
                    if (newDistance < distances[neighbor]) {
                        distances[neighbor] = newDistance; // Update distance
                        predecessors[neighbor] = currentNode; // Update predecessor
                        pq.offer(new Node(neighbor, distances[neighbor])); // Add to queue
                    }
                }
            }
        }

        // Build the path from start to end
        path.clear();
        buildPath(start - 1, end - 1);
        return path;
    }

    /**
     * A helper class to represent a node and its distance in the priority queue.
     */
    private static class Node {
        int id;
        double distance;

        Node(int id, double distance) {
            this.id = id;
            this.distance = distance;
        }
    }
}
