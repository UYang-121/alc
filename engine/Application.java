package engine;

import engine.support.FXFrontEnd;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;


public class Application extends FXFrontEnd {

  private Screen currentScreen;
  private Vec2d windowSize;

  public Application(String title) {
    super(title);
    windowSize = new Vec2d(currentStageSize);
  }

  public Application(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
    super(title, windowSize, debugMode, fullscreen);
    this.windowSize = new Vec2d(currentStageSize);
  }

 public final void setScreen(Screen nextScreen) {
    if (nextScreen == null) {
      throw new IllegalArgumentException("A screen cannot be null");
    }

    if (currentScreen != null) {
      currentScreen.onExit();
    }
    currentScreen = nextScreen;
    currentScreen.onResize(windowSize);
    currentScreen.onEnter();
  }

  public final Vec2d getWindowSize() {
    return windowSize;
  }

  @Override
  protected void onTick(long nanosSincePreviousTick) {
    if (currentScreen != null) {
      currentScreen.onTick(nanosSincePreviousTick);
    }
  }

  @Override
  protected void onLateTick() {
    if (currentScreen != null) {
      currentScreen.onLateTick();
    }
  }

  @Override
  protected void onDraw(GraphicsContext g) {
    if (currentScreen != null) {
      currentScreen.onDraw(g);
    }
  }

  @Override
  protected void onKeyTyped(KeyEvent event) {
    if (currentScreen != null) {
      currentScreen.onKeyTyped(event);
    }
  }

  @Override
  protected void onKeyPressed(KeyEvent event) {
    if (currentScreen != null) {
      currentScreen.onKeyPressed(event);
    }
  }

  @Override
  protected void onKeyReleased(KeyEvent event) {
    if (currentScreen != null) {
      currentScreen.onKeyReleased(event);
    }
  }

    @Override
  protected void onMouseClicked(MouseEvent event) {
    if (currentScreen != null) {
      currentScreen.onMouseClicked(event);
    }
  }

  @Override
  protected void onMousePressed(MouseEvent event) {
    if (currentScreen != null) {
      currentScreen.onMousePressed(event);
    }
  }

  @Override
  protected void onMouseReleased(MouseEvent event) {
    if (currentScreen != null) {
      currentScreen.onMouseReleased(event);
    }
  }

  @Override
  protected void onMouseDragged(MouseEvent event) {
    if (currentScreen != null) {
      currentScreen.onMouseDragged(event);
    }
  }

  @Override
  protected void onMouseMoved(MouseEvent event) {
    if (currentScreen != null) {
      currentScreen.onMouseMoved(event);
    }
  }

  @Override
  protected void onMouseWheelMoved(ScrollEvent event) {
    if (currentScreen != null) {
      currentScreen.onMouseWheelMoved(event);
    }
  }

    @Override
  protected void onFocusChanged(boolean focused) {
    if (currentScreen != null) {
      currentScreen.onFocusChanged(focused);
    }
  }

  @Override
  protected void onResize(Vec2d newSize) {
    windowSize = new Vec2d(newSize);
    if (currentScreen != null) {
      currentScreen.onResize(windowSize);
    }
  }

  @Override
  protected void onShutdown() {
    if (currentScreen != null) {
      currentScreen.onExit();
    }
  }

  @Override
  protected void onStartup() {
    if (currentScreen != null) {
      currentScreen.onResize(windowSize);
    }
  }
}
