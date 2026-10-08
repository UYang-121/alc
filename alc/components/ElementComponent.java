package alc.components;

import alc.model.ElementType;
import engine.world.Component;


public class ElementComponent implements Component {
  private final ElementType type;

  public ElementComponent(ElementType type) {
    this.type = type;
  }

  public ElementType getType() {
    return type;
  }
}