package util;

import pojo.Didian;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class floor5_Draw {
    public static void test(int st, int ed, int randomNumber){
        pointAndDraw(E_Dijkstra.dijkstra(st, ed), randomNumber);
    }
    public static void pointAndDraw(List<Integer> list, int randomNumber) {
        //娣诲姞鐐�
        List<Point2D.Double> points = new ArrayList<>();
        floor5_ReadSheet1 readSheet1 = new floor5_ReadSheet1();
        List<Didian> didianList = readSheet1.read();

        Didian[] didians = new Didian[95];
        int i = 1;
        for (Didian di : didianList) {
            didians[i] = di;
            i++;
        }

        for (Integer in : list) {
            Double x = didians[in].getX();
            Double y = didians[in].getY();
            Point2D.Double aDouble = new Point2D.Double(x*55.6, y*55.5);
            points.add(aDouble);
        }

        draw(points, randomNumber);
    }

    public static void draw(List<Point2D.Double> points, int randomNumber) {
        System.setProperty("java.awt.headless", "true");

        // 鍒涘缓 CoordinatePanelWithBackground 瀵硅薄骞剁粯鍒跺浘鍍�
        floor5_CoordinatePanelWithBackground panel = new floor5_CoordinatePanelWithBackground(points);
        String StringPath = "D:\\IdeaProjects\\HTML\\redMove\\floor5_path" + randomNumber + ".png";
        panel.drawToImage(StringPath);
    }
}