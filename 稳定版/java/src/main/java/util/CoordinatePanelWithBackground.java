package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is responsible for drawing a set of points on an image with a background. It draws 
 * the points and connects them with lines. It ensures that duplicate lines are not drawn 
 * and allows customization of the point colors based on the position of the points.
 */
public class CoordinatePanelWithBackground {

    // List of points to be drawn on the image
    private final List<Point2D.Double> points;
    
    // Background image to be drawn behind the points
    private BufferedImage backgroundImage;
    
    // Set to store the lines that have been drawn to avoid duplication
    private Set<Line> drawnLines = new HashSet<>();

    /**
     * Constructor that initializes the list of points and attempts to load a background image.
     *
     * @param points List of points to be drawn
     */
    public CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // Load the background image
        try {
            // Replace with appropriate path to the background image
            backgroundImage = ImageIO.read(new File("/www/wwwroot/database.ccjy16.top/data/photo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the points and their connecting lines to a new image and saves it to the specified path.
     * The background image is drawn first, followed by the points and the lines connecting them.
     *
     * @param outputPath The file path where the image will be saved
     */
    public void drawToImage(String outputPath) {
        int width = 1544 + 50; // Image width with padding
        int height = 863 + 50; // Image height with padding
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics context for drawing on the image
        Graphics2D g2d = bufferedImage.createGraphics();

        // Define margins and axis positions
        int margin = 50; // Padding for the image edges
        int xAxisY = height - margin; // Y-coordinate for the x-axis
        int yAxisX = margin; // X-coordinate for the y-axis

        // Draw the background image, if available
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            g2d.drawImage(backgroundImage, yAxisX, xAxisY - bgHeight, bgWidth, bgHeight, null);
        }

        // Draw the points
        g2d.setColor(Color.RED);
        int ii = 0;
        for (Point2D.Double point : points) {
            // Color the first and last points differently
            if(ii == 0){
                g2d.setColor(Color.yellow);
            } else if(ii == points.size() - 1){
                g2d.setColor(Color.BLUE);
            }
            int x = (int) (yAxisX + point.x); // Convert point's x coordinate to image x coordinate
            int y = (int) (xAxisY - point.y); // Convert point's y coordinate to image y coordinate
            if(ii == 0 || ii == points.size() - 1){
                g2d.fillOval(x - 10, y - 10, 20, 20); // Draw larger circle for the first and last points
            } else {
                g2d.fillOval(x - 5, y - 5, 10, 10); // Draw smaller circle for intermediate points
            }
            if(ii == 0 || ii == points.size() - 1){
                g2d.setColor(Color.red); // Reset color for the lines
            }
            ii++;
        }

        // Draw thick connecting lines between the points
        g2d.setColor(Color.RED); // Line color
        BasicStroke thickStroke = new BasicStroke(4.0f); // Set line thickness
        g2d.setStroke(thickStroke);

        // Draw the lines connecting the points
        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);
            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);

            // Check if this line has been drawn already
            if (!drawnLines.contains(line)) {
                // Draw the line if it hasn't been drawn
                g2d.drawLine(x1, y1, x2, y2);
                // Mark the line as drawn
                drawnLines.add(line);
            }
        }

        g2d.dispose();

        // Save the final image to the specified path
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inner class representing a line between two points.
     * This class ensures that lines are compared in an order-independent manner to avoid duplication.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        /**
         * Constructor to create a line object ensuring the points are stored in a consistent order.
         *
         * @param point1 First point of the line
         * @param point2 Second point of the line
         */
        public Line(Point2D.Double point1, Point2D.Double point2) {
            // Ensure point1 is always the "lesser" point (by x or y coordinate)
            if (point1.x < point2.x || (point1.x == point2.x && point1.y < point2.y)) {
                this.point1 = point1;
                this.point2 = point2;
            } else {
                this.point1 = point2;
                this.point2 = point1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return (point1.equals(line.point1) && point2.equals(line.point2)) ||
                    (point1.equals(line.point2) && point2.equals(line.point1));
        }

        @Override
        public int hashCode() {
            return point1.hashCode() + point2.hashCode();
        }
    }
}
