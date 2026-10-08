package engine.ui;

import engine.support.Vec2d;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


/** A reusable button with hover and pressed states. */
public class Button extends UIElement {

  private final Runnable action;
  private String label;
  private boolean hovered;
  private boolean pressed;

  public Button(Vec2d position, Vec2d size, String label, Runnable action) {
    super(position, size);
    this.label = label;
    this.action = action;
  }
  
  public void setLabel(String label) {
    this.label = label;
  }
  
  public void onMouseMoved(MouseEvent event) {
    hovered = contains(event.getX(), event.getY());
  }

  public void onMouseDragged(MouseEvent event) {
    hovered = contains(event.getX(), event.getY());
  }

  public void onMousePressed(MouseEvent event) {
    pressed = contains(event.getX(), event.getY());
  }

  public void onMouseReleased(MouseEvent event) {
    boolean shouldRun = pressed && contains(event.getX(), event.getY());
    pressed = false;
    if (shouldRun && action != null) {
      action.run();
    }
  }

   @Override
  public void draw(GraphicsContext g) {
    Color fill = Color.web("#F6F1E8");
    if (hovered) {
      fill = Color.web("#FFD166");
    }
    if (pressed) {
      fill = Color.web("#E9B949");
    }

    g.save();
    g.setFill(fill);
    g.fillRoundRect(position.x, position.y, size.x, size.y, 14, 14);
    g.setStroke(Color.web("#172A3A"));
    g.setLineWidth(3);
    g.strokeRoundRect(position.x, position.y, size.x, size.y, 14, 14);
    g.setFill(Color.web("#172A3A"));
    g.setFont(Font.font("Avenir Next", FontWeight.BOLD, Math.max(18, size.y * 0.34)));
    g.setTextAlign(TextAlignment.CENTER);
    g.setTextBaseline(VPos.CENTER);
    g.fillText(label, position.x + size.x / 2, position.y + size.y / 2, size.x - 16);
    g.restore();
  }
 

}
