package one.senri.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import one.senri.model.SpaceCraft;
import one.senri.utility.Utility2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpaceFighter extends SpaceCraft {
  private static final Logger logger = LogManager.getLogger(SpaceFighter.class);
  private boolean launchingMissile;

  public SpaceFighter() {
    super();
    createFighterPolygons(getPosition());
    velocity = new Vector2D(0.0, 0.0);
    launchingMissile = false;
  }

  public void launchMissile() {
    this.launchingMissile = true;
  }

  public void stopMissile() {
    this.launchingMissile = false;
  }

  public boolean isLaunchingMissile() {
    return this.launchingMissile;
  }

  public void draw(Graphics2D g) {
    Polygon[] newPolygons = new Polygon[] {
      new Polygon(polygons[0].xpoints, polygons[0].ypoints, polygons[0].npoints),
      new Polygon(polygons[1].xpoints, polygons[1].ypoints, polygons[1].npoints)
    };

    for (Polygon p : newPolygons) {
      p.translate((int)getPosition().getX(), (int)getPosition().getY());
    }

    Color old = g.getColor();
    g.setColor(Color.WHITE);
    g.fill(newPolygons[0]);
    g.setColor(Color.BLUE);
    g.fill(newPolygons[1]);
    g.setColor(old);
  }

  private void createFighterPolygons(Point2D.Double position) {
    polygons = new Polygon[] {new Polygon(), new Polygon() };
    Polygon outerDiamond = Utility2D.createPolygon(Utility2D.createHDiamondPoints(30, 10, position));
    Point2D[] wingPoints = new Point2D[] {
      new Point2D.Double(position.getX(), position.getY()),
      new Point2D.Double(position.getX() - 0.333 * 30, position.getY() - 15),
      new Point2D.Double(position.getX() + 0.333 * 30, position.getY()),
      new Point2D.Double(position.getX() - 0.333 * 30, position.getY() + 15)
    };
    Polygon wingPolygon = Utility2D.createPolygon(wingPoints);

    Area f = new Area(outerDiamond);
    f.add(new Area(wingPolygon));
    polygons[0] = Utility2D.areaToPolygon(f);
    Point2D[] outerDiamondPoints = Utility2D.createHDiamondPoints(20, 6, position);
    Utility2D.addPointsToPolygon(polygons[1], outerDiamondPoints);
  }
}
