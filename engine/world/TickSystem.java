package engine.world;

import java.util.ArrayDeque;
import java.util.Deque;

public class TickSystem extends System {
  private final Deque<GameObject> pendingRemoves = new ArrayDeque<>();
  private boolean ticking;


  @Override
  public void removeGameObject(GameObject object) {
    if (ticking) {
      pendingRemoves.add(object);
    } else {
      super.removeGameObject(object);
    }
  }

  public void onTick(long nanosSincePreviousTick) {
    ticking = true;
    for (GameObject object : gameObjects) {
      object.getComponent(TickableComponent.class)
          .ifPresent(component -> component.tick(nanosSincePreviousTick, object));
    }
    ticking = false;
    while (!pendingRemoves.isEmpty()) {
      removeGameObject(pendingRemoves.remove());
    }
  }
}