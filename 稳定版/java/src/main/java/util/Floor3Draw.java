package util;

import pojo.Point;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for drawing the path on the floor plan based on a list of points.
 * It retrieves coordinates from the Floor3ReadSheet1, computes the path between the start and end points,
 * and then generates an image with the path drawn on a background.
 */
public class Floor3Draw {

    /**
     * Initiates the drawing of a path based on the result from the EDijkstra algorithm.
     * 
     * @param startPoint The index of the start point.
     * @param endPoint The index of the end point.
     * @param randomNumber A random number used for generating unique image file names.
     */
    public static void test(int startPoint, int endPoint, int randomNumber) {
        List<Integer> path = EDijkstra.dijkstra(startPoint, endPoint);
        pointAndDraw(path, randomNumber);
    }

    /**
     * Converts the list of path indices into a list of coordinates, then draws the path.
     * 
     * @param path The list of point indices representing the path.
     * @param randomNumber A random number used for generating unique image file names.
     */
    public static void pointAndDraw(List<Integer> path, int randomNumber) {
        List<Point2D.Double> points = new ArrayList<>();
        Floor3ReadSheet1 readSheet1 = new Floor3ReadSheet1();
        List<Point> pointList = readSheet1.read();

        // Creating an array to hold point data
        Point[] pointsArray = new Point[123];
        int index = 1;
        for (Point point : pointList) {
            pointsArray[index] = point;
            index++;
        }

        // Add the points from the path list to the points list for drawing
        for (Integer pointIndex : path) {
            Double x = pointsArray[pointIndex].getX();
            Double y = pointsArray[pointIndex].getY();
            Point2D.Double point = new Point2D.Double(x * 81, y * 81);
            points.add(point);
        }

        // Draw the path on the floor plan
        draw(points, randomNumber);
    }

    /**
     * Draws the given list of points on the floor plan and saves it as an image.
     * 
     * @param points The list of points to be drawn on the floor plan.
     * @param randomNumber A random number used for generating unique image file names.
     */
    public static void draw(List<Point2D.Double> points, int randomNumber) {
        // Set system property to allow headless operation (no graphical interface)
        System.setProperty("java.awt.headless", "true");

        // Create the drawing panel with the points to be drawn
        Floor3CoordinatePanelWithBackground panel = new Floor3CoordinatePanelWithBackground(points);

        // Define the output file path
        String outputPath = "/www/wwwroot/database.ccjy16.top/data/floor3_path" + randomNumber + ".png";
        panel.drawToImage(outputPath);
    }
}
