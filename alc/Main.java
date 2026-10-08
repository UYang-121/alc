package alc;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;
import engine.support.Vec2d;

public class Main {
  public static void main(String[] args) {
    FXFrontEnd app = new App("Alchemy", new Vec2d(960, 600), true, false);
    FXApplication application = new FXApplication();
    application.begin(app);
  }
}
