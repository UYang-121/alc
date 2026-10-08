package engine.graphics;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public final class SpriteSheetResource {
  private static final Map<String, SpriteSheetResource> RESOURCES = new HashMap<>();

  private final String relativePath;
  private final double sheetWidth;
  private final double sheetHeight;
  private Image image;

  private SpriteSheetResource(String relativePath, double sheetWidth, double sheetHeight) {
    this.relativePath = relativePath;
    this.sheetWidth = sheetWidth;
    this.sheetHeight = sheetHeight;
  }

  public static synchronized SpriteSheetResource get(
      String relativePath, double sheetWidth, double sheetHeight) {
    return RESOURCES.computeIfAbsent(relativePath,
        path -> new SpriteSheetResource(path, sheetWidth, sheetHeight));
  }

  public void load() {
    if (image != null) {
      return;
    }
    Image loadedImage = new Image(Path.of(relativePath).toUri().toString());
    if (loadedImage.isError()
        || loadedImage.getWidth() != sheetWidth
        || loadedImage.getHeight() != sheetHeight) {
      throw new IllegalArgumentException("Could not load sprite sheet: " + relativePath);
    }
    image = loadedImage;
  }

  public Image getImage() {
    load();
    return image;
  }

  public boolean isLoaded() {
    return image != null;
  }

  public double getSheetWidth() {
    return sheetWidth;
  }

  public double getSheetHeight() {
    return sheetHeight;
  }
}