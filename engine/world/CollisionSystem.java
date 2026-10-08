package engine.world;

import engine.collision.CollisionComponent;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionSystem extends System {
  private final GameWorld world;
  private final Deque<GameObject> pendingAdds = new ArrayDeque<>();
  private final Deque<GameObject> pendingRemoves = new ArrayDeque<>();
  private final Set<GameObject> removedDuringCollision = new HashSet<>();
  private boolean checking;

  public CollisionSystem(GameWorld world) {
    this.world = world;
  }

  @Override
  public void addGameObject(GameObject object) {
    if (contains(object) || pendingAdds.contains(object)) {
      return;
    }
    if (checking) {
      pendingAdds.add(object);
    } else {
      super.addGameObject(object);
    }
  }

  @Override
  public void removeGameObject(GameObject object) {
    if (checking) {
      if (removedDuringCollision.add(object)) {
        pendingRemoves.add(object);
      }
    } else {
      super.removeGameObject(object);
    }
  }

  public void onTick() {
    checking = true;
    List<GameObject> collidable = new ArrayList<>(gameObjects);
    for (GameObject object : collidable) {
      object.getComponent(CollisionComponent.class)
          .ifPresent(component -> component.sync(object.getTransform()));
    }

    for (int firstIndex = 0; firstIndex < collidable.size(); firstIndex++) {
      GameObject first = collidable.get(firstIndex);
      if (removedDuringCollision.contains(first)) {
        continue;
      }
      for (int secondIndex = firstIndex + 1; secondIndex < collidable.size(); secondIndex++) {
        GameObject second = collidable.get(secondIndex);
        if (removedDuringCollision.contains(second)) {
          continue;
        }
        CollisionComponent firstCollision = first.getComponent(CollisionComponent.class).get();
        CollisionComponent secondCollision = second.getComponent(CollisionComponent.class).get();
        if (firstCollision.collides(secondCollision)) {
          firstCollision.notifyCollision(first, second, world);
          secondCollision.notifyCollision(second, first, world);
        }
      }
    }
    checking = false;
    flushQueues();
  }

  private void flushQueues() {
    while (!pendingRemoves.isEmpty()) {
      super.removeGameObject(pendingRemoves.remove());
    }
    removedDuringCollision.clear();
    while (!pendingAdds.isEmpty()) {
      super.addGameObject(pendingAdds.remove());
    }
  }
}
