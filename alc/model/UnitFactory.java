package alc.model;

import alc.components.CombinationBehavior;
import alc.components.ElementComponent;
import alc.components.UnitDragComponent;
import engine.collision.CircleShape;
import engine.collision.CollisionComponent;
import engine.graphics.SpriteComponent;
import engine.graphics.SpriteSheetResource;
import engine.support.Vec2d;
import engine.world.GameObject;
import engine.world.TransformComponent;

public final class UnitFactory {
  private static final double UNIT_SIZE = 72;
  private static final SpriteSheetResource ELEMENT_SPRITES = SpriteSheetResource.get(
      "alc/resources/elements.png", 768, 96);

  private UnitFactory() { }

  public static GameObject create(ElementType type, double centerX, double centerY) {
    GameObject unit = new GameObject(
        new TransformComponent(
            new Vec2d(centerX - UNIT_SIZE / 2, centerY - UNIT_SIZE / 2),
            new Vec2d(UNIT_SIZE, UNIT_SIZE)),
        10);
    unit.addComponent(new ElementComponent(type));
    unit.addComponent(new SpriteComponent(ELEMENT_SPRITES, type.getSprite()));
    unit.addComponent(new UnitDragComponent());
    unit.addComponent(new CollisionComponent(
        new CircleShape(new Vec2d(UNIT_SIZE / 2, UNIT_SIZE / 2), UNIT_SIZE * 0.42),
        new CombinationBehavior()));
    return unit;
  }

  public static void loadSprites() {
    ELEMENT_SPRITES.load();
  }

  public static SpriteSheetResource getSpriteSheet() {
    return ELEMENT_SPRITES;
  }
}
