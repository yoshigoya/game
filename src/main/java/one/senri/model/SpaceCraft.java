package one.senri.model;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import one.senri.model.SpaceObject;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class SpaceCraft extends SpaceObject {
  protected Polygon[] polygons;
  protected Vector2D velocity;

  private static final double DEFAULT_VELOCITY = 10.0;
  
  public static final int DIRECTION_UP = 0;
  public static final int DIRECTION_DOWN = 1;
  public static final int DIRECTION_LEFT = 2;
  public static final int DIRECTION_RIGHT = 3;

  public SpaceCraft() {
    this(0, 0);
  }

  public SpaceCraft(double x, double y) {
    super(x, y);
  }

  public SpaceCraft(Point2D.Double position) {
    super(position);
  }

  @Override
  public void moveTo(double x, double y) {
    super.moveTo(x, y);
  }

  @Override
  public void moveTo(Point2D.Double position) {
    this.moveTo(position.x, position.y);
  }

  public Vector2D getVelocity() {
    return this.velocity;
  }

  public void moveUp(double delta) {
    this.moveTo(getPosition().x, getPosition().y - delta);
  }

  public void moveDown(double delta) {
    this.moveTo(getPosition().x, getPosition().y + delta);
  }

  public void moveLeft(double delta) {
    this.moveTo(getPosition().x - delta, getPosition().y);
  }

  public void moveRight(double delta) {
    this.moveTo(getPosition().x + delta, getPosition().y);
  }

  public void decelerate(int direction) {
    switch(direction) {
      case DIRECTION_UP:
        this.velocity = this.velocity.add(new Vector2D(0.0, DEFAULT_VELOCITY));
        break;
      case DIRECTION_DOWN:
        this.velocity = this.velocity.add(new Vector2D(0.0, -DEFAULT_VELOCITY));
        break;
      case DIRECTION_LEFT:
        this.velocity = this.velocity.add(new Vector2D(DEFAULT_VELOCITY, 0.0));
        break;
      case DIRECTION_RIGHT:
        this.velocity = this.velocity.add(new Vector2D(-DEFAULT_VELOCITY, 0.0));
        break;
      default:
        break;
    }
  }

  public void accelerate(int direction) {
    switch(direction) {
      case DIRECTION_UP:
        if (this.velocity.getY() > -DEFAULT_VELOCITY) {
          this.velocity = this.velocity.add(new Vector2D(0.0, -DEFAULT_VELOCITY));
        }
        break;
      case DIRECTION_DOWN:
        if (this.velocity.getY() < DEFAULT_VELOCITY) {
          this.velocity = this.velocity.add(new Vector2D(0.0, DEFAULT_VELOCITY));
        }
        break;
      case DIRECTION_LEFT:
        if (this.velocity.getX() > -DEFAULT_VELOCITY) {
          this.velocity = this.velocity.add(new Vector2D(-DEFAULT_VELOCITY, 0.0));
        }
        break;
      case DIRECTION_RIGHT:
        if (this.velocity.getX() < DEFAULT_VELOCITY) {
          this.velocity = this.velocity.add(new Vector2D(DEFAULT_VELOCITY, 0.0));
        }
        break;
      default:
        break;
    }
  }

  public void resetVelocity() {
    velocity = new Vector2D(0.0, 0.0);
  }
}
