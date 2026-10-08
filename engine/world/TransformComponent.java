package engine.world;

import engine.support.Vec2d;

public class TransformComponent implements Component {
  private Vec2d position;
  private final Vec2d size;

  public TransformComponent(Vec2d position, Vec2d size) {
    this.position = new Vec2d(position);
    this.size = new Vec2d(size);
  }

 public boolean contains(double x, double y) {
    return x >= position.x && x <= position.x + size.x
        && y >= position.y && y <= position.y + size.y;
  }

  public Vec2d getPosition() {
    return new Vec2d(position);
  }

  public Vec2d getSize() {
    return new Vec2d(size);
  }

  public void setPosition(Vec2d position) {
    this.position = new Vec2d(position);
  }

}