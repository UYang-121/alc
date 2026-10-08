package engine.ui;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

/** Base class for a drawable rectangular UI element. */
public abstract class UIElement {

  protected Vec2d position;
  protected Vec2d size;

  protected UIElement(Vec2d position, Vec2d size) {
    setBounds(position, size);
  }

  public final void setBounds(Vec2d position, Vec2d size) {
    if (position == null || size == null || size.x < 0 || size.y < 0) {
      throw new IllegalArgumentException("UI bounds must have a position and nonnegative size");
    }
    this.position = position;
    this.size = size;
  }

  public final boolean contains(double x, double y) {
    return x >= position.x && x < position.x + size.x
        && y >= position.y && y < position.y + size.y;
  }

  public abstract void draw(GraphicsContext g);

}
