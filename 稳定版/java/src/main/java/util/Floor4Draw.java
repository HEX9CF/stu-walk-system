package util;

import pojo.Point;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The Floor4Draw class is responsible for generating and drawing paths
 * on a floor plan by taking the output of the Dijkstra algorithm (list of point indices) 
 * and drawing the corresponding points on the background image.
 */
public class Floor4Draw {

    /**
     * Tests the path generation and drawing from the start to the end point.
     * This method is typically used to test the integration of pathfinding and drawing.
     *
     * @param st Starting point index.
     * @param ed Ending point index.
     * @param randomNumber A random number used for generating a unique file name for the saved image.
     */
    public static void test(int st, int ed, int randomNumber) {
        // Get the path from the Dijkstra algorithm
        List<Integer> path = EDijkstra.dijkstra(st, ed);  // Assuming EDijkstra is another class you implemented
        // Generate points from the path and draw them on the image
        pointAndDraw(path, randomNumber);
    }

    /**
     * Converts the path (a list of point indices) into actual coordinates, 
     * and draws these points on the floor plan.
     *
     * @param path List of point indices representing the path.
     * @param randomNumber A random number used for generating a unique file name for the saved image.
     */
    public static void pointAndDraw(List<Integer> path, int randomNumber) {
        List<Point2D.Double> points = new ArrayList<>();

        // Read the points data from the Excel sheet
        Floor4ReadSheet1 readSheet1 = new Floor4ReadSheet1();
        List<Point> pointList = readSheet1.read();

        // Validate the points list size
        if (pointList == null || pointList.size() < 98) {
            System.err.println("Error: Insufficient points in the data.");
            return;
        }

        // Initialize the points array with the size of the list
        Point[] didians = new Point[pointList.size()];
        didians = pointList.toArray(didians);

        // Loop through the path indices and add corresponding points to the list
        for (Integer index : path) {
            if (index >= 0 && index < didians.length) {
                Double x = didians[index].getX();
                Double y = didians[index].getY();
                // Scale the coordinates for drawing
                Point2D.Double point = new Point2D.Double(x * 61, y * 61);
                points.add(point);
            } else {
                // Handle invalid point index
                System.err.println("Invalid point index: " + index);
            }
        }

        // Draw the points and save the image
        draw(points, randomNumber);
    }

    /**
     * Draws the points on the floor plan background and saves the resulting image.
     * It uses the Floor4CoordinatePanelWithBackground class to handle the drawing and image generation.
     *
     * @param points List of points to be drawn on the floor plan.
     * @param randomNumber A random number used for generating a unique file name for the saved image.
     */
    public static void draw(List<Point2D.Double> points, int randomNumber) {
        // Set the system property for headless operation (useful for non-GUI environments)
        System.setProperty("java.awt.headless", "true");

        // Create a panel to handle drawing the points and background image
        Floor4CoordinatePanelWithBackground panel = new Floor4CoordinatePanelWithBackground(points);

        // Define the output path for the generated image
        String outputPath = "/www/wwwroot/database.ccjy16.top/data/floor4_path" + randomNumber + ".png";

        // Generate the image and save it to the specified path
        panel.drawToImage(outputPath);
    }
}
