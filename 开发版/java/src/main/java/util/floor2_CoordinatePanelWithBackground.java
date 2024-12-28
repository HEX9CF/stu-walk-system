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
 * A JPanel subclass for displaying a coordinate system with a background image.
 */
public class Floor2CoordinatePanelWithBackground extends JPanel {
    private final List<Point2D.Double> points;
    private BufferedImage backgroundImage;
    // A set to store lines that have been drawn to avoid duplicates
    private Set<Line> drawnLines = new HashSet<>();

    /**
     * Constructs a new instance with a list of points to be displayed.
     *
     * @param points The list of points to display.
     */
    public Floor2CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;
        setPreferredSize(new Dimension(1544 + 50, 863 + 50)); // Set panel size with margins

        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("D:\\IdeaProjects\\HTML\\redMove\\floor2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Define the starting position of the coordinate axes
        int margin = 50; // Margin size
        int xAxisY = height - margin; // X-axis position
        int yAxisX = margin; // Y-axis position

        // Draw background image
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            // Ensure the background image aligns with the bottom-left corner at (0, 0)
            g2d.drawImage(backgroundImage, yAxisX, xAxisY - bgHeight, bgWidth, bgHeight, this);
        }

        // Draw points
        g2d.setColor(Color.RED);
        int ii = 0;
        for (Point2D.Double point : points) {
            if (ii == 0) {
                g2d.setColor(Color.yellow);
            } else if (ii == points.size() - 1) {
                g2d.setColor(Color.BLUE);
            }
            int x = (int) (yAxisX + point.x); // Convert point's x-coordinate to panel coordinate
            int y = (int) (xAxisY - point.y); // Convert point's y-coordinate to panel coordinate
            if (ii == 0 || ii == points.size() - 1) {
                g2d.fillOval(x - 10, y - 10, 20, 20);
            } else {
                g2d.fillOval(x - 5, y - 5, 10, 10);
            }
            if (ii == 0 || ii == points.size() - 1) {
                g2d.setColor(Color.red);
            }
            ii++;
        }

        // Draw bold lines
        g2d.setColor(Color.RED); // Choose a different color for lines
        BasicStroke thickStroke = new BasicStroke(4.0f); // Set line width to 4.0f
        g2d.setStroke(thickStroke);

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);
            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);

            // Check if the line has been drawn
            if (!drawnLines.contains(line)) {
                // Draw line
                g2d.drawLine(x1, y1, x2, y2);
                // Mark the line as drawn
                drawnLines.add(line);
            }
        }
    }

    /**
     * Represents a line segment between two points.
     */
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        public Line(Point2D.Double point1, Point2D.Double point2) {
            // Keep points in order to ensure unordered property
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
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Line line = (Line) o;
            return (point1.equals(line.point1) && point2.equals(line.point2)) ||
                    (point1.equals(line.point2) && point2.equals(line.point1));
        }

        @Override
        public int hashCode() {
            return point1.hashCode() + point2.hashCode();
        }
    }

    /**
     * Saves the current panel as an image file.
     *
     * @param filePath The path to save the image.
     */
    public void saveImage(String filePath) {
        // Get panel width and height
        int width = this.getWidth();
        int height = this.getHeight();
        // Create BufferedImage and draw on it
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Use paintComponent to draw on BufferedImage
        this.paint(g2d);
        g2d.dispose();

        // Save BufferedImage to file
        try {
            ImageIO.write(bufferedImage, "png", new File(filePath));
            System.out.println("Image saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}