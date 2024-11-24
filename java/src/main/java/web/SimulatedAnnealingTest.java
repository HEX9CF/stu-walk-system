package web;

import util.Simulated_Annealing;

import java.util.Random;

/**
 * Test class for Simulated Annealing algorithm.
 * Demonstrates finding the shortest path for a given set of nodes.
 */
public class SimulatedAnnealingTest {

    public static void main(String[] args) {
        // Desired path to calculate the shortest route
        int[] desiredPath = {5, 4, 29, 1};
        int[] shortestPath;

        // Generate a random seed
        Random random = new Random();
        int randomSeed = random.nextInt(1001);

        // Calculate the shortest path using Simulated Annealing
        shortestPath = Simulated_Annealing.zuiDuanLu(desiredPath, randomSeed);

        // Print the resulting shortest path
        for (int node : shortestPath) {
            System.out.print(node + " ");
        }
        System.out.println();
    }
}
