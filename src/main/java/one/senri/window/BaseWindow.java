package one.senri.window;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;

public class BaseWindow extends JFrame {
  public BaseWindow(String title, int width, int height) {
    this(title, 0, 0, width, height, true);
    setLocationRelativeTo(null);
  }

  public BaseWindow(String title, int width, int height, boolean resizable) {
    this(title, 0, 0, width, height, resizable);
    setLocationRelativeTo(null);
  }

  public BaseWindow(String title, int x, int y, int width, int height, boolean resizable) {
    super();
    
    setTitle(title);
    setSize(width, height);
    setLocation(x, y);
    setResizable(resizable);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public BaseWindow() {
    this("", 0, 0, 640, 480, true);
  }
}
