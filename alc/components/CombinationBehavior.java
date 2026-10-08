package alc.components;

import alc.model.ElementType;
import alc.model.UnitFactory;
import engine.collision.CollisionBehavior;
import engine.support.Vec2d;
import engine.world.GameObject;
import engine.world.GameWorld;

public class CombinationBehavior implements CollisionBehavior {
  @Override
  public void onCollision(GameObject self, GameObject other, GameWorld world) {
    if (self.getId() > other.getId()) {
      return;
    }
    UnitDragComponent firstDrag = self.getComponent(UnitDragComponent.class).orElse(null);
    UnitDragComponent secondDrag = other.getComponent(UnitDragComponent.class).orElse(null);
    if ((firstDrag != null && firstDrag.isDragging())
        || (secondDrag != null && secondDrag.isDragging())) {
      return;
    }
    ElementComponent first = self.getComponent(ElementComponent.class).orElse(null);
    ElementComponent second = other.getComponent(ElementComponent.class).orElse(null);
    if (first == null || second == null) {
      return;
    }
    ElementType result = ElementType.combine(first.getType(), second.getType());
    if (result == null) {
      return;
    }

    Vec2d firstCenter = centerOf(self);
    Vec2d secondCenter = centerOf(other);
    double resultX = (firstCenter.x + secondCenter.x) / 2;
    double resultY = (firstCenter.y + secondCenter.y) / 2;
    world.removeGameObject(self);
    world.removeGameObject(other);
    world.addGameObject(UnitFactory.create(result, resultX, resultY));
  }

  private Vec2d centerOf(GameObject object) {
    Vec2d position = object.getTransform().getPosition();
    Vec2d size = object.getTransform().getSize();
    return new Vec2d(position.x + size.x / 2, position.y + size.y / 2);
  }
}
