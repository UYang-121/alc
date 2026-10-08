package engine.world;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class System {
  protected final Set<GameObject> gameObjects = new LinkedHashSet<>();

  public void addGameObject(GameObject object) {
    gameObjects.add(object);
  }

  public void removeGameObject(GameObject object) {
    gameObjects.remove(object);
  }

  public boolean contains(GameObject object) {
    return gameObjects.contains(object);
  }

  public Set<GameObject> getGameObjects() {
    return Collections.unmodifiableSet(gameObjects);
  }
}
