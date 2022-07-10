package one.senri.utility;

import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.PathIterator;
import java.awt.geom.Area;

public class Utility2D {
  public static Point2D[] createVDiamondPoints(int width, int height, Point2D center) {
    Point2D[] diamondPoints = new Point2D[] {
      new Point2D.Double(center.getX(), center.getY() - height * 0.666),
      new Point2D.Double(center.getX() + width / 2, center.getY()),
      new Point2D.Double(center.getX(), center.getY() + height * 0.333),
      new Point2D.Double(center.getX() - width / 2, center.getY())
    };
    return diamondPoints;
  }

  public static Point2D[] createHDiamondPoints(int width, int height, Point2D center) {
    Point2D[] diamondPoints = new Point2D[] {
      new Point2D.Double(center.getX() - width * 0.333, center.getY()),
      new Point2D.Double(center.getX(), center.getY() - height / 2),
      new Point2D.Double(center.getX() + width * 0.666, (int)center.getY()),
      new Point2D.Double(center.getX(), center.getY() + height / 2)
    };
    return diamondPoints;
  }

  public static Polygon createPolygon(Point2D[] points) {
    Polygon p = new Polygon();
    for (int i = 0; i < points.length; i++) {
      p.addPoint((int)points[i].getX(), (int)points[i].getY());
    }
    // Close polygon
    p.addPoint((int)points[0].getX(), (int)points[0].getY());
    return p;
  }

  public static void addPointsToPolygon(Polygon p, Point2D[] points) {
    for (int i = 0; i < points.length; i++) {
      p.addPoint((int)points[i].getX(), (int)points[i].getY());
    }
    // Close polygon
    p.addPoint((int)points[0].getX(), (int)points[0].getY());
  }

  public static Polygon areaToPolygon(Area area) {
    Polygon polygon = new Polygon();
    PathIterator pi = area.getPathIterator(null);
    while(!pi.isDone()) {
      double[] points = new double[6];
      int type = pi.currentSegment(points);
      if (type != PathIterator.SEG_CLOSE) {
        polygon.addPoint((int)points[0], (int)points[1]);
      }
      pi.next();
    }
    return polygon;
  }
}

