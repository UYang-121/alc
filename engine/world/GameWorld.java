package engine.world;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;

public class GameWorld {
  private final Set<GameObject> gameObjects = new LinkedHashSet<>();
  private final GraphicsSystem graphicsSystem = new GraphicsSystem();

  public void addGameObject(GameObject object) {
    if (gameObjects.add(object)) {
      register(object);
    }
  }

  public void onDraw(GraphicsContext g) {
    graphicsSystem.onDraw(g);
  }

  public List<GameObject> getObjectsAt(double worldX, double worldY) {
    List<GameObject> result = new java.util.ArrayList<>();
    List<GameObject> drawOrder = graphicsSystem.getDrawOrder();
    for (int index = drawOrder.size() - 1; index >= 0; index--) {
      GameObject object = drawOrder.get(index);
      if (object.getTransform().contains(worldX, worldY)) {
        result.add(object);
      }
    }
    return result;
  }

  public Set<GameObject> getGameObjects() {
    return Collections.unmodifiableSet(gameObjects);
  }

  private void register(GameObject object) {
    if (object.getComponent(DrawableComponent.class).isPresent()) {
      graphicsSystem.addGameObject(object);
    }
  }
}