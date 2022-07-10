package one.senri.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import one.senri.model.SpaceObject;

public class Missile extends SpaceObject {
  private int speed;
  private int damage;

  public Missile(SpaceObject origin, int speed, int damage) {
    super(origin.getPosition());
    this.speed = speed;
    this.damage = damage;
  }

  public int getSpeed() { return speed; }
  public int getDamage() { return damage; }

  public void draw(Graphics2D g) {
    Point2D.Double p = this.getPosition();
    Color old = g.getColor();
    g.setColor(Color.WHITE);
    g.drawLine((int)p.x, (int)p.y, (int)(p.x - this.speed), (int)p.y);
    g.setColor(old);
  }
}
