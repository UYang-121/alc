package alc.model;

import engine.graphics.SpriteRegion;

public enum SpriteType implements SpriteRegion {
  FIRE(0, 0),
  WATER(0, 1),
  EARTH(0, 2),
  AIR(0, 3),
  STEAM(0, 4),
  MUD(0, 5),
  LAVA(0, 6),
  LIFE(0, 7);

  private static final double SPRITE_WIDTH = 96;
  private static final double SPRITE_HEIGHT = 96;
  private static final double HORIZONTAL_PADDING = 0;
  private static final double VERTICAL_PADDING = 0;
  private static final double HORIZONTAL_SPACING = 0;
  private static final double VERTICAL_SPACING = 0;

  private final int row;
  private final int column;

  SpriteType(int row, int column) {
    this.row = row;
    this.column = column;
  }

  @Override
  public double getSourceX() {
    return HORIZONTAL_PADDING + column * (SPRITE_WIDTH + HORIZONTAL_SPACING);
  }

  @Override
  public double getSourceY() {
    return VERTICAL_PADDING + row * (SPRITE_HEIGHT + VERTICAL_SPACING);
  }

  @Override
  public double getWidth() {
    return SPRITE_WIDTH;
  }

  @Override
  public double getHeight() {
    return SPRITE_HEIGHT;
  }
}
