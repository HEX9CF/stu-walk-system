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
 * This class represents a coordinate panel with a background image.
 * It allows for drawing points on a background image and connecting the points with lines.
 */
public class Floor2CoordinatePanelWithBackground {
    private final List<Point2D.Double> points; // List of points to be drawn on the panel
    private BufferedImage backgroundImage; // Background image to be drawn behind the points and lines
    private Set<Line> drawnLines = new HashSet<>(); // Set to keep track of drawn lines to avoid duplicates

    /**
     * Constructor to initialize the coordinate panel with the given list of points.
     *
     * @param points The list of points to be drawn on the coordinate panel.
     */
    public Floor2CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("/www/wwwroot/database.ccjy16.top/data/floor2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method draws the points and lines on an image and saves it to the specified output path.
     *
     * @param outputPath The file path where the generated image will be saved.
     */
    public void drawToImage(String outputPath) {
        int imageWidth = 1154 + 50; // Image width with margin
        int imageHeight = 806 + 50; // Image height with margin
        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics context for drawing
        Graphics2D graphics2D = bufferedImage.createGraphics();

        // Define the axis margin and positions
        int margin = 50; // Margin for the axis
        int xAxisPositionY = imageHeight - margin; // Y position of the X-axis
        int yAxisPositionX = margin; // X position of the Y-axis

        // Draw the background image
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            graphics2D.drawImage(backgroundImage, yAxisPositionX, xAxisPositionY - bgHeight, bgWidth, bgHeight, null);
        }

        // Draw points on the image
        graphics2D.setColor(Color.RED);
        int pointIndex = 0;
        for (Point2D.Double point : points) {
            if (pointIndex == 0) {
                graphics2D.setColor(Color.YELLOW); // First point in yellow
            } else if (pointIndex == points.size() - 1) {
                graphics2D.setColor(Color.BLUE); // Last point in blue
            }
            int x = (int) (yAxisPositionX + point.x); // Convert point x to panel coordinate
            int y = (int) (xAxisPositionY - point.y); // Convert point y to panel coordinate
            if (pointIndex == 0 || pointIndex == points.size() - 1) {
                graphics2D.fillOval(x - 10, y - 10, 20, 20); // Draw larger circles for the first and last points
            } else {
                graphics2D.fillOval(x - 5, y - 5, 10, 10); // Smaller circles for other points
            }
            if (pointIndex == 0 || pointIndex == points.size() - 1) {
                graphics2D.setColor(Color.RED); // Reset color to red after the first and last points
            }
            pointIndex++;
        }

        // Draw thick lines connecting the points
        graphics2D.setColor(Color.RED); // Set line color to red
        BasicStroke thickStroke = new BasicStroke(4.0f); // Set the line width to 4.0f
        graphics2D.setStroke(thickStroke);

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);
            int x1 = (int) (yAxisPositionX + point1.x);
            int y1 = (int) (xAxisPositionY - point1.y);
            int x2 = (int) (yAxisPositionX + point2.x);
            int y2 = (int) (xAxisPositionY - point2.y);

            Line line = new Line(point1, point2);

            // Draw the line if it has not been drawn before
            if (!drawnLines.contains(line)) {
                graphics2D.drawLine(x1, y1, x2, y2);
                drawnLines.add(line); // Mark this line as drawn
            }
        }

        graphics2D.dispose(); // Release resources used by the graphics context

        // Save the generated image to the specified file path
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("Image has been saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class represents a line between two points.
     * The order of the points is important for line comparison.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        /**
         * Constructor to initialize the line with two points.
         * The points are stored in a specific order for comparison purposes.
         *
         * @param point1 The first point.
         * @param point2 The second point.
         */
        public Line(Point2D.Double point1, Point2D.Double point2) {
            if (point1.x < point2.x || (point1.x == point2.x && point1.y < point2.y)) {
                this.point1 = point1;
                this.point2 = point2;
            } else {
                this.point1 = point2;
                this.point2 = point1;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Line line = (Line) obj;
            return (point1.equals(line.point1) && point2.equals(line.point2)) ||
                   (point1.equals(line.point2) && point2.equals(line.point1));
        }

        @Override
        public int hashCode() {
            return point1.hashCode() + point2.hashCode();
        }
    }
}
