package util;

import pojo.Point;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * SimulatedAnnealing class implements the Simulated Annealing algorithm to find the optimal path
 * between a set of points. The algorithm iteratively explores the solution space by accepting
 * both better and worse solutions based on a probability function controlled by the temperature.
 * It generates a path based on the input points and tries to minimize the total distance traveled.
 */
public class SimulatedAnnealing {
    
    // 2D array to store distance between points
    public static double[][] d;
    
    // List to store intermediate path results
    public static List<List<Integer>> relist;

    /**
     * Finds the shortest path using Simulated Annealing algorithm for the given set of points.
     *
     * @param want the array of points to visit in order.
     * @param randomNumber a random number for path visualization.
     * @return the optimal path as an array, including start, end, and intermediate points.
     */
    public static int[] zuiDuanLu(int[] want, int randomNumber) {
        ReadSheet4 readSheet4 = new ReadSheet4();
        List<Integer> list = new ArrayList<>();
        d = readSheet4.read();
        int nt = want.length;

        if (nt == 2) {
            List<Integer> twoPath = new ArrayList<>();
            List<Integer> dj = Dijkstra.dijkstra(want[0], want[1]);
            twoPath.addAll(dj);
            pointAndDraw(twoPath, randomNumber); // Draw the path
            int[] result = new int[3];
            result[0] = want[0];
            result[1] = want[1];
            result[2] = (int) Math.round(d[want[0] - 1][want[1] - 1]);
            return result;
        }

        int st = want[0];
        int ed = want[nt - 1];

        // Generate the sequence of intermediate points
        int mid_n = 0;
        int[] mid_want = new int[nt - 2];
        for (int i = 0; i < nt; i++) {
            if (want[i] != st && want[i] != ed) {
                mid_want[mid_n] = want[i];
                mid_n++;
            }
        }

        // Simulated annealing parameters initialization
        double init_T = 1000;   // Initial temperature
        double T = init_T; // Temperature for the first iteration
        int maxn = 1000;  // Maximum number of iterations
        int each_cishu = 500;  // Number of iterations for each temperature
        double xishu = 0.95;  // Cooling rate for temperature

        // Randomly generate an initial solution
        int[] t_path = generateRandomPath(mid_want, mid_n);
        int[] best_path = Arrays.copyOf(t_path, t_path.length);
        double t_res = calculate(t_path, st, ed); // Initial distance

        // Save intermediate results for output and drawing
        double min_res = t_res; // Initialize the best solution
        double[] res = new double[maxn]; // Record the minimum result at the end of each outer loop

        // Simulated annealing algorithm
        Random random = new Random();
        for (int j = 0; j < maxn; j++) { // Outer loop

            for (int i = 0; i < each_cishu; i++) { // Inner loop

                int[] new_path = getNewPath(t_path); // Generate a new path
                double res1 = calculate(new_path, st, ed); // Calculate the new path distance
                if (res1 < t_res) { // If the new solution has a shorter distance, update the solution
                    t_path = new_path;
                    t_res = res1;
                } else {
                    double p = Math.exp(-(res1 - t_res) / T); // Calculate probability using Metropolis criterion
                    double v = random.nextDouble();

                    if (v < p) {   // Generate a random probability and compare
                        t_path = new_path;  // Update the current path to the new path
                        t_res = res1;
                    }
                }
                // Check if the best solution should be updated
                if (t_res < min_res) { // If the current distance is shorter, update the shortest distance
                    min_res = t_res;
                    best_path = Arrays.copyOf(t_path, t_path.length);
                }
            }
            res[j] = min_res; // Save the minimum distance after each outer loop
            T = xishu * T;   // Reduce the temperature
        }

        int[] result = new int[best_path.length + 2];
        System.out.print("Minimum Distance: ");
        System.out.println(min_res);
        result[0] = st;
        System.arraycopy(best_path, 0, result, 1, best_path.length);
        result[result.length - 1] = ed;

        System.out.print("==========>");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
        int length = result.length;
        // Find the shortest path between two points and the intermediate points
        List<Integer> finalPath = new ArrayList<>();
        for (int i = 0; i < length - 1; i++) {
            List<Integer> dj = Dijkstra.dijkstra(result[i], result[i + 1]); // Get the shortest path between two points
            finalPath.addAll(dj); // Add each segment of the path to the final path
        }

        System.out.println("Final Path: " + finalPath.toString());
        pointAndDraw(finalPath, randomNumber); // Draw the path
        int[] result2 = new int[result.length + 1];
        for (int i = 0; i < result.length; i++) {
            result2[i] = result[i];
        }
        result2[result.length] = (int) Math.round(min_res);
        return result2;
    }

