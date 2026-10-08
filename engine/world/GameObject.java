package engine.world;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class GameObject {
  private static final AtomicLong NEXT_ID = new AtomicLong();

  private final long id;
  private final Map<Class<?>, Component> components;
  private final TransformComponent transform;
  private int zIndex;
  private boolean selected;

  public GameObject(TransformComponent transform, int zIndex) {
    id = NEXT_ID.incrementAndGet();
    components = new LinkedHashMap<>();
    this.transform = transform;
    this.zIndex = zIndex;
    addComponent(transform);
  }

  public void addComponent(Component component) {
    components.put(component.getClass(), component);
  }

  public <T extends Component> Optional<T> getComponent(Class<T> type) {
    for (Component component : components.values()) {
      if (type.isInstance(component)) {
        return Optional.of(type.cast(component));
      }
    }
    return Optional.empty();
  }

  public Collection<Component> getComponents() {
    return components.values();
  }

  public long getId() {
    return id;
  }

  public TransformComponent getTransform() {
    return transform;
  }

  public int getZIndex() {
    return zIndex;
  }

  void setZIndex(int zIndex) {
    this.zIndex = zIndex;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}