package alc;

import alc.screens.GameScreen;
import alc.screens.TitleScreen;
import engine.Application;
import engine.support.Vec2d;

public class App extends Application {
  public App(String title) {
    super(title);
    showTitleScreen();
  }

  public App(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
    showTitleScreen();
  }

  public void showTitleScreen() {
    setScreen(new TitleScreen(this));
  }

  public void showGameScreen() {
    setScreen(new GameScreen(this));
  }
}
