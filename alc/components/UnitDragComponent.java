package alc.components;

import engine.support.Vec2d;
import engine.world.DragComponent;
import engine.world.GameObject;

public class UnitDragComponent implements DragComponent {
  private double offsetX;
  private double offsetY;
  private boolean dragging;

  @Override
  public void beginDrag(GameObject object, double worldX, double worldY) {
    Vec2d position = object.getTransform().getPosition();
    offsetX = worldX - position.x;
    offsetY = worldY - position.y;
    dragging = true;
  }

  @Override
  public void drag(GameObject object, double worldX, double worldY) {
    object.getTransform().setPosition(new Vec2d(worldX - offsetX, worldY - offsetY));
  }

  @Override
  public void endDrag(GameObject object) {
    dragging = false;
  }

  public boolean isDragging() {
    return dragging;
  }
}