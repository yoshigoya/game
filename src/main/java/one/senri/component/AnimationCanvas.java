package one.senri.component;

import one.senri.utility.Utility2D;
import one.senri.model.SpaceFighter;
import one.senri.model.SpaceFighterModel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimationCanvas extends Canvas {
  private Color backgroundColor;
  private static final Logger logger = LogManager.getLogger(AnimationCanvas.class);
  private Point center;
  private SpaceFighterModel model;

  public AnimationCanvas(SpaceFighterModel model) {
    super();
    this.backgroundColor = Color.BLACK;
    this.model = model;
  }

  @Override
  public void paint(Graphics g) {
    Dimension panelSize = this.getSize();
    center = new Point(panelSize.width / 2, panelSize.height / 2);

    super.paint(g);
    Graphics2D g2d = (Graphics2D)g.create();
    g2d.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(
        RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
    
    // Background
    g2d.setColor(this.backgroundColor);
    g2d.fillRect(0, 0, panelSize.width, panelSize.height);

    g2d.setColor(Color.WHITE);

    model.draw(g2d);
    
    g2d.dispose();
  }
}
