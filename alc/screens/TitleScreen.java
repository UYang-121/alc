package alc.screens;

import alc.App;
import engine.Screen;
import engine.support.Vec2d;
import engine.ui.Button;
import engine.ui.RectangleElement;
import engine.ui.TextElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class TitleScreen extends Screen {
  private final App app;
  private final RectangleElement background;
  private final TextElement title;
  private final TextElement subtitle;
  private final Button playButton;
  private final Button quitButton;

  public TitleScreen(App app) {
    super(app);
    this.app = app;
    background = new RectangleElement(Vec2d.ORIGIN, size, Color.web("#1B1929"));
    title = new TextElement(Vec2d.ORIGIN, Vec2d.ORIGIN,
        "ALCHEMY", 76, Color.web("#EAD7FF"));
    title.setWeight(FontWeight.EXTRA_BOLD);
    subtitle = new TextElement(Vec2d.ORIGIN, Vec2d.ORIGIN,
        "shape the elements", 18, Color.web("#AAA0BD"));
    playButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "PLAY", app::showGameScreen);
    quitButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "QUIT", app::shutdown);
    layout();
  }

  @Override
  public void onDraw(GraphicsContext g) {
    background.draw(g);
    drawDecorations(g);
    title.draw(g);
    subtitle.draw(g);
    playButton.draw(g);
    quitButton.draw(g);
  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
      app.showGameScreen();
    } else if (event.getCode() == KeyCode.ESCAPE) {
      app.shutdown();
    }
  }

  @Override
  public void onMouseMoved(MouseEvent event) {
    playButton.onMouseMoved(event);
    quitButton.onMouseMoved(event);
  }

  @Override
  public void onMouseDragged(MouseEvent event) {
    playButton.onMouseDragged(event);
    quitButton.onMouseDragged(event);
  }

  @Override
  public void onMousePressed(MouseEvent event) {
    playButton.onMousePressed(event);
    quitButton.onMousePressed(event);
  }

  @Override
  public void onMouseReleased(MouseEvent event) {
    playButton.onMouseReleased(event);
    quitButton.onMouseReleased(event);
  }

  @Override
  public void onResize(Vec2d newSize) {
    super.onResize(newSize);
    layout();
  }

  private void layout() {
    background.setBounds(Vec2d.ORIGIN, size);
    double buttonWidth = Math.max(170, Math.min(240, size.x * 0.22));
    title.setBounds(new Vec2d(size.x * 0.12, size.y * 0.25),
        new Vec2d(size.x * 0.76, 100));
    title.setFontSize(Math.max(58, Math.min(98, size.x * 0.09)));
    subtitle.setBounds(new Vec2d(size.x * 0.2, size.y * 0.43),
        new Vec2d(size.x * 0.6, 36));
    playButton.setBounds(new Vec2d((size.x - buttonWidth) / 2, size.y * 0.58),
        new Vec2d(buttonWidth, 54));
    quitButton.setBounds(new Vec2d((size.x - buttonWidth) / 2, size.y * 0.58 + 72),
        new Vec2d(buttonWidth, 54));
  }

  private void drawDecorations(GraphicsContext g) {
    g.save();
    g.setGlobalAlpha(0.22);
    g.setStroke(Color.web("#BFA4DE"));
    g.setLineWidth(2);
    for (int radius = 80; radius < Math.min(size.x, size.y); radius += 70) {
      g.strokeOval(size.x / 2 - radius, size.y / 2 - radius, radius * 2, radius * 2);
    }
    g.restore();
  }
}
