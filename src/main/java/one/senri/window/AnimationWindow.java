package one.senri.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

import one.senri.component.AnimationCanvas;
import one.senri.model.SpaceFighterModel;
import one.senri.window.BaseWindow;
import one.senri.event.SpaceFighterEventListener;
import one.senri.event.SpaceFighterEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimationWindow extends BaseWindow implements ActionListener, KeyListener, MouseListener, SpaceFighterEventListener {
  static Logger logger = LogManager.getLogger(AnimationWindow.class);

  private int framesPerSec;

  // Default Frames per seconds
  private final int DEFAULT_FPS = 60;

  private Timer animationTimer;
  private boolean animationRunning;
  private AnimationCanvas canvas;
  private SpaceFighterModel gameModel;

  public AnimationWindow(String title, int width, int height) {
    this(title, 0, 0, width, height);
    setLocationRelativeTo(null);
  }

  public AnimationWindow(String title, int x, int y, int width, int height) {
    super(title, x, y, width, height, false);
    this.framesPerSec = DEFAULT_FPS;
    this.animationTimer = new Timer(1000 / this.framesPerSec, this);
    this.animationRunning = false;
    this.gameModel = new SpaceFighterModel();
    canvas = new AnimationCanvas(this.gameModel);
    add(canvas, BorderLayout.CENTER);

    addKeyListener(this);
    canvas.addMouseListener(this);
    this.gameModel.addSpaceFighterEventListener(this);
  }

  public void startAnimation() {
    if (!animationRunning) {
      this.canvas.createBufferStrategy();
      this.animationTimer.start();
      animationRunning = true;
      logger.debug("Aimation started");
    }
  }

  public void stopAnimation() {
    if (animationRunning) {
      this.animationTimer.stop();
      animationRunning = true;
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    repaint();
    canvas.repaint();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_UP:
      case KeyEvent.VK_W:
        this.gameModel.accelerateUpSpaceFighter();
        break;
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_S:
        this.gameModel.accelerateDownSpaceFighter();
        break;
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_A:
        this.gameModel.accelerateLeftSpaceFighter();
        break;
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_D:
        this.gameModel.accelerateRightSpaceFighter();
        break;
      case KeyEvent.VK_SPACE:
        this.gameModel.launchMissile();
        break;
      case KeyEvent.VK_Q:
        quit(new SpaceFighterEvent());
        break;
      case KeyEvent.VK_P:
        this.gameModel.pause();
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch(e.getKeyCode()) {
      case KeyEvent.VK_UP:
      case KeyEvent.VK_W:
        this.gameModel.decelerateUpSpaceFighter();
        break;
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_S:
        this.gameModel.decelerateDownSpaceFighter();
        break;
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_A:
        this.gameModel.decelerateLeftSpaceFighter();
        break;
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_D:
        this.gameModel.decelerateRightSpaceFighter();
        break;
      case KeyEvent.VK_SPACE:
        this.gameModel.stopMissile();
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
  
  public void setFPS(int fps) {
    if (fps > 60) {
      logger.warn("FPS should be <=60. it was set to 60.");
    }
    if (fps <= 0) {
      logger.error("Illegal fps passed: %d", fps);
      throw new IllegalArgumentException("FPS shoudl be greater than 0");
    }
    this.framesPerSec = fps;
  }

  public int getFPS() {
    return this.framesPerSec;
  }

  // MouseListener interfaces
  @Override
  public void mouseClicked(MouseEvent e) {
    gameModel.clicked(e.getPoint());
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void quit(SpaceFighterEvent e) {
    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
}
