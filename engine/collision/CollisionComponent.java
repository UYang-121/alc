package engine.collision;

import engine.support.Vec2d;
import engine.world.Component;
import engine.world.GameObject;
import engine.world.GameWorld;
import engine.world.TransformComponent;

public class CollisionComponent implements Component {
  private final Shape shape;
  private final CollisionBehavior behavior;
  private final int layer;
  private final int collisionMask;

  public CollisionComponent(Shape shape, CollisionBehavior behavior) {
    this(shape, behavior, 1, 1);
  }

  public CollisionComponent(Shape shape, CollisionBehavior behavior,
      int layer, int collisionMask) {
    this.shape = shape;
    this.behavior = behavior;
    this.layer = layer;
    this.collisionMask = collisionMask;
  }

  public void sync(TransformComponent transform) {
    shape.sync(transform);
  }

  public boolean collides(CollisionComponent other) {
    return (collisionMask & other.layer) != 0
        && (other.collisionMask & layer) != 0
        && shape.collides(other.shape);
  }

  public boolean containsPoint(double x, double y) {
    return shape.containsPoint(new Vec2d(x, y));
  }

  public void notifyCollision(GameObject self, GameObject other, GameWorld world) {
    if (behavior != null) {
      behavior.onCollision(self, other, world);
    }
  }

  public Shape getShape() {
    return shape;
  }
}