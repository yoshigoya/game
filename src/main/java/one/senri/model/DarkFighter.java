package one.senri.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import one.senri.utility.Utility2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DarkFighter extends SpaceCraft {
  private static final Logger logger = LogManager.getLogger(DarkFighter.class);

  public DarkFighter() {
    this(new Point2D.Double(), new Point2D.Double());
  }

  public DarkFighter(Point2D.Double target, Point2D.Double position) {
    super(position);
    createDarkFighterPolygon();
    velocity = new Vector2D(target.x - this.getPosition().x, target.y - this.getPosition().y).normalize();
  }

  private void createDarkFighterPolygon() {
    Point2D.Double p = new Point2D.Double(0.0, 0.0);

    Point2D[] bodyPoints = new Point2D[] {
      new Point2D.Double(p.x - 15.0, p.y),
      new Point2D.Double(p.x + 10, p.y - 5),
      new Point2D.Double(p.x + 10, p.y + 5),
      new Point2D.Double(p.x - 15.0, p.y)
    };

    Point2D[] wingPoints = new Point2D[] {
      new Point2D.Double(p.x + 10, p.y),
      new Point2D.Double(p.x - 5, p.y + 15),
      new Point2D.Double(p.x, p.y),
      new Point2D.Double(p.x - 5, p.y - 15),
      new Point2D.Double(p.x + 10, p.y)
    };

    Area f = new Area(Utility2D.createPolygon(bodyPoints));
    Area w = new Area(Utility2D.createPolygon(wingPoints));
    f.add(w);
    
    this.polygons = new Polygon[] {
      Utility2D.areaToPolygon(f),
    };
  }

  public void draw(Graphics2D g) {
    Polygon p =
      new Polygon(polygons[0].xpoints, polygons[0].ypoints, polygons[0].npoints);
    p.translate((int)getPosition().getX(), (int)getPosition().getY());
    Color old = g.getColor();
    g.setColor(Color.RED);
    g.fill(p);
    g.setColor(Color.BLUE);
    g.fillOval((int)getPosition().getX(), (int)getPosition().getY() - 3, 6, 6);
    g.setColor(old);
  }
}
