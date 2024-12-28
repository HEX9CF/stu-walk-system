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
 * This class provides functionality to draw a set of points and lines on a background image.
 * It ensures no duplicate lines are drawn and allows saving the result to a file.
 */
public class Floor3CoordinatePanelWithBackground {
    private final List<Point2D.Double> points;
    private BufferedImage backgroundImage;
    private Set<Line> drawnLines = new HashSet<>();

    /**
     * Constructor that initializes the panel with a list of points and loads the background image.
     *
     * @param points List of points to be drawn.
     */
    public Floor3CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // Load background image from a file
        try {
            // Use an absolute path for the background image (can be replaced with URL)
            backgroundImage = ImageIO.read(new File("/www/wwwroot/database.ccjy16.top/data/floor3.png"));
        } catch (IOException e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    /**
     * Draws the points and lines onto an image and saves the result.
     *
     * @param outputPath The file path where the generated image will be saved.
     */
    public void drawToImage(String outputPath) {
        int width = 1204; // Image width (including margins)
        int height = 856; // Image height (including margins)
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create the graphics context for drawing
        Graphics2D g2d = bufferedImage.createGraphics();

        // Define margin and axis positions
        int margin = 50;
        int xAxisY = height - margin;
        int yAxisX = margin;

        // Draw the background image
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            g2d.drawImage(backgroundImage, yAxisX, xAxisY - bgHeight, bgWidth, bgHeight, null);
        }

        // Draw the points
        drawPoints(g2d, xAxisY, yAxisX);

        // Draw the lines
        drawLines(g2d, xAxisY, yAxisX);

        // Dispose the graphics context
        g2d.dispose();

        // Save the drawn image to a file
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }

    /**
     * Draws the points on the canvas with different colors for the first, last, and intermediate points.
     *
     * @param g2d     The graphics context to draw on.
     * @param xAxisY  The Y-coordinate of the X-axis.
     * @param yAxisX  The X-coordinate of the Y-axis.
     */
    private void drawPoints(Graphics2D g2d, int xAxisY, int yAxisX) {
        g2d.setColor(Color.RED);
        int index = 0;

        for (Point2D.Double point : points) {
            if (index == 0) {
                g2d.setColor(Color.YELLOW); // First point in yellow
            } else if (index == points.size() - 1) {
                g2d.setColor(Color.BLUE); // Last point in blue
            } else {
                g2d.setColor(Color.RED); // Intermediate points in red
            }

            int x = (int) (yAxisX + point.x);
            int y = (int) (xAxisY - point.y);

            int pointSize = (index == 0 || index == points.size() - 1) ? 20 : 10;
            g2d.fillOval(x - pointSize / 2, y - pointSize / 2, pointSize, pointSize);

            index++;
        }
    }

    /**
     * Draws the lines between consecutive points, ensuring no duplicate lines are drawn.
     *
     * @param g2d     The graphics context to draw on.
     * @param xAxisY  The Y-coordinate of the X-axis.
     * @param yAxisX  The X-coordinate of the Y-axis.
     */
    private void drawLines(Graphics2D g2d, int xAxisY, int yAxisX) {
        g2d.setColor(Color.RED);
        BasicStroke thickStroke = new BasicStroke(4.0f); // Set line thickness
        g2d.setStroke(thickStroke);

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);

            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);
            if (!drawnLines.contains(line)) {
                g2d.drawLine(x1, y1, x2, y2);
                drawnLines.add(line);
            }
        }
    }

    /**
     * Represents a line between two points, ensuring that the line is uniquely identified
     * regardless of the order of the points.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        public Line(Point2D.Double point1, Point2D.Double point2) {
            // Ensure a consistent order for the points
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
