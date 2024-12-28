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
 * The Floor5CoordinatePanelWithBackground class is responsible for rendering a list of points on a panel
 * and saving the generated image to a file. It also draws lines between consecutive points and includes a background image.
 */
public class Floor5CoordinatePanelWithBackground {
    private final List<Point2D.Double> points;
    private BufferedImage backgroundImage;
    // Set to store already drawn lines, preventing duplicate lines
    private Set<Line> drawnLines = new HashSet<>();

    /**
     * Constructor to initialize the panel with a list of points and load the background image.
     *
     * @param points A list of points to be drawn on the panel.
     */
    public Floor5CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // Load the background image
        try {
            // Use the local file path to load the background image
            backgroundImage = ImageIO.read(new File("/www/wwwroot/database.ccjy16.top/data/floor5.png"));
            // Uncomment and adjust for local testing:
            // backgroundImage = ImageIO.read(new File("D:\\IdeaProjects\\HTML\\redMove\\floor5.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method draws the points and lines on the background image and saves the resulting image to the specified file path.
     * The points are drawn as colored circles and connected with thick red lines.
     *
     * @param outputPath The file path where the resulting image should be saved.
     */
    public void drawToImage(String outputPath) {
        int width = 1154 + 50; // Image width with margin
        int height = 806 + 50; // Image height with margin
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the Graphics2D context to draw on the image
        Graphics2D g2d = bufferedImage.createGraphics();

        // Define margins for the x and y axes
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
        g2d.setColor(Color.RED);
        int ii = 0;
        for (Point2D.Double point : points) {
            // Color the first and last points differently (yellow and blue)
            if (ii == 0) {
                g2d.setColor(Color.yellow);
            } else if (ii == points.size() - 1) {
                g2d.setColor(Color.BLUE);
            }

            // Convert the coordinates to panel coordinates
            int x = (int) (yAxisX + point.x);
            int y = (int) (xAxisY - point.y);

            // Draw the points as circles (larger for the first and last points)
            if (ii == 0 || ii == points.size() - 1) {
                g2d.fillOval(x - 10, y - 10, 20, 20);
            } else {
                g2d.fillOval(x - 5, y - 5, 10, 10);
            }

            // Reset color to red for the next point
            if (ii == 0 || ii == points.size() - 1) {
                g2d.setColor(Color.red);
            }
            ii++;
        }

        // Draw lines connecting the points
        g2d.setColor(Color.RED); // Red color for the lines
        BasicStroke thickStroke = new BasicStroke(4.0f); // Set line thickness
        g2d.setStroke(thickStroke);

        // Loop through the points and draw lines between consecutive points
        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);
            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);

            // Check if the line has already been drawn to prevent duplicates
            if (!drawnLines.contains(line)) {
                g2d.drawLine(x1, y1, x2, y2); // Draw the line
                drawnLines.add(line); // Mark the line as drawn
            }
        }

        // Dispose the Graphics2D object after drawing
        g2d.dispose();

        // Save the image to the specified file
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("Image saved to: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The Line class represents a line between two points. It is used to track and compare lines that have already been drawn.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        /**
         * Constructor to create a line from two points.
         * Ensures that the points are ordered to avoid duplicate lines.
         *
         * @param point1 The first point of the line.
         * @param point2 The second point of the line.
         */
        public Line(Point2D.Double point1, Point2D.Double point2) {
            // Ensure the points are ordered by their coordinates
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
