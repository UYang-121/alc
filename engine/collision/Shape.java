package engine.collision;

import engine.support.Vec2d;
import engine.world.TransformComponent;

public interface Shape {
  void sync(TransformComponent transform);

  boolean containsPoint(Vec2d point);

  boolean collides(Shape other);

  boolean collidesCircle(CircleShape circle);

  boolean collidesAAB(AABShape box);
}
