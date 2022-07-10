package one.senri.app;

import one.senri.window.AnimationWindow;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        AnimationWindow window = new AnimationWindow("Animation", 640, 480);
        window.setVisible(true);
        window.startAnimation();
      }
    });
  }
}
