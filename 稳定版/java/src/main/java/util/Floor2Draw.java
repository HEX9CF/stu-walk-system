package util;

import pojo.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the logic for generating and drawing paths on floor 2.
 * It includes methods to test path generation, convert points to coordinates, and save the drawn path as an image.
 */
public class Floor2Draw {

    /**
     * This method tests the path generation from a start point to an end point,
     * and then draws the path using random number for file naming.
     *
     * @param start The starting point index.
     * @param end The ending point index.
     * @param randomNumber A random number used for generating the output image file name.
     */
    public static void test(int start, int end, int randomNumber) {
        // Get the list of points representing the path using Dijkstra's algorithm and draw it
        pointAndDraw(EDijkstra.dijkstra(start, end), randomNumber);
    }

    /**
     * Converts a list of point indices into actual coordinates, and then draws the path on a background.
     *
     * @param pathIndices List of point indices representing the path.
     * @param randomNumber A random number used for generating the output image file name.
     */
    public static void pointAndDraw(List<Integer> pathIndices, int randomNumber) {
        // Create a list to store the calculated points for the path
        List<Point2D.Double> pathPoints = new ArrayList<>();

        // Read the points from the floor plan sheet
        Floor2ReadSheet1 readSheet = new Floor2ReadSheet1();
        List<Point> allPoints = readSheet.read();

        // Create an array to store points
        Point[] pointsArray = new Point[123];

        // Populate the points array
        int index = 1; // Start from index 1, assuming the first point is reserved as index 0
        for (Point point : allPoints) {
            pointsArray[index] = point;
            index++;
        }

        // Convert path indices into actual coordinates
        for (Integer indexInPath : pathIndices) {
            Point point = pointsArray[indexInPath];
            Double x = point.getX();
            Double y = point.getY();
            Point2D.Double pathPoint = new Point2D.Double(x * 112, y * 111); // Scale the coordinates
            pathPoints.add(pathPoint);
        }

        // Call the draw method to generate the image
        draw(pathPoints, randomNumber);
    }

    /**
     * Draws the path represented by the list of points and saves the resulting image to the specified file path.
     *
     * @param points List of points to be drawn.
     * @param randomNumber A random number used for generating the output image file name.
     */
    public static void draw(List<Point2D.Double> points, int randomNumber) {
        // Set the system property to allow image generation without a GUI
        System.setProperty("java.awt.headless", "true");

        // Create a CoordinatePanelWithBackground object and generate the image
        Floor2CoordinatePanelWithBackground panel = new Floor2CoordinatePanelWithBackground(points);

        // Define the output path for the image
        String outputPath = "/www/wwwroot/database.ccjy16.top/data/floor2_path" + randomNumber + ".png";

        // Generate the image and save it to the specified path
        panel.drawToImage(outputPath);
    }
}
