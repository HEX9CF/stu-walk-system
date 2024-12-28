package util;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * The Draw class is responsible for rendering a coordinate graph with a background image 
 * and saving the result as a PNG file. This class is typically used for generating graphs 
 * based on a list of 2D points and storing them in a specified file location.
 */
public class Draw {

    /**
     * This method generates an image of a coordinate graph with the provided points.
     * The graph is drawn on a background image and then saved as a PNG file with a unique name.
     * 
     * @param points A list of Point2D.Double objects representing the coordinates to be plotted.
     * @param randomNumber A random number used to generate a unique file path for saving the image.
     */
    public void draw(List<Point2D.Double> points, int randomNumber) {
        // Set the system property for headless mode. This disables the need for a GUI.
        System.setProperty("java.awt.headless", "true");

        // Create an instance of CoordinatePanelWithBackground to draw the graph
        CoordinatePanelWithBackground panel = new CoordinatePanelWithBackground(points);

        // Define the file path where the image will be saved, using the random number to ensure the file name is unique
        String StringPath = "/www/wwwroot/database.ccjy16.top/data/photo_path" + randomNumber + ".png";

        // Draw the image and save it at the specified path
        panel.drawToImage(StringPath);
    }
}
