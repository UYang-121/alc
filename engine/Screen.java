package engine;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/** A single view in an application, such as a menu or game screen. */
public abstract class Screen {

  protected final Application application;
  protected Vec2d size;

  protected Screen(Application application) {
    this.application = application;
    size = application.getWindowSize();
  }

  public void onEnter() { }

  public void onExit() { }

  public void onTick(long nanosSincePreviousTick) { }

  public void onLateTick() { }

  public abstract void onDraw(GraphicsContext g);

  public void onKeyTyped(KeyEvent event) { }

  public void onKeyPressed(KeyEvent event) { }

  public void onKeyReleased(KeyEvent event) { }

  public void onMouseClicked(MouseEvent event) { }

  public void onMousePressed(MouseEvent event) { }

  public void onMouseReleased(MouseEvent event) { }

  public void onMouseDragged(MouseEvent event) { }

  public void onMouseMoved(MouseEvent event) { }

  public void onMouseWheelMoved(ScrollEvent event) { }

  public void onFocusChanged(boolean focused) { }

  public void onResize(Vec2d newSize) {
    size = new Vec2d(newSize);
  }
}