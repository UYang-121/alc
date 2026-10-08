package alc.model;

import java.util.List;

public enum ElementType {
  FIRE("Fire", true, false, SpriteType.FIRE),
  WATER("Water", true, false, SpriteType.WATER),
  EARTH("Earth", true, false, SpriteType.EARTH),
  AIR("Air", true, false, SpriteType.AIR),
  STEAM("Steam", false, false, SpriteType.STEAM),
  MUD("Mud", false, false, SpriteType.MUD),
  LAVA("Lava", false, false, SpriteType.LAVA),
  LIFE("Life", false, true, SpriteType.LIFE);

  private static final List<ElementType> BASE_ELEMENTS =
      List.of(FIRE, WATER, EARTH, AIR);

  private final String label;
  private final boolean base;
  private final boolean terminal;
  private final SpriteType sprite;

  ElementType(String label, boolean base, boolean terminal, SpriteType sprite) {
    this.label = label;
    this.base = base;
    this.terminal = terminal;
    this.sprite = sprite;
  }

  public String getLabel() {
    return label;
  }

  public boolean isBase() {
    return base;
  }

  public boolean isTerminal() {
    return terminal;
  }

  public SpriteType getSprite() {
    return sprite;
  }

  public static List<ElementType> getBaseElements() {
    return BASE_ELEMENTS;
  }

  public static ElementType combine(ElementType first, ElementType second) {
    if (matches(first, second, FIRE, WATER)) {
      return STEAM;
    }
    if (matches(first, second, EARTH, WATER)) {
      return MUD;
    }
    if (matches(first, second, FIRE, EARTH)) {
      return LAVA;
    }
    if (matches(first, second, STEAM, MUD)) {
      return LIFE;
    }
    return null;
  }

  private static boolean matches(ElementType first, ElementType second,
      ElementType left, ElementType right) {
    return first == left && second == right || first == right && second == left;
  }
}
