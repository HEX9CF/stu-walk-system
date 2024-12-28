package util;

import pojo.Didian;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Simulated Annealing algorithm implementation for finding the shortest path.
 */
public class SimulatedAnnealing {

    private static double[][] distanceMatrix;

    /**
     * Finds the shortest path using Simulated Annealing.
     *
     * @param locations    the list of locations (including start and end).
     * @param randomNumber a random number used for file naming in drawing.
     * @return an array containing the optimal path and its total distance.
     */
    public static int[] findShortestPath(int[] locations, int randomNumber) {
        ReadSheet4 sheetReader = new ReadSheet4();
        distanceMatrix = sheetReader.read();

        int numLocations = locations.length;

        if (numLocations == 2) { // Only start and end locations
            List<Integer> directPath = Dijkstra.dijkstra(locations[0], locations[1]);
            plotPathAndDraw(directPath, randomNumber);

            int[] result = {locations[0], locations[1], 
                            (int) Math.round(distanceMatrix[locations[0] - 1][locations[1] - 1])};
            return result;
        }

        int start = locations[0];
        int end = locations[numLocations - 1];

        // Extract intermediate locations
        int[] intermediateLocations = Arrays.stream(locations)
                                             .filter(loc -> loc != start && loc != end)
                                             .toArray();

        // Simulated Annealing parameters
        final double INITIAL_TEMPERATURE = 1000.0;
        final int MAX_ITERATIONS = 1000;
        final int ITERATIONS_PER_TEMP = 500;
        final double TEMPERATURE_DECAY = 0.95;

        double temperature = INITIAL_TEMPERATURE;

        // Generate initial random path
        int[] currentPath = generateRandomPath(intermediateLocations);
        int[] bestPath = Arrays.copyOf(currentPath, currentPath.length);
        double currentDistance = calculateDistance(currentPath, start, end);
        double minDistance = currentDistance;

        Random random = new Random();

        // Simulated Annealing algorithm
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            for (int step = 0; step < ITERATIONS_PER_TEMP; step++) {
                int[] newPath = generateNeighborPath(currentPath);
                double newDistance = calculateDistance(newPath, start, end);

                if (newDistance < currentDistance) {
                    currentPath = newPath;
                    currentDistance = newDistance;
                } else {
                    double acceptanceProbability = Math.exp(-(newDistance - currentDistance) / temperature);
                    if (random.nextDouble() < acceptanceProbability) {
                        currentPath = newPath;
                        currentDistance = newDistance;
                    }
                }

                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    bestPath = Arrays.copyOf(currentPath, currentPath.length);
                }
            }

            temperature *= TEMPERATURE_DECAY; // Reduce temperature
        }

        // Construct the final path
        int[] optimalPath = new int[bestPath.length + 2];
        optimalPath[0] = start;
        System.arraycopy(bestPath, 0, optimalPath, 1, bestPath.length);
        optimalPath[optimalPath.length - 1] = end;

        List<Integer> finalPath = new ArrayList<>();
        for (int i = 0; i < optimalPath.length - 1; i++) {
            finalPath.addAll(Dijkstra.dijkstra(optimalPath[i], optimalPath[i + 1]));
        }

        plotPathAndDraw(finalPath, randomNumber);

        // Return the optimal path and its distance
        int[] result = Arrays.copyOf(optimalPath, optimalPath.length + 1);
        result[result.length - 1] = (int) Math.round(minDistance);
        return result;
    }

    /**
     * Plots the path and generates a visual representation.
     *
     * @param path         the list of locations in the path.
     * @param randomNumber a random number used for file naming.
     */
    private static void plotPathAndDraw(List<Integer> path, int randomNumber) {
        ReadSheet1 sheetReader = new ReadSheet1();
        List<Didian> locations = sheetReader.read();

        List<Point2D.Double> points = new ArrayList<>();
        for (Integer location : path) {
            Didian loc = locations.get(location - 1);
            points.add(new Point2D.Double(loc.getX() * 11.35, loc.getY() * 11.35));
        }

        Draw drawer = new Draw();
        drawer.draw(points, randomNumber);
    }

    /**
     * Generates a random initial path.
     *
     * @param locations the array of intermediate locations.
     * @return a randomly shuffled path.
     */
    private static int[] generateRandomPath(int[] locations) {
        int[] result = Arrays.copyOf(locations, locations.length);
        Random random = new Random();
        for (int i = 0; i < result.length; i++) {
            int swapIndex = random.nextInt(result.length);
            int temp = result[i];
            result[i] = result[swapIndex];
            result[swapIndex] = temp;
        }
        return result;
    }

    /**
     * Generates a neighboring path by modifying the current path.
     *
     * @param path the current path.
     * @return a new path generated from the current path.
     */
    private static int[] generateNeighborPath(int[] path) {
        int n = path.length;
        Random random = new Random();
        int[] newPath = Arrays.copyOf(path, n);

        if (random.nextDouble() < 0.5) {
            // Swap two random elements
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            int temp = newPath[i];
            newPath[i] = newPath[j];
            newPath[j] = temp;
        } else {
            // Reverse a random segment
            int i = random.nextInt(n);
            int j = random.nextInt(n);
            if (i > j) {
                int temp = i;
                i = j;
                j = temp;
            }
            while (i < j) {
                int temp = newPath[i];
                newPath[i] = newPath[j];
                newPath[j] = temp;
                i++;
                j--;
            }
        }

        return newPath;
    }

    /**
     * Calculates the total distance of a path.
     *
     * @param path the path array.
     * @param start the starting location.
     * @param end   the ending location.
     * @return the total distance of the path.
     */
    private static double calculateDistance(int[] path, int start, int end) {
        double distance = 0.0;
        for (int i = 0; i < path.length - 1; i++) {
            distance += distanceMatrix[path[i] - 1][path[i + 1] - 1];
        }
        distance += distanceMatrix[start - 1][path[0] - 1];
        distance += distanceMatrix[path[path.length - 1] - 1][end - 1];
        return distance;
    }
}
