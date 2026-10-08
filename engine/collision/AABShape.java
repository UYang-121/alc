package engine.collision;

import engine.support.Vec2d;
import engine.world.TransformComponent;

public class AABShape implements Shape {
  private final Vec2d localTopLeft;
  private final Vec2d size;
  private Vec2d topLeft;

  public AABShape(Vec2d localTopLeft, Vec2d size) {
    this.localTopLeft = new Vec2d(localTopLeft);
    this.size = new Vec2d(size);
    topLeft = new Vec2d(localTopLeft);
  }

  @Override
  public void sync(TransformComponent transform) {
    topLeft = transform.getPosition().plus(localTopLeft);
  }

  @Override
  public boolean containsPoint(Vec2d point) {
    return point.x >= topLeft.x && point.x <= topLeft.x + size.x
        && point.y >= topLeft.y && point.y <= topLeft.y + size.y;
  }

  @Override
  public boolean collides(Shape other) {
    return other.collidesAAB(this);
  }

  @Override
  public boolean collidesCircle(CircleShape circle) {
    Vec2d center = circle.getCenter();
    double closestX = Math.max(topLeft.x, Math.min(topLeft.x + size.x, center.x));
    double closestY = Math.max(topLeft.y, Math.min(topLeft.y + size.y, center.y));
    return circle.containsPoint(new Vec2d(closestX, closestY));
  }

  @Override
  public boolean collidesAAB(AABShape box) {
    return topLeft.x <= box.topLeft.x + box.size.x
        && topLeft.x + size.x >= box.topLeft.x
        && topLeft.y <= box.topLeft.y + box.size.y
        && topLeft.y + size.y >= box.topLeft.y;
  }

  public Vec2d getTopLeft() {
    return new Vec2d(topLeft);
  }

  public Vec2d getSize() {
    return new Vec2d(size);
  }
}
