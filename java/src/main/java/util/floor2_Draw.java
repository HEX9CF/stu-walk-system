package util;

import pojo.Didian;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for drawing points and lines based on calculated paths.
 */
public class Floor2Draw {
    /**
     * Tests the Dijkstra algorithm and draws the resulting path.
     *
     * @param st           The starting point index.
     * @param ed           The ending point index.
     * @param randomNumber A random number for file naming.
     */
    public static void test(int st, int ed, int randomNumber) {
        pointAndDraw(E_Dijkstra.dijkstra(st, ed), randomNumber);
    }

    /**
     * Adds points to a list and draws them with connecting lines.
     *
     * @param list         The list of point indices.
     * @param randomNumber A random number for file naming.
     */
    public static void pointAndDraw(List<Integer> list, int randomNumber) {
        // Add points
        List<Point2D.Double> points = new ArrayList<>();
        Floor2ReadSheet1 readSheet1 = new Floor2ReadSheet1();
        List<Didian> didianList = readSheet1.read();

        Didian[] didians = new Didian[123];
        int i = 1;
        for (Didian di : didianList) {
            didians[i] = di;
            i++;
        }

        for (Integer in : list) {
            Double x = didians[in].getX();
            Double y = didians[in].getY();
            Point2D.Double aDouble = new Point2D.Double(x * 112, y * 111);
            points.add(aDouble);
        }

        draw(points, randomNumber);
    }

    /**
     * Draws the points and saves the resulting image.
     *
     * @param points       The list of points to draw.
     * @param randomNumber A random number for file naming.
     */
    public static void draw(List<Point2D.Double> points, int randomNumber) {
        System.setProperty("java.awt.headless", "true");

        // Create Floor2CoordinatePanelWithBackground object and draw image
        Floor2CoordinatePanelWithBackground panel = new Floor2CoordinatePanelWithBackground(points);
        String stringPath = "D:\\IdeaProjects\\HTML\\redMove\\floor2_path" + randomNumber + ".png";
        panel.saveImage(stringPath);
    }
}