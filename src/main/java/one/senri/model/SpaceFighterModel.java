package one.senri.model;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import one.senri.model.Missile;
import one.senri.model.Star;
import one.senri.model.DarkFighter;
import one.senri.utility.Sprite;
import one.senri.utility.SoundEffect;
import one.senri.event.SpaceFighterEvent;
import one.senri.event.SpaceFighterEventListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class SpaceFighterModel {
  private static final Logger logger = LogManager.getLogger(SpaceFighterModel.class);

  private SpaceFighter spaceFighter;
  private int fighterRemainingCount;
  private int spaceWidth;
  private int spaceHeight;

  private int currentLevel;

  private ArrayList<Missile> missiles;
  private ArrayList<Missile> enemyMissiles;
  private ArrayList<Star> stars;
  private ArrayList<DarkFighter> fighters;

  private ArrayList<Sprite> sprites;

  private Random rand;
  private int tickCount;
  private int score;

  private boolean darkFighterSpawnFlag;
  private boolean inInitializationFlag;
  private boolean gameOverFlag;
  private boolean pauseFlag;

  private Rectangle2D quitButton;
  private Rectangle2D restartButton;

  private Rectangle2D gameOverBounds;
  private Rectangle2D restartBounds;
  private Rectangle2D quitBounds;

  private static final String GAME_OVER_TEXT = "Game Over";
  private static final String RESTART_TEXT = "Restart";
  private static final String QUIT_TEXT = "Quit";
  private static final String SPACE_FIGHTER_TEXT = "Space Fighter";
  private SoundEffect soundEffect;

  private ArrayList<SpaceFighterEventListener> listeners;

  public SpaceFighterModel() {
    listeners = new ArrayList<SpaceFighterEventListener>();
    listeners.clear();

    spaceWidth = 640;
    spaceHeight = 480;
    tickCount = 0;
    score = 0;
    fighterRemainingCount = 2;
    currentLevel = 1;
    darkFighterSpawnFlag = true;
    inInitializationFlag = false;
    pauseFlag = false;
    gameOverFlag = false;
    soundEffect = new SoundEffect("sounds/explosion2.wav");

    rand = new Random(System.currentTimeMillis());
    
    spaceFighter = new SpaceFighter();
    spaceFighter.moveTo(spaceWidth / 2 - 30, spaceHeight / 2);

    missiles = new ArrayList<Missile>();
    missiles.clear();

    enemyMissiles = new ArrayList<Missile>();
    enemyMissiles.clear();

    stars = new ArrayList<Star>();
    stars.clear();
    initScene();

    fighters = new ArrayList<DarkFighter>();
    fighters.clear();

    // Load sprite images
    sprites = new ArrayList<Sprite>();
    sprites.clear();
 }

 public void addSpaceFighterEventListener(SpaceFighterEventListener listener) {
   if (!listeners.contains(listener)) {
     listeners.add(listener);
   }
 }

  public void accelerateUpSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.accelerate(SpaceFighter.DIRECTION_UP);
    }
  }
  public void accelerateDownSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.accelerate(SpaceFighter.DIRECTION_DOWN);
    }
  }
  public void accelerateLeftSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.accelerate(SpaceFighter.DIRECTION_LEFT);
    }
  }
  public void accelerateRightSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.accelerate(SpaceFighter.DIRECTION_RIGHT);
    }
  }
  public void decelerateUpSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.decelerate(SpaceFighter.DIRECTION_UP);
    }
  }
  public void decelerateDownSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.decelerate(SpaceFighter.DIRECTION_DOWN);
    }
  }
  public void decelerateLeftSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.decelerate(SpaceFighter.DIRECTION_LEFT);
    }
  }
  public void decelerateRightSpaceFighter() {
    if (!inInitializationFlag) {
      spaceFighter.decelerate(SpaceFighter.DIRECTION_RIGHT);
    }
  }


  public void tick() {
    tickCount++;
    // Move stars
    addStar();
    for (Star s : stars) {
      s.moveTo(s.getPosition().x - 0.5, s.getPosition().y);
    }
    stars.removeIf(s -> (s.getPosition().x < 0));

    // Move missiles
    for (Missile m : missiles) {
      m.moveTo(m.getPosition().x + m.getSpeed(), m.getPosition().y);
    }

    // Move missiles
    for (Missile m : enemyMissiles) {
      m.moveTo(m.getPosition().x + m.getSpeed(), m.getPosition().y);
    }


    // Add dark fighters
    if (rand.nextInt(100) == 25 && darkFighterSpawnFlag) {
      Point2D.Double position = new Point2D.Double(700, rand.nextInt(480));
      DarkFighter df = new DarkFighter(spaceFighter.getPosition(), position);
      fighters.add(df);
    }

    // Move dark fighters
    for (DarkFighter df : fighters) {
      if (!inInitializationFlag) {
        if (rand.nextInt(70) == 1) {
          Missile missile = new Missile(df, -2, 1);
          enemyMissiles.add(missile);
        }
      }
      Point2D.Double currentPosition = df.getPosition();
      df.moveTo(currentPosition.x + df.getVelocity().getX(), currentPosition.y + df.getVelocity().getY());
    }

    // Move SpaceFighter
    if (!inInitializationFlag) {
      Vector2D velocity = spaceFighter.getVelocity();
      double dx = velocity.getX() / 10;
      double dy = velocity.getY() / 10;
      Point2D.Double position = spaceFighter.getPosition();
      double x = position.x;
      double y = position.y;

     if ((position.x + dx > 20) && (position.x + dx < spaceWidth / 2)) {
        x += dx;
      }
      if ((y + dy > 40) && ((position.y + dy) < (spaceHeight - 40))) {
        y += dy;
      }
      spaceFighter.moveTo(x, y);
    }

    if (tickCount == 1 && this.spaceFighter.isLaunchingMissile()) {
      missiles.add(new Missile(spaceFighter, 4, 1));
    }

    // colision detect
    // missiles <-> Dark fighters
    for (Missile m : missiles) {
      for (DarkFighter df : fighters) {
        if (df.contains(m.getPosition())) {
          // Missile hit the dark figher
          df.setEndurance(df.getEndurance() - m.getDamage());
          m.setEndurance(0);
          sprites.add(new Sprite("sprites/explosion-sprite.png", df.getPosition()));
          soundEffect.play(false);
          score += 10;
        }
      }
    }
    // enemy missiles <-> Space fighters
    if (!inInitializationFlag) {
      for (Missile m : enemyMissiles) {
        if (spaceFighter.contains(m.getPosition())) {
          logger.debug("Missile hit the space figher");
          spaceFighter.setEndurance(spaceFighter.getEndurance() - m.getDamage());
          m.setEndurance(0);
          this.spaceFighter.stopMissile();
          sprites.add(new Sprite("sprites/explosion-sprite.png", spaceFighter.getPosition()));
          soundEffect.play(false);
          spaceFighter.moveTo(-120, 240);
          spaceFighter.resetVelocity();
          if (fighterRemainingCount > 0) {
            fighterRemainingCount--;
            spaceFighter.accelerate(SpaceFighter.DIRECTION_RIGHT);
          }
          else {
            gameOverFlag = true;
          }
          darkFighterSpawnFlag = false;
          inInitializationFlag = true;
        }
      }
    }

    if (inInitializationFlag) {
      if (spaceFighter.getPosition().x == 30) {
        spaceFighter.decelerate(SpaceFighter.DIRECTION_RIGHT);
        inInitializationFlag = false;
        darkFighterSpawnFlag = true;
      }
    }

    // remove dark fighters which whent out of space
    fighters.removeIf(df -> (df.getEndurance() <= 0));
    fighters.removeIf(df -> (df.getPosition().x < 0));
    // remove missiles which went out of space
    missiles.removeIf(m -> (m.getEndurance() <= 0));
    missiles.removeIf(m -> (m.getPosition().x < 0 || m.getPosition().x > spaceWidth));
    // remove enemy missiles which went out of space
    enemyMissiles.removeIf(m -> (m.getEndurance() <= 0));
    enemyMissiles.removeIf(m -> (m.getPosition().x < 0 || m.getPosition().x > spaceWidth));

    sprites.removeIf(s -> s.isDone());
    
    if (tickCount == 10) {
      tickCount = 0;
    }
  }

  public void draw(Graphics2D g) {
    if (!pauseFlag) {
      this.tick();
    }

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
    
    for (Missile m: enemyMissiles) {
      m.draw(g);
    }

    for (Sprite s : sprites) {
      s.draw(g);
      if (!pauseFlag) {
        s.getNextFrame();
      }
    }

    this.drawGameInformation(g);
  }

  private void drawGameInformation(Graphics2D g) {
    Font oldFont = g.getFont();
    Font newFont = oldFont.deriveFont(14.0f);
    Font bigFont = oldFont.deriveFont(24.0f);
    g.setFont(newFont);
    
    String scoreText = String.format("Score: %d", score);
    g.drawString(scoreText, 10, 20);

    String fighterCountText = String.format("Fighter x %d", fighterRemainingCount);
    g.drawString(fighterCountText, 550, 20);

    if (gameOverFlag) {
      g.setFont(bigFont);
      FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

      if (gameOverBounds == null) {
        gameOverBounds = bigFont.getStringBounds(GAME_OVER_TEXT, frc);
        restartBounds = bigFont.getStringBounds(RESTART_TEXT, frc);
        quitBounds = bigFont.getStringBounds(QUIT_TEXT, frc);

        restartButton = new Rectangle2D.Double(
            160 - restartBounds.getWidth() / 2 - 10,
            360 - restartBounds.getHeight() - 15,
            restartBounds.getWidth() + 20,
            restartBounds.getHeight() + 10);
        quitButton = new Rectangle2D.Double(
            480 - quitBounds.getWidth() / 2 - 10,
            360 - quitBounds.getHeight() - 15,
            quitBounds.getWidth() + 20,
            quitBounds.getHeight() + 10);
      }

      g.draw(quitButton);
      g.draw(restartButton);
      
      g.drawString(GAME_OVER_TEXT, (int)(320 - gameOverBounds.getWidth() / 2), (int)(240 - gameOverBounds.getHeight() / 2));
      g.drawString(RESTART_TEXT, (int)(160 - restartBounds.getWidth() / 2), (int)(360 - restartBounds.getHeight() / 2));
      g.drawString(QUIT_TEXT, (int)(480 - quitBounds.getWidth() / 2), (int)(360 - quitBounds.getHeight() / 2));

      g.setFont(newFont);
    }

    g.setFont(oldFont);
  }

  public void clicked(Point p) {
    if (quitButton != null && quitButton.contains(p)) {
      soundEffect.disposeSoundEffect();
      for (SpaceFighterEventListener l : listeners) {
        l.quit(new SpaceFighterEvent(this));
      }
    }

    if (restartButton != null && restartButton.contains(p)) {
      logger.debug("Restart button clicked");
      score = 0;
      fighterRemainingCount = 2;
      currentLevel = 1;
      fighters.clear();
      missiles.clear();
      sprites.clear();
      enemyMissiles.clear();
      darkFighterSpawnFlag = true;
      
      spaceFighter.moveTo(-120, 240);
      spaceFighter.resetVelocity();
      spaceFighter.accelerate(SpaceFighter.DIRECTION_RIGHT);

      //this.inInitializationFlag = false;
      this.gameOverFlag = false;
    }
  }

  public void pause() {
    pauseFlag = !pauseFlag;
  }

  public void launchMissile() {
    if (!inInitializationFlag) {
      this.spaceFighter.launchMissile();
    }
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
    if (rand.nextInt(30) == 5) {
      stars.add(new Star(640, rand.nextInt(480)));
    }
  }  
}
