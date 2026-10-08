package engine.collision;

import engine.world.GameObject;
import engine.world.GameWorld;

public interface CollisionBehavior {
  void onCollision(GameObject self, GameObject other, GameWorld world);
}
