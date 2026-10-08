package alc.ui;

import alc.model.ElementType;
import alc.model.UnitFactory;
import engine.graphics.SpriteComponent;
import engine.support.Vec2d;
import engine.ui.UIElement;
import engine.ui.Viewport;
import engine.world.GameObject;
import engine.world.GameWorld;
import engine.world.TransformComponent;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Palette extends UIElement {
  private static final double TRASH_HEIGHT = 72;

  private final GameWorld world;
  private final Viewport viewport;
  private final List<ElementType> baseElements;
  private final Map<ElementType, GameObject> previews;
  private ElementType draggingType;
  private double mouseX;
  private double mouseY;

  public Palette(Vec2d position, Vec2d size, GameWorld world, Viewport viewport) {
    super(position, size);
    this.world = world;
    this.viewport = viewport;
    baseElements = ElementType.getBaseElements();
    previews = new EnumMap<>(ElementType.class);
    for (ElementType type : baseElements) {
      GameObject preview = new GameObject(
          new TransformComponent(Vec2d.ORIGIN, new Vec2d(56, 56)), 0);
      preview.addComponent(new SpriteComponent(UnitFactory.getSpriteSheet(), type.getSprite()));
      previews.put(type, preview);
    }
  }

  @Override
  public void draw(GraphicsContext g) {
    g.save();
    g.setFill(Color.web("#1C1B2B"));
    g.fillRoundRect(position.x, position.y, size.x, size.y, 16, 16);
    g.setStroke(Color.web("#807398"));
    g.setLineWidth(2);
    g.strokeRoundRect(position.x, position.y, size.x, size.y, 16, 16);
    g.setFill(Color.web("#EEE5F6"));
    g.setFont(Font.font("Avenir Next", FontWeight.BOLD, 16));
    g.setTextAlign(TextAlignment.CENTER);
    g.setTextBaseline(VPos.CENTER);
    g.fillText("ELEMENTS", position.x + size.x / 2, position.y + 28);

    for (int index = 0; index < baseElements.size(); index++) {
      drawEntry(g, baseElements.get(index), index);
    }
    drawTrash(g);
    g.restore();

    if (draggingType != null) {
      drawPreview(g, draggingType, mouseX - 28, mouseY - 28, 0.75);
    }
  }

  public boolean onMousePressed(MouseEvent event) {
    if (event.getButton() != MouseButton.PRIMARY || !contains(event.getX(), event.getY())) {
      return false;
    }
    int index = (int) ((event.getY() - position.y - 50) / rowHeight());
    if (index >= 0 && index < baseElements.size() && event.getY() < trashY()) {
      draggingType = baseElements.get(index);
      mouseX = event.getX();
      mouseY = event.getY();
      return true;
    }
    return false;
  }

  public void onMouseDragged(MouseEvent event) {
    if (draggingType != null) {
      mouseX = event.getX();
      mouseY = event.getY();
    }
  }

  public void onMouseReleased(MouseEvent event) {
    if (draggingType != null && viewport.contains(event.getX(), event.getY())) {
      Point2D worldPoint = viewport.screenToWorld(event.getX(), event.getY());
      world.addGameObject(UnitFactory.create(
          draggingType, worldPoint.getX(), worldPoint.getY()));
    }
    draggingType = null;
  }

  public boolean isTrashDrop(double screenX, double screenY) {
    return screenX >= position.x + 12 && screenX <= position.x + size.x - 12
        && screenY >= trashY() && screenY <= position.y + size.y - 10;
  }

  private void drawEntry(GraphicsContext g, ElementType type, int index) {
    double entryHeight = rowHeight();
    double entryY = position.y + 50 + index * entryHeight;
    g.setFill(Color.web("#29273A"));
    g.fillRoundRect(position.x + 12, entryY, size.x - 24, entryHeight - 10, 12, 12);
    drawPreview(g, type, position.x + 20, entryY + Math.max(3, (entryHeight - 66) / 2), 1);
    g.setFill(Color.web("#DDD3E8"));
    g.setFont(Font.font("Avenir Next", 14));
    g.setTextAlign(TextAlignment.LEFT);
    g.setTextBaseline(VPos.CENTER);
    g.fillText(type.getLabel(), position.x + 84, entryY + (entryHeight - 10) / 2);
  }

  private void drawPreview(GraphicsContext g, ElementType type, double x, double y, double opacity) {
    GameObject preview = previews.get(type);
    preview.getTransform().setPosition(new Vec2d(x, y));
    g.save();
    g.setGlobalAlpha(opacity);
    preview.getComponent(SpriteComponent.class).get().draw(g, preview);
    g.restore();
  }

  private double rowHeight() {
    return Math.max(58, (size.y - 64 - TRASH_HEIGHT) / baseElements.size());
  }

  private void drawTrash(GraphicsContext g) {
    double trashY = trashY();
    g.setFill(Color.web("#4A2330"));
    g.fillRoundRect(position.x + 12, trashY, size.x - 24, TRASH_HEIGHT, 12, 12);
    g.setStroke(Color.web("#D87A91"));
    g.setLineWidth(2);
    g.strokeRoundRect(position.x + 12, trashY, size.x - 24, TRASH_HEIGHT, 12, 12);
    g.setFill(Color.web("#F4D8DF"));
    g.setFont(Font.font("Avenir Next", FontWeight.BOLD, 13));
    g.setTextAlign(TextAlignment.CENTER);
    g.fillText("DROP UNIT TO REMOVE", position.x + size.x / 2, trashY + TRASH_HEIGHT / 2);
  }

  private double trashY() {
    return position.y + size.y - TRASH_HEIGHT - 10;
  }
}