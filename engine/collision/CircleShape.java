package engine.collision;

import engine.support.Vec2d;
import engine.world.TransformComponent;

public class CircleShape implements Shape {
  private final Vec2d localCenter;
  private final double radius;
  private Vec2d center;

  public CircleShape(Vec2d localCenter, double radius) {
    this.localCenter = new Vec2d(localCenter);
    this.radius = radius;
    center = new Vec2d(localCenter);
  }

  @Override
  public void sync(TransformComponent transform) {
    center = transform.getPosition().plus(localCenter);
  }

  @Override
  public boolean containsPoint(Vec2d point) {
    return center.dist2(point) <= radius * radius;
  }

  @Override
  public boolean collides(Shape other) {
    return other.collidesCircle(this);
  }

  @Override
  public boolean collidesCircle(CircleShape circle) {
    double radii = radius + circle.radius;
    return center.dist2(circle.center) <= radii * radii;
  }

  @Override
  public boolean collidesAAB(AABShape box) {
    return box.collidesCircle(this);
  }

  public Vec2d getCenter() {
    return new Vec2d(center);
  }

  public double getRadius() {
    return radius;
  }
}
