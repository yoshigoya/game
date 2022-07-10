package one.senri.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import one.senri.model.Missile;
import one.senri.model.Star;
import one.senri.model.DarkFighter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class SpaceFighterModel {
  private static final Logger logger = LogManager.getLogger(SpaceFighterModel.class);

  private SpaceFighter spaceFighter;
  private int figherRemainingCount;

  private int spaceWidth;
  private int spaceHeight;

  private int currentLevel;

  private ArrayList<Missile> missiles;
  private ArrayList<Star> stars;
  private ArrayList<DarkFighter> fighters;

  private Random rand;
  private int tickCount;

  public SpaceFighterModel() {
    spaceWidth = 640;
    spaceHeight = 480;
    tickCount = 0;

    rand = new Random(System.currentTimeMillis());
    
    spaceFighter = new SpaceFighter();
    spaceFighter.moveTo(spaceWidth / 2 - 30, spaceHeight / 2);

    missiles = new ArrayList<Missile>();
    missiles.clear();

    stars = new ArrayList<Star>();
    stars.clear();
    initScene();

    fighters = new ArrayList<DarkFighter>();
    fighters.clear();

    DarkFighter f = new DarkFighter();
    f.moveTo(600, 240);
    fighters.add(f);
  }

  public void accelerateUpSpaceFighter() {
    spaceFighter.accelerate(SpaceFighter.DIRECTION_UP);
  }
  public void accelerateDownSpaceFighter() {
    spaceFighter.accelerate(SpaceFighter.DIRECTION_DOWN);
  }
  public void accelerateLeftSpaceFighter() {
    spaceFighter.accelerate(SpaceFighter.DIRECTION_LEFT);
  }
  public void accelerateRightSpaceFighter() {
    spaceFighter.accelerate(SpaceFighter.DIRECTION_RIGHT);
  }
  public void decelerateUpSpaceFighter() {
    spaceFighter.decelerate(SpaceFighter.DIRECTION_UP);
  }
  public void decelerateDownSpaceFighter() {
    spaceFighter.decelerate(SpaceFighter.DIRECTION_DOWN);
  }
  public void decelerateLeftSpaceFighter() {
    spaceFighter.decelerate(SpaceFighter.DIRECTION_LEFT);
  }
  public void decelerateRightSpaceFighter() {
    spaceFighter.decelerate(SpaceFighter.DIRECTION_RIGHT);
  }


  public void tick() {
    tickCount++;
    // Move stars
    addStar();
    for (Star s : stars) {
      s.moveTo(s.getPosition().x - 1, s.getPosition().y);
    }
    stars.removeIf(s -> (s.getPosition().x < 0));

    // Move missiles
    for (Missile m : missiles) {
      m.moveTo(m.getPosition().x + m.getSpeed(), m.getPosition().y);
    }

    // Move SpaceFighter
    Vector2D velocity = spaceFighter.getVelocity();
    double dx = velocity.getX() / 10;
    double dy = velocity.getY() / 10;
    Point2D.Double position = spaceFighter.getPosition();
    double x = position.x;
    double y = position.y;

    if ((position.x + dx > 20) && (position.x + dx < spaceWidth / 2)) {
      x += dx;
    }

    if ((position.y > 20) && (position.y < (spaceHeight - 20))) {
      y += dy;
    }
    spaceFighter.moveTo(x, y);

    if (tickCount == 1 && this.spaceFighter.isLaunchingMissile()) {
      missiles.add(new Missile(spaceFighter, 4, 1));
    }

    // colision detect

    // remove missiles which went out of space
    missiles.removeIf(m -> (m.getPosition().x < 0 || m.getPosition().x > spaceWidth));
    if (tickCount == 10) {
      tickCount = 0;
    }
  }

  public void draw(Graphics2D g) {
    this.tick();

    // Background
    for (Star s : stars) {
      s.draw(g);
    }

    // Dark Fighters
    for (DarkFighter f : fighters) {
      f.draw(g);
    }

    spaceFighter.draw(g);
    
    for (Missile m: missiles) {
      m.draw(g);
    }
  }

  public void launchMissile() {
    this.spaceFighter.launchMissile();
  }

  public void stopMissile() {
    this.spaceFighter.stopMissile();
  }

  private void initScene() {
    for (int i = 0; i < 20; i++) {
      stars.add(new Star(rand.nextInt(640), rand.nextInt(480)));
    }
  }

  private void addStar() {
    if (rand.nextInt(20) == 5) {
      stars.add(new Star(640, rand.nextInt(480)));
    }
  }  
}
