package util;

import pojo.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The Floor5Draw class handles the process of selecting points based on a list of indices,
 * calculating their coordinates, and then drawing them on a floor plan image.
 * It also saves the drawn image to a specified file.
 */
public class Floor5Draw {

    /**
     * This method is used to trigger the process of selecting points and drawing them on the floor plan.
     * It calls the Dijkstra algorithm to get the path between two points, then converts the indices into coordinates
     * and passes them to the drawing method.
     *
     * @param st Starting point index.
     * @param ed Ending point index.
     * @param randomNumber Random number appended to the file path to save the image with a unique name.
     */
    public static void test(int st, int ed, int randomNumber) {
        pointAndDraw(EDijkstra.dijkstra(st, ed), randomNumber);
    }

    /**
     * This method takes a list of point indices and converts them into actual coordinates,
     * which are then passed to the drawing method. The coordinates are scaled based on floor-specific scaling factors.
     *
     * @param list List of point indices representing the path.
     * @param randomNumber Random number used to generate a unique filename for the saved image.
     */
    public static void pointAndDraw(List<Integer> list, int randomNumber) {
        // Create a list of points to store the selected coordinates
        List<Point2D.Double> points = new ArrayList<>();

        // Read the point data from the floor plan (using Floor5ReadSheet1 to load coordinates)
        Floor5ReadSheet1 readSheet1 = new Floor5ReadSheet1();
        List<Point> pointList = readSheet1.read();

        // Initialize an array to store the points (assuming there are 95 points)
        Point[] didians = new Point[95];
        int i = 1;
        // Populate the didians array with points from the pointList
        for (Point di : pointList) {
            didians[i] = di;
            i++;
        }

        // Convert the indices in the list into real-world coordinates
        for (Integer in : list) {
            Double x = didians[in].getX();
            Double y = didians[in].getY();
            // Scale the coordinates by floor-specific scaling factors (55.6 and 55.5)
            Point2D.Double aDouble = new Point2D.Double(x * 55.6, y * 55.5);
            points.add(aDouble);
        }

        // Call the draw method to create and save the image
        draw(points, randomNumber);
    }

    /**
     * This method is responsible for drawing the points on the floor plan and saving the resulting image.
     * It creates a `Floor5CoordinatePanelWithBackground` object, which handles the drawing and saves the image.
     *
     * @param points A list of points to be drawn.
     * @param randomNumber A unique identifier used to create a unique filename for the saved image.
     */
    public static void draw(List<Point2D.Double> points, int randomNumber) {
        // Set the system property to ensure that the code runs in headless mode (no GUI required)
        System.setProperty("java.awt.headless", "true");

        // Create the panel object with the list of points
        Floor5CoordinatePanelWithBackground panel = new Floor5CoordinatePanelWithBackground(points);
        
        // Define the path where the image will be saved (with random number to ensure uniqueness)
        String StringPath = "/www/wwwroot/database.ccjy16.top/data/floor5_path" + randomNumber + ".png";
        
        // Call the drawToImage method to render the points and save the image
        panel.drawToImage(StringPath);
    }
}