    /**
     * Adds points and draws the path.
     *
     * @param list the list of points to be drawn.
     * @param randomNumber a random number for visualization.
     */
    public static void pointAndDraw(List<Integer> list, int randomNumber) {
        // Add points
        List<Point2D.Double> points = new ArrayList<>();
        ReadSheet1 readSheet1 = new ReadSheet1();
        List<Point> pointList = readSheet1.read();

        Point[] didians = new Point[60];
        int i = 1;
        for (Point di : pointList) {
            didians[i] = di;
            i++;
        }

        for (Integer in : list) {
            Double x = didians[in].getX();
            Double y = didians[in].getY();
            Point2D.Double aDouble = new Point2D.Double(x * 11.35, y * 11.35);
            points.add(aDouble);
        }
        System.out.println(points);

        Draw draw = new Draw();
        draw.draw(points, randomNumber);
    }

    /**
     * Randomly generates an initial path from the set of intermediate points.
     *
     * @param mid_want the array of intermediate points.
     * @param mid_n the number of intermediate points.
     * @return the generated random path.
     */
    public static int[] generateRandomPath(int[] mid_want, int mid_n) {
        int[] result = new int[mid_n];
        Random random = new Random();
        boolean[] visited = new boolean[mid_n];
        for (int i = 0; i < mid_n; i++) {
            int randIndex;
            do {
                randIndex = random.nextInt(mid_n);
            } while (visited[randIndex]);
            visited[randIndex] = true;
            result[i] = mid_want[randIndex];
        }
        return result;
    }

    /**
     * Generates a new path based on the current path using a randomization strategy.
     *
     * @param t_path the current path.
     * @return the new path generated by randomization.
     */
    public static int[] getNewPath(int[] t_path) {
        int n = t_path.length; // The number of elements in the original path
        Random random = new Random();
        double p1 = 0.33;
        double p2 = 0.33;
        double judge = random.nextDouble(); // Randomly generate a probability

        int[] new_path = Arrays.copyOf(t_path, t_path.length);

        if (judge < p1) { // Swap method
            int num1 = random.nextInt(n); // Generate random position
            int num2 = random.nextInt(n); // Generate random position

            // Swap the elements at two random positions
            int temp = new_path[num1];
            new_path[num1] = new_path[num2];
            new_path[num2] = temp;

        } else if (judge < p1 + p2) { // Shift method
            int num1 = random.nextInt(n);
            int num2 = random.nextInt(n);
            int num3 = random.nextInt(n);

            // Sort the three random positions
            int[] sorted = {num1, num2, num3};
            Arrays.sort(sorted);
            num1 = sorted[0];
            num2 = sorted[1];
            num3 = sorted[2];

            // Shift operation
            int[] t1 = Arrays.copyOfRange(new_path, 0, num1);
            int[] t2 = Arrays.copyOfRange(new_path, num1, num2 + 1);
            int[] t3 = Arrays.copyOfRange(new_path, num2 + 1, num3 + 1);
            int[] t4 = Arrays.copyOfRange(new_path, num3 + 1, n);

            // Reassemble the path
            new_path = concatenate(t1, t3, t2, t4);

        } else { // Inversion method
            int num1 = random.nextInt(n);
            int num2 = random.nextInt(n);

            // Ensure num1 is less than num2
            if (num1 > num2) {
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }

            int[] t1 = Arrays.copyOfRange(new_path, 0, num1);
            int[] t2 = Arrays.copyOfRange(new_path, num1, num2 + 1);
            int[] t3 = Arrays.copyOfRange(new_path, num2 + 1, n);

            // Inversion operation
            int[] reversed = reverseArray(t2);
            new_path = concatenate(t1, reversed, t3);
        }
        return new_path;
    }

    // Helper method to concatenate arrays
    private static int[] concatenate(int[]... arrays) {
        int length = 0;
        for (int[] array : arrays) {
            length += array.length;
        }
        int[] result = new int[length];
        int index = 0;
        for (int[] array : arrays) {
            for (int element : array) {
                result[index++] = element;
            }
        }
        return result;
    }

    // Helper method to reverse an array
    private static int[] reverseArray(int[] array) {
        int[] reversed = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }

    // Placeholder for calculate method to compute the distance of a path
    private static double calculate(int[] path, int st, int ed) {
        // Actual distance calculation logic goes here
        return 0.0; // Dummy return value
    }
}
