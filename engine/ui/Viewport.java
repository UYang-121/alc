package engine.ui;

import engine.support.Vec2d;
import engine.world.DragComponent;
import engine.world.DrawableComponent;
import engine.world.GameObject;
import engine.world.GameWorld;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Viewport extends UIElement {
  private static final double MIN_SCALE = 0.45;
  private static final double MAX_SCALE = 3.5;

  private final GameWorld world;
  private double cameraX;
  private double cameraY;
  private double scale = 1;
  private double lastMouseX;
  private double lastMouseY;
  private GameObject draggedObject;
  private boolean panning;

  public Viewport(Vec2d position, Vec2d size, GameWorld world) {
    super(position, size);
    this.world = world;
  }

  /** Draws the world through a clipped game-to-screen transform. */
  @Override
  public void draw(GraphicsContext g) {
    g.save();
    g.beginPath();
    g.rect(position.x, position.y, size.x, size.y);
    g.closePath();
    g.clip();
    g.setTransform(gameToScreen());
    world.onDraw(g);
    g.restore();

    g.save();
    g.setStroke(Color.web("#D8C4E8"));
    g.setLineWidth(3);
    g.strokeRoundRect(position.x, position.y, size.x, size.y, 10, 10);
    g.restore();
  }

  public void drawDraggedObject(GraphicsContext g) {
    if (draggedObject == null || contains(lastMouseX, lastMouseY)) {
      return;
    }
    draggedObject.getComponent(DrawableComponent.class).ifPresent(drawable -> {
      g.save();
      g.setTransform(gameToScreen());
      drawable.draw(g, draggedObject);
      g.restore();
    });
  }

  public boolean onMousePressed(MouseEvent event) {
    if (event.getButton() != MouseButton.PRIMARY || !contains(event.getX(), event.getY())) {
      return false;
    }
    Point2D worldPoint = screenToWorld(event.getX(), event.getY());
    draggedObject = null;
    for (GameObject object : world.getObjectsAt(worldPoint.getX(), worldPoint.getY())) {
      if (object.getComponent(DragComponent.class).isPresent()) {
        draggedObject = object;
        break;
      }
    }
    for (GameObject object : world.getGameObjects()) {
      object.setSelected(object == draggedObject);
    }
    if (draggedObject != null) {
      DragComponent drag = draggedObject.getComponent(DragComponent.class).get();
      drag.beginDrag(draggedObject, worldPoint.getX(), worldPoint.getY());
    } else {
      panning = true;
    }
    lastMouseX = event.getX();
    lastMouseY = event.getY();
    return true;
  }

  public void onMouseDragged(MouseEvent event) {
    if (draggedObject != null) {
      Point2D worldPoint = screenToWorld(event.getX(), event.getY());
      draggedObject.getComponent(DragComponent.class).get()
          .drag(draggedObject, worldPoint.getX(), worldPoint.getY());
    } else if (panning) {
      panBy((lastMouseX - event.getX()) / scale, (lastMouseY - event.getY()) / scale);
    }
    lastMouseX = event.getX();
    lastMouseY = event.getY();
  }

  public void onMouseReleased(MouseEvent event) {
    if (draggedObject != null) {
      draggedObject.getComponent(DragComponent.class).get().endDrag(draggedObject);
    }
    draggedObject = null;
    panning = false;
  }

  public void onMouseWheelMoved(ScrollEvent event) {
    if (contains(event.getX(), event.getY())) {
      zoomAt(event.getX(), event.getY(), event.getDeltaY() > 0 ? 1.12 : 1 / 1.12);
    }
  }

  /** Keeps the game-space point under the cursor fixed while changing scale. */
  public void zoomAt(double screenX, double screenY, double factor) {
    Point2D anchor = screenToWorld(screenX, screenY);
    scale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale * factor));
    cameraX = anchor.getX() - (screenX - position.x) / scale;
    cameraY = anchor.getY() - (screenY - position.y) / scale;
  }

  public void panBy(double worldDeltaX, double worldDeltaY) {
    cameraX = Math.max(-900, Math.min(900, cameraX + worldDeltaX));
    cameraY = Math.max(-650, Math.min(650, cameraY + worldDeltaY));
  }

  public Point2D screenToWorld(double screenX, double screenY) {
    return new Point2D(
        cameraX + (screenX - position.x) / scale,
        cameraY + (screenY - position.y) / scale);
  }

  public Point2D worldToScreen(double worldX, double worldY) {
    return new Point2D(
        position.x + (worldX - cameraX) * scale,
        position.y + (worldY - cameraY) * scale);
  }

  public double getScale() {
    return scale;
  }

  public GameObject getDraggedObject() {
    return draggedObject;
  }

  private Affine gameToScreen() {
    return new Affine(scale, 0, position.x - cameraX * scale,
        0, scale, position.y - cameraY * scale);
  }
}