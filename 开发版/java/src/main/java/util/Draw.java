package util;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Utility class for generating graphs from a list of points.
 */
public class Draw {

    /**
     * Generates a graph with a background and saves it as an image.
     *
     * @param points       the list of points to be plotted.
     * @param randomNumber a random number used for generating the output file name.
     */
    public void generateGraph(List<Point2D.Double> points, int randomNumber) {
        // Enable headless mode for environments without a display
        System.setProperty("java.awt.headless", "true");

        // Create a CoordinatePanelWithBackground object and draw the graph
        CoordinatePanelWithBackground panel = new CoordinatePanelWithBackground(points);

        // Construct the output file path
        String outputPath = "/www/wwwroot/database.ccjy16.top/data/photo_path" + randomNumber + ".png";

        // Draw the graph to an image
        panel.drawToImage(outputPath);

        // Log the output path
        System.out.println("Graph generated and saved to: " + outputPath);
    }
}
