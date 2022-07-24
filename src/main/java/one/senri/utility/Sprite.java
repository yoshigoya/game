package one.senri.utility;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.net.URL;
import java.io.IOException;

import one.senri.model.SpaceObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sprite extends SpaceObject {
  private static final Logger logger = LogManager.getLogger(Sprite.class);
  private BufferedImage image;
  private BufferedImage[] frames;

  private static final int DEFAULT_FRAME_WIDTH = 32;
  private static final int DEFAULT_FRAME_HEIGHT = 32;

  private int currentFrame;
  private int tickCount;
  private final int tickSkipCount = 4;
  private boolean done;
  
  public Sprite() {
    this("");
  }

  public Sprite(String filename) {
    this(filename, new Point2D.Double());
  }

  public Sprite(String filename, Point2D.Double position) {
    super(position);
    if (filename.length() != 0) {
      frames = new BufferedImage[16];
      loadImage(filename);
      loadFrames();
      currentFrame = 0;
      tickCount = 0;
      done = false;
    }
  }

  public boolean isDone() {
    return this.done;
  }

  public boolean loadImage(String filename) {
    boolean status = false;
    URL url = getClass().getClassLoader().getResource(filename);
    if (url == null) {
      logger.error("Failed to load image: {}", filename);
    }
    else {
      try {
        logger.debug("URL: {}", url);
        image = ImageIO.read(url);
        logger.debug("Width: {} Height: {}", image.getWidth(), image.getHeight());
        status = true;
      }
      catch (IOException e) {
        logger.error(e.getMessage());
      }
    }
    return status;
  }

  public BufferedImage getFrame(int frameNumber) {
    return this.frames[frameNumber];
  }

  public BufferedImage getNextFrame() {
    tickCount++;
    if (tickCount == (tickSkipCount - 1)) {
      if (currentFrame == (frames.length - 1)) {
        currentFrame = 0;
        done = true;
      }
      else {
        currentFrame++;
      }
      tickCount = 0;
    }
    return this.frames[currentFrame];
  }

  private void loadFrames() {
    if (image != null) {
      for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
          this.frames[4 * i + j] = image.getSubimage(
              DEFAULT_FRAME_WIDTH * j,
              DEFAULT_FRAME_HEIGHT * i,
              DEFAULT_FRAME_WIDTH,
              DEFAULT_FRAME_HEIGHT);
        }
      }
    }
  }

  public void draw(Graphics2D g) {
    g.drawImage(
        this.frames[currentFrame],
        null,
        (int)(this.getPosition().x - DEFAULT_FRAME_WIDTH / 2),
        (int)(this.getPosition().y - DEFAULT_FRAME_HEIGHT / 2));
  }

  public Image getImage() {
    return this.image;
  }
}
