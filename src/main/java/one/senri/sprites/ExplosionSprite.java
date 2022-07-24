package one.senri.sprites;

import java.awt.geom.Point2D;
import one.senri.utility.Sprite;

public class ExplosionSprite extends Sprite {
  private static final String spritePath = "sprites/explosion-sprite.png";

  public ExplosionSprite(Point2D.Double position) {
    super(spritePath, position);
  }
}
