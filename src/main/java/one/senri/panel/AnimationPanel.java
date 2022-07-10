package one.senri.panel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.BorderLayout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnimationPanel extends JPanel {

  private static final Logger logger = LogManager.getLogger(AnimationPanel.class);
  public AnimationPanel() {
    super();
  }
  
  @Override
  public void paint(Graphics g) {
    super.paint(g);
  }
}
