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
 * This class provides functionality to draw coordinates with a background image.
 */
public class CoordinatePanelWithBackground {

    private final List<Point2D.Double> points; // List of points to draw
    private BufferedImage backgroundImage; // Background image
    private final Set<Line> drawnLines = new HashSet<>(); // Tracks already drawn lines

    /**
     * Constructs a CoordinatePanelWithBackground with a list of points.
     *
     * @param points the list of points to be drawn
     */
    public CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;
        loadBackgroundImage("/www/wwwroot/database.ccjy16.top/data/photo.png");
    }

    /**
     * Loads the background image from the specified path.
     *
     * @param imagePath the file path of the background image
     */
    private void loadBackgroundImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Failed to load background image: " + e.getMessage());
        }
    }

    /**
     * Draws the points and connections to an image file.
     *
     * @param outputPath the file path where the output image will be saved
     */
    public void drawToImage(String outputPath) {
        int width = 1544 + 50; // Width of the output image
        int height = 863 + 50; // Height of the output image
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get graphics context
        Graphics2D g2d = bufferedImage.createGraphics();

        // Define axis positions
        int margin = 50;
        int xAxisPosition = height - margin; // X-axis position
        int yAxisPosition = margin; // Y-axis position

        // Draw background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, yAxisPosition, xAxisPosition - backgroundImage.getHeight(),
                    backgroundImage.getWidth(), backgroundImage.getHeight(), null);
        }

        // Draw points
        drawPoints(g2d, xAxisPosition, yAxisPosition);

        // Draw connections between points
        drawConnections(g2d, xAxisPosition, yAxisPosition);

        // Dispose of graphics context
        g2d.dispose();

        // Save the image to file
        saveImage(bufferedImage, outputPath);
    }

    /**
     * Draws the points on the graphics context.
     */
    private void drawPoints(Graphics2D g2d, int xAxisPosition, int yAxisPosition) {
        int pointIndex = 0;
        for (Point2D.Double point : points) {
            int x = (int) (yAxisPosition + point.x);
            int y = (int) (xAxisPosition - point.y);

            // Set point color based on position in the list
            if (pointIndex == 0) {
                g2d.setColor(Color.YELLOW); // First point
            } else if (pointIndex == points.size() - 1) {
                g2d.setColor(Color.BLUE); // Last point
            } else {
                g2d.setColor(Color.RED); // Intermediate points
            }

            int size = (pointIndex == 0 || pointIndex == points.size() - 1) ? 20 : 10;
            g2d.fillOval(x - size / 2, y - size / 2, size, size);

            pointIndex++;
        }
    }

    /**
     * Draws connections between points on the graphics context.
     */
    private void drawConnections(Graphics2D g2d, int xAxisPosition, int yAxisPosition) {
        g2d.setColor(Color.RED); // Connection line color
        g2d.setStroke(new BasicStroke(4.0f)); // Connection line thickness

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);

            int x1 = (int) (yAxisPosition + point1.x);
            int y1 = (int) (xAxisPosition - point1.y);
            int x2 = (int) (yAxisPosition + point2.x);
            int y2 = (int) (xAxisPosition - point2.y);

            Line line = new Line(point1, point2);

            // Draw line if not already drawn
            if (drawnLines.add(line)) {
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    /**
     * Saves the image to the specified file path.
     *
     * @param image      the BufferedImage to save
     * @param outputPath the file path where the image will be saved
     */
    private void saveImage(BufferedImage image, String outputPath) {
        try {
            ImageIO.write(image, "png", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Failed to save image: " + e.getMessage());
        }
    }

    /**
     * Represents a line segment between two points.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return point1.equals(line.point1) && point2.equals(line.point2);
        }

        @Override
        public int hashCode() {
            return point1.hashCode() + point2.hashCode();
        }
    }
}
