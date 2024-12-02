package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Floor1CoordinatePanelWithBackground class is responsible for drawing a set of points on a floor plan
 * (represented as a background image), with lines connecting these points. It ensures that no duplicate lines
 * are drawn, and the points are represented with different colors based on their position in the list (start, 
 * end, and intermediate points). The drawing can then be saved as an image file.
 */
public class Floor1CoordinatePanelWithBackground {
    private final List<Point2D.Double> points; // List of points to be drawn
    private BufferedImage backgroundImage; // Background image of the floor plan
    private Set<Line> drawnLines = new HashSet<>(); // Set to track drawn lines and avoid duplicates

    /**
     * Constructor for initializing the panel with a set of points.
     * It also loads the background image of the floor.
     * 
     * @param points List of points to be drawn on the floor
     */
    public Floor1CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // Load the background image for the floor
        try {
            // Replace with your floor image path or URL
            backgroundImage = ImageIO.read(new File("/www/wwwroot/database.ccjy16.top/data/floor1.png"));
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions
        }
    }

    /**
     * Draws the points and connecting lines to an image and saves it to the specified file path.
     * 
     * @param outputPath The file path where the resulting image should be saved
     */
    public void drawToImage(String outputPath) {
        // Define the dimensions of the image (with margins)
        int width = 1277 + 50; // Width of the image
        int height = 892 + 50; // Height of the image
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics context for the image
        Graphics2D g2d = bufferedImage.createGraphics();

        // Define margin and coordinate axis positions
        int margin = 50; // Margin to leave space around the image
        int xAxisY = height - margin; // y-coordinate for the x-axis
        int yAxisX = margin; // x-coordinate for the y-axis

        // Draw the background image
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            // Ensure the background image is aligned to the bottom left corner (0, 0)
            g2d.drawImage(backgroundImage, yAxisX, xAxisY - bgHeight, bgWidth, bgHeight, null);
        }

        // Draw the points (start, intermediate, and end points)
        g2d.setColor(Color.RED);
        int ii = 0;
        for (Point2D.Double point : points) {
            // Change color for the start and end points
            if(ii == 0) {
                g2d.setColor(Color.YELLOW); // Start point in yellow
            } else if(ii == points.size() - 1) {
                g2d.setColor(Color.BLUE); // End point in blue
            }
            
            // Convert point coordinates to the panel's coordinate system
            int x = (int) (yAxisX + point.x);
            int y = (int) (xAxisY - point.y);
            
            // Draw larger circles for start and end points
            if(ii == 0 || ii == points.size() - 1) {
                g2d.fillOval(x - 10, y - 10, 20, 20); // Larger circle for start/end
            } else {
                g2d.fillOval(x - 5, y - 5, 10, 10); // Smaller circle for intermediate points
            }
            
            // Reset color back to red for intermediate points
            if(ii == 0 || ii == points.size() - 1) {
                g2d.setColor(Color.RED); // Reset color to red after drawing start/end point
            }
            ii++;
        }

        // Draw the connecting lines with a thick stroke
        g2d.setColor(Color.RED); // Line color
        BasicStroke thickStroke = new BasicStroke(4.0f); // Line width (4.0f)
        g2d.setStroke(thickStroke);

        // Draw lines between consecutive points
        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);
            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);

            // Avoid drawing duplicate lines
            if (!drawnLines.contains(line)) {
                g2d.drawLine(x1, y1, x2, y2); // Draw the line
                drawnLines.add(line); // Mark the line as drawn
            }
        }

        // Dispose of the graphics context
        g2d.dispose();

        // Save the resulting image to the specified file path
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace(); // Handle IO exceptions
        }
    }

    /**
     * Represents a line between two points. It ensures that lines are treated as undirected (i.e., no duplicates).
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        public Line(Point2D.Double point1, Point2D.Double point2) {
            // Ensure points are stored in a consistent order to avoid duplicates
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
            // Check if both points are the same regardless of the order
            return (point1.equals(line.point1) && point2.equals(line.point2)) ||
                    (point1.equals(line.point2) && point2.equals(line.point1));
        }

        @Override
        public int hashCode() {
            // Generate a unique hash code based on the points
            return point1.hashCode() + point2.hashCode();
        }
    }
}
