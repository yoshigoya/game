package one.senri.model;

import java.awt.geom.Point2D;

public class SpaceObject {
  private Point2D.Double position;
  protected int endurance;

  public SpaceObject() {
    this(new Point2D.Double(0, 0));
  }

  public SpaceObject(double x, double y) {
    this.position = new Point2D.Double(x, y);
    this.endurance = 1;
  }

  public SpaceObject(Point2D.Double position) {
    this.position = new Point2D.Double();
    this.position.x = position.x;
    this.position.y = position.y;
    this.endurance = 1;
  }

  public Point2D.Double getPosition() {
    return this.position;
  }

  public void moveTo(double x, double y) {
    this.position.x = x;
    this.position.y = y;
  }

  public void moveTo(Point2D.Double position) {
    this.moveTo(position.x, position.y);
  }

  public int getEndurance() {
    return this.endurance;
  }

  public void setEndurance(int value) {
    this.endurance = value;
  }
}
