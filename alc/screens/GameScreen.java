package alc.screens;

import alc.App;
import alc.components.WorldDrawingComponent;
import alc.model.ElementType;
import alc.model.UnitFactory;
import alc.ui.Palette;
import engine.Screen;
import engine.support.Vec2d;
import engine.ui.Button;
import engine.ui.TextElement;
import engine.ui.Viewport;
import engine.world.GameObject;
import engine.world.GameWorld;
import engine.world.TransformComponent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class GameScreen extends Screen {
  private final App app;
  private final GameWorld world;
  private final Viewport viewport;
  private final Palette palette;
  private final Button backButton;
  private final TextElement instructions;

  public GameScreen(App app) {
    super(app);
    this.app = app;
    world = new GameWorld();
    viewport = new Viewport(Vec2d.ORIGIN, new Vec2d(100, 100), world);
    palette = new Palette(Vec2d.ORIGIN, new Vec2d(170, 460), world, viewport);
    backButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "BACK", app::showTitleScreen);
    instructions = new TextElement(Vec2d.ORIGIN, Vec2d.ORIGIN,
        "OVERLAP TO COMBINE  |  SCROLL TO ZOOM  |  DROP ON TRASH TO REMOVE",
        14, Color.web("#D8CFE3"));
    instructions.setWeight(FontWeight.BOLD);

    GameObject background = new GameObject(
        new TransformComponent(new Vec2d(-1400, -1000), new Vec2d(2800, 2000)), -100);
    background.addComponent(new WorldDrawingComponent());
    world.addGameObject(background);
    world.addGameObject(UnitFactory.create(ElementType.FIRE, 190, 180));
    world.addGameObject(UnitFactory.create(ElementType.WATER, 390, 320));
    world.addGameObject(UnitFactory.create(ElementType.EARTH, 610, 180));
    world.addGameObject(UnitFactory.create(ElementType.AIR, 790, 330));
    layout();
  }

  @Override
  public void onTick(long nanosSincePreviousTick) {
    UnitFactory.loadSprites();
    world.onTick(nanosSincePreviousTick);
  }

  @Override
  public void onDraw(GraphicsContext g) {
    g.setFill(Color.web("#11101B"));
    g.fillRect(0, 0, size.x, size.y);
    viewport.draw(g);
    palette.draw(g);
    viewport.drawDraggedObject(g);
    backButton.draw(g);
    instructions.draw(g);
  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    double distance = 28 / viewport.getScale();
    if (event.getCode() == KeyCode.LEFT) {
      viewport.panBy(-distance, 0);
    } else if (event.getCode() == KeyCode.RIGHT) {
      viewport.panBy(distance, 0);
    } else if (event.getCode() == KeyCode.UP) {
      viewport.panBy(0, -distance);
    } else if (event.getCode() == KeyCode.DOWN) {
      viewport.panBy(0, distance);
    } else if (event.getCode() == KeyCode.ESCAPE) {
      app.showTitleScreen();
    }
  }

  @Override
  public void onMouseMoved(MouseEvent event) {
    backButton.onMouseMoved(event);
  }

  @Override
  public void onMousePressed(MouseEvent event) {
    backButton.onMousePressed(event);
    if (!palette.onMousePressed(event)) {
      viewport.onMousePressed(event);
    }
  }

  @Override
  public void onMouseDragged(MouseEvent event) {
    backButton.onMouseDragged(event);
    palette.onMouseDragged(event);
    viewport.onMouseDragged(event);
  }

  @Override
  public void onMouseReleased(MouseEvent event) {
    GameObject droppedObject = viewport.getDraggedObject();
    if (droppedObject != null && palette.isTrashDrop(event.getX(), event.getY())) {
      world.removeGameObject(droppedObject);
    }
    palette.onMouseReleased(event);
    viewport.onMouseReleased(event);
    backButton.onMouseReleased(event);
  }

  @Override
  public void onMouseWheelMoved(ScrollEvent event) {
    viewport.onMouseWheelMoved(event);
  }

  @Override
  public void onResize(Vec2d newSize) {
    super.onResize(newSize);
    layout();
  }

  private void layout() {
    double margin = Math.max(18, size.x * 0.025);
    double top = 76;
    double paletteWidth = Math.max(150, Math.min(190, size.x * 0.2));
    double contentHeight = Math.max(300, size.y - top - margin);
    double viewportWidth = Math.max(360, size.x - paletteWidth - margin * 3);
    viewport.setBounds(new Vec2d(margin, top), new Vec2d(viewportWidth, contentHeight));
    palette.setBounds(new Vec2d(size.x - paletteWidth - margin, top),
        new Vec2d(paletteWidth, contentHeight));
    backButton.setBounds(new Vec2d(margin, 16), new Vec2d(110, 44));
    instructions.setBounds(new Vec2d(140, 16),
        new Vec2d(Math.max(260, size.x - 160), 44));
    instructions.setFontSize(Math.max(11, Math.min(15, size.x * 0.014)));
  }
}
