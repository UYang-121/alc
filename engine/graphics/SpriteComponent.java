package engine.graphics;

import engine.support.Vec2d;
import engine.world.DrawableComponent;
import engine.world.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpriteComponent implements DrawableComponent {
  private final SpriteSheetResource spriteSheet;
  private final SpriteRegion sprite;

  public SpriteComponent(SpriteSheetResource spriteSheet, SpriteRegion sprite) {
    this.spriteSheet = spriteSheet;
    this.sprite = sprite;
  }

  @Override
  public void draw(GraphicsContext g, GameObject object) {
    Vec2d position = object.getTransform().getPosition();
    Vec2d size = object.getTransform().getSize();
    g.save();
    if (object.isSelected()) {
      g.setStroke(Color.web("#F6E6FF"));
      g.setLineWidth(5);
      g.strokeOval(position.x - 6, position.y - 6, size.x + 12, size.y + 12);
    }
    g.drawImage(spriteSheet.getImage(),
        sprite.getSourceX(), sprite.getSourceY(), sprite.getWidth(), sprite.getHeight(),
        position.x, position.y, size.x, size.y);
    g.restore();
  }

  public SpriteSheetResource getSpriteSheet() {
    return spriteSheet;
  }
}