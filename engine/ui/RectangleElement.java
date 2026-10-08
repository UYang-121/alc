package engine.ui;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/** A filled rectangle with an optional outline. */
public class RectangleElement extends UIElement {
  private Color fill;
  private Color stroke;
  private double strokeWidth;

  public RectangleElement(Vec2d position, Vec2d size, Color fill) {
    super(position, size);
    this.fill = fill;
    stroke = Color.TRANSPARENT;
    strokeWidth = 0;
  }

public void setFill(Color fill) {
    this.fill = fill;
  }

  public void setStroke(Color stroke, double strokeWidth) {
    this.stroke = stroke;
    this.strokeWidth = strokeWidth;
  }

  @Override
  public void draw(GraphicsContext g) {
    g.save();
    g.setFill(fill);
    g.fillRect(position.x, position.y, size.x, size.y);
    if (strokeWidth > 0) {
      g.setStroke(stroke);
      g.setLineWidth(strokeWidth);
      g.strokeRect(position.x, position.y, size.x, size.y);
    }
    g.restore();
  }
}