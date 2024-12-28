
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

public class floor5_CoordinatePanelWithBackground {
    private final List<Point2D.Double> points;
    private BufferedImage backgroundImage;
    // 鐢ㄤ簬瀛樺偍宸茬粡缁樺埗杩囩殑绾挎
    private Set<Line> drawnLines = new HashSet<>();

    public floor5_CoordinatePanelWithBackground(List<Point2D.Double> points) {
        this.points = points;

        // 鍔犺浇鑳屾櫙鍥�
        try {
            backgroundImage = ImageIO.read(new File("D:\\IdeaProjects\\HTML\\redMove\\floor5.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawToImage(String outputPath) {
        int width = 1154 + 50; // 鍥惧儚瀹藉害
        int height = 806 + 50; // 鍥惧儚楂樺害
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // 鑾峰彇鍥惧儚鐨勭粯鍥句笂涓嬫枃
        Graphics2D g2d = bufferedImage.createGraphics();

        // 瀹氫箟鍧愭爣杞寸殑璧峰浣嶇疆
        int margin = 50; // 鐣欏嚭杈硅窛
        int xAxisY = height - margin; // x杞寸殑浣嶇疆
        int yAxisX = margin; // y杞寸殑浣嶇疆

        // 缁樺埗鑳屾櫙鍥�
        if (backgroundImage != null) {
            int bgWidth = backgroundImage.getWidth();
            int bgHeight = backgroundImage.getHeight();
            // 纭繚鑳屾櫙鍥剧殑宸︿笅瑙掑榻愬埌 (0, 0)
            g2d.drawImage(backgroundImage, yAxisX, xAxisY - bgHeight, bgWidth, bgHeight, null);
        }

        // 缁樺埗鐐�
        g2d.setColor(Color.RED);
        int ii = 0;
        for (Point2D.Double point : points) {
            if(ii == 0){
                g2d.setColor(Color.yellow);
            } else if(ii == points.size() - 1){
                g2d.setColor(Color.BLUE);
            }
            int x = (int) (yAxisX + point.x); // 灏嗙偣鐨� x 鍧愭爣杞崲涓洪潰鏉垮潗鏍�
            int y = (int) (xAxisY - point.y); // 灏嗙偣鐨� y 鍧愭爣杞崲涓洪潰鏉垮潗鏍�
            if(ii == 0 || ii == points.size() - 1){
                g2d.fillOval(x - 10, y - 10, 20, 20);
            } else {
                g2d.fillOval(x - 5, y - 5, 10, 10);
            }
            if(ii == 0 || ii == points.size() - 1){
                g2d.setColor(Color.red);
            }
            ii ++;
        }

        // 缁樺埗鍔犵矖鐨勮繛绾�
        g2d.setColor(Color.RED); // 鍙互閫夋嫨涓嶅悓鐨勯鑹叉潵缁樺埗杩炵嚎
        BasicStroke thickStroke = new BasicStroke(4.0f); // 璁惧畾绾挎潯瀹藉害涓� 4.0f
        g2d.setStroke(thickStroke);

        for (int i = 1; i < points.size(); i++) {
            Point2D.Double point1 = points.get(i - 1);
            Point2D.Double point2 = points.get(i);
            int x1 = (int) (yAxisX + point1.x);
            int y1 = (int) (xAxisY - point1.y);
            int x2 = (int) (yAxisX + point2.x);
            int y2 = (int) (xAxisY - point2.y);

            Line line = new Line(point1, point2);

            // 妫€鏌ユ槸鍚﹀凡缁忕粯鍒惰繃璇ョ嚎娈�
            if (!drawnLines.contains(line)) {
                // 鐢荤嚎
                g2d.drawLine(x1, y1, x2, y2);
                // 灏嗚绾挎鏍囪涓哄凡缁樺埗
                drawnLines.add(line);
            }
        }

        g2d.dispose();

        // 淇濆瓨 BufferedImage 鍒版枃浠�
        try {
            ImageIO.write(bufferedImage, "png", new File(outputPath));
            System.out.println("鍥惧儚宸蹭繚瀛樺埌: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 琛ㄧず绾挎鐨勭被
    private static class Line {
        private final Point2D.Double point1;
        private final Point2D.Double point2;

        public Line(Point2D.Double point1, Point2D.Double point2) {
            // 淇濇寔鐐圭殑椤哄簭浠ョ‘淇濇棤搴忔€�
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