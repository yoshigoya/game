package one.senri.model;

import one.senri.model.SpaceObject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Star extends SpaceObject {
  private static final Logger logger = LogManager.getLogger(Star.class);
  private int radius;
  private Color color;
  private static final  Random r = new Random(System.currentTimeMillis());

  public Star() {
    this(0, 0);
  }

  public Star(double x, double y) {
    super(x, y);
    radius = 1;
    radius += r.nextInt(4);
    switch(radius) {
      case 1:
        color = Color.YELLOW;
        break;
      case 2:
        color = Color.RED;
        break;
      case 3:
        color = Color.BLUE;
        break;
      case 4:
        color = Color.GRAY;
        break;
      case 5:
        color = Color.ORANGE;
        break;
    }
  }

  public void draw(Graphics2D g) {
    Color old = g.getColor();
    g.setColor(this.color);
    g.fillOval(
        (int)this.getPosition().getX(),
        (int)this.getPosition().getY(),
        radius,
        radius);
    g.setColor(old);
  }
}
