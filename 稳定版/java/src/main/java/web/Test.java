package web;

import util.SimulatedAnnealing;

import java.util.Random;

/**
 * This class demonstrates the use of Simulated Annealing to find the optimal solution.
 * It creates an array representing the desired sequence and uses Simulated Annealing
 * to find the best route.
 */
public class Test {

    public static void main(String[] args) {
        // Desired sequence of elements
        int[] desiredSequence = {5, 4, 29, 1};

        // Create random number generator
        Random random = new Random();
        
        // Generate a random number between 0 and 1000
        int randomNumber = random.nextInt(1001);

        // Use Simulated Annealing to find the optimal route for the desired sequence
        int[] optimalRoute = SimulatedAnnealing.zuiDuanLu(desiredSequence, randomNumber);

        // Print the optimal route
        for (int i = 0; i < optimalRoute.length; i++) {
            System.out.print(optimalRoute[i] + " ");
        }
        System.out.println();
    }
}
