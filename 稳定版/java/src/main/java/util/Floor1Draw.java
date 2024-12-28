package util;

import pojo.Point;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The Floor1Draw class is responsible for processing a list of points, 
 * calculating paths between specific points using the Dijkstra algorithm, 
 * and drawing the result on a floor plan with a background.
 */
public class Floor1Draw {

    /**
     * Test method that triggers the point-and-draw functionality.
     * It uses the Dijkstra algorithm to calculate the shortest path between two points
     * and then draws the path on the floor plan.
     * 
     * @param st Starting point index
     * @param ed Ending point index
     * @param randomNumber Random number used for unique output file names
     */
    public static void test(int st, int ed, int randomNumber){
        pointAndDraw(EDijkstra.dijkstra(st, ed), randomNumber); // Call pointAndDraw with the result of Dijkstra
    }

    /**
     * This method processes the list of indices returned by the Dijkstra algorithm,
     * retrieves corresponding coordinates from a predefined list, 
     * and draws the path on the floor plan image.
     * 
     * @param list List of point indices returned by the Dijkstra algorithm
     * @param randomNumber Random number for unique image output file name
     */
    public static void pointAndDraw(List<Integer> list, int randomNumber) {
        // List to hold the points for drawing
        List<Point2D.Double> points = new ArrayList<>();
        
        // Read the predefined set of points from a floor plan data source
        Floor1ReadSheet1 readSheet1 = new Floor1ReadSheet1();
        List<Point> pointList = readSheet1.read();

        // Array to hold 122 points, representing different locations on the floor plan
        Point[] didians = new Point[122];
        
        // Populate the array with points from pointList
        int i = 1;
        for (Point di : pointList) {
            didians[i] = di;
            i++;
        }

        // Iterate over the list of points (indices) and retrieve their coordinates
        for (Integer in : list) {
            // Get the x and y coordinates for each point
            Double x = didians[in].getX();
            Double y = didians[in].getY();
            
            // Scale the coordinates (x and y) for display
            Point2D.Double aDouble = new Point2D.Double(x * 86, y * 86);
            points.add(aDouble); // Add the point to the list of points
        }

        // Call the draw method to generate the image
        draw(points, randomNumber);
    }

    /**
     * This method takes a list of points and draws them on the floor plan,
     * saving the resulting image to a file.
     * 
     * @param points List of points to be drawn on the floor plan
     * @param randomNumber Random number used for generating unique output file names
     */
    public static void draw(List<Point2D.Double> points, int randomNumber) {
        // Set the system property to avoid headless exceptions (no GUI required)
        System.setProperty("java.awt.headless", "true");

        // Create a new panel for drawing the points and path on the floor plan
        Floor1CoordinatePanelWithBackground panel = new Floor1CoordinatePanelWithBackground(points);
        
        // Define the output file path for the image, using the random number to generate a unique name
        String StringPath = "/www/wwwroot/database.ccjy16.top/data/floor1_path" + randomNumber + ".png";
        
        // Draw the points and save the result to the specified file
        panel.drawToImage(StringPath);
    }
}
