package engine.world;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.NavigableSet;
import java.util.TreeSet;
import javafx.scene.canvas.GraphicsContext;

public class GraphicsSystem extends System {
  private final NavigableSet<GameObject> drawOrder = new TreeSet<>(
      Comparator.comparingInt(GameObject::getZIndex).thenComparingLong(GameObject::getId));
  private final Deque<GameObject> pendingAdds = new ArrayDeque<>();
  private final Deque<GameObject> pendingRemoves = new ArrayDeque<>();
  private boolean drawing;

  @Override
  public void addGameObject(GameObject object) {
    if (contains(object) || pendingAdds.contains(object)) {
      return;
    }
    if (drawing) {
      pendingAdds.add(object);
    } else {
      super.addGameObject(object);
      drawOrder.add(object);
    }
  }

  @Override
  public void removeGameObject(GameObject object) {
    if (drawing) {
      pendingRemoves.add(object);
    } else {
      super.removeGameObject(object);
      drawOrder.remove(object);
    }
  }

  public void changeZIndex(GameObject object, int zIndex) {
    drawOrder.remove(object);
    object.setZIndex(zIndex);
    if (contains(object)) {
      drawOrder.add(object);
    }
  }

  public void onDraw(GraphicsContext g) {
    drawing = true;
    for (GameObject object : drawOrder) {
      object.getComponent(DrawableComponent.class)
          .ifPresent(component -> component.draw(g, object));
    }
    drawing = false;
    flushQueues();
  }

  public NavigableSet<GameObject> getDrawOrder() {
    return Collections.unmodifiableNavigableSet(drawOrder);
  }

  private void flushQueues() {
    while (!pendingRemoves.isEmpty()) {
      removeGameObject(pendingRemoves.remove());
    }
    while (!pendingAdds.isEmpty()) {
      addGameObject(pendingAdds.remove());
    }
  }

}