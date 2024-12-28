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
 * This class provides functionality to draw points and lines on a background image for floor 4 layout.
 * It allows saving the drawn layout as an image to a file.
 */
public class Floor4CoordinatePanelWithBackground {
    private final List<Point2D.Double> points;  // List of points to be drawn
    private BufferedImage backgroundImage;  // Background image
    private Set<Line> drawnLines = new HashSet<>();  // Set to store drawn lines to avoid duplicates

    /**
     * Constructor to initialize the panel with points and load the background image.
     *
     * @param points List of points to draw on the panel.
     */
    public Floor4CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // Load background image from file
        try {
            backgroundImage = ImageIO.read(new File("/www/wwwroot/database.ccjy16.top/data/floor4.png"));
            // You can also load from local path if needed
            // backgroundImage = ImageIO.read(new File("D:\\IdeaProjects\\HTML\\redMove\\floor4.png"));
        } catch (IOException e) {
            e.printStackTrace();  // Handle image loading error
        }
    }

    /**
     * Draws the points, lines, and background image, and saves the resulting image to a file.
     *
     * @param outputPath Path to save the generated image.
     */
    public void drawToImage(String outputPath) {
        int width = 1154 + 50;  // Image width with margin
        int height = 806 + 50;  // Image height with margin

        // Create a buffered image and get its graphics context
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Define axis margins and positions
        int margin = 50;
        int xAxisY = height - margin;
        int yAxisX = margin;

        // Draw background image if available
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            g2d.drawImage(backgroundImage, yAxisX, xAxisY - bgHeight, bgWidth, bgHeight, null);
        }

        // Draw points on the panel
        g2d.setColor(Color.RED);
        int ii = 0;
        for (Point2D.Double point : points) {
            if (ii == 0) {
                g2d.setColor(Color.YELLOW);  // Start point is yellow
            } else if (ii == points.size() - 1) {
                g2d.setColor(Color.BLUE);  // End point is blue
            }

            int x = (int) (yAxisX + point.x);  // Convert point x to panel coordinates
            int y = (int) (xAxisY - point.y);  // Convert point y to panel coordinates

            // Draw the points (larger for start/end, smaller for others)
            if (ii == 0 || ii == points.size() - 1) {
                g2d.fillOval(x - 10, y - 10, 20, 20);
            } else {
                g2d.fillOval(x - 5, y - 5, 10, 10);
            }

            // Reset the color to red after drawing start/end points
            if (ii == 0 || ii == points.size() - 1) {
                g2d.setColor(Color.RED);
            }
            ii++;
        }

        // Draw thick red lines connecting the points
        g2d.setColor(Color.RED);
        BasicStroke thickStroke = new BasicStroke(4.0f);
        g2d.setStroke(thickStroke);

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);

            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);

            // Only draw the line if it hasn't been drawn already
            if (!drawnLines.contains(line)) {
                g2d.drawLine(x1, y1, x2, y2);
                drawnLines.add(line);  // Mark this line as drawn
            }
        }

        // Dispose of the graphics context
        g2d.dispose();

        // Save the image to the specified output path
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();  // Handle save error
        }
    }

    /**
     * Helper class to represent a line between two points.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        public Line(Point2D.Double point1, Point2D.Double point2) {
            // Ensure consistent ordering of points to avoid duplicate lines
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
            return point1.hashCode() + point2.hashCode();  // Ensures proper hashing
        }
    }
}
