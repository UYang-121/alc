package tic.screens;

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
import tic.App;

/** Opening screen for the game. */
public class TitleScreen extends Screen {

  private final App app;
  private final RectangleElement background;
  private final TextElement title;
  private final TextElement subtitle;
  private final Button startButton;

  public TitleScreen(App app) {
    super(app);
    this.app = app;
    background = new RectangleElement(Vec2d.ORIGIN, size, Color.web("#102536"));
    title = new TextElement(Vec2d.ORIGIN, Vec2d.ORIGIN, "TIC-TAC-TOE", 58,
        Color.web("#F6F1E8"));
    title.setWeight(FontWeight.EXTRA_BOLD);
    subtitle = new TextElement(Vec2d.ORIGIN, Vec2d.ORIGIN,
        "Two players. Ten seconds each turn.", 21, Color.web("#A9C1D1"));
    startButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "START GAME", app::showGameScreen);
    layout();

     @Override
  public void onDraw(GraphicsContext g) {
    background.draw(g);
    title.draw(g);
    subtitle.draw(g);
    startButton.draw(g);
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
    startButton.onMouseMoved(event);
  }

  @Override
  public void onMouseDragged(MouseEvent event) {
    startButton.onMouseDragged(event);
  }

  @Override
  public void onMousePressed(MouseEvent event) {
    startButton.onMousePressed(event);
  }

  @Override
  public void onMouseReleased(MouseEvent event) {
    startButton.onMouseReleased(event);
  }

  @Override
  public void onResize(Vec2d newSize) {
    super.onResize(newSize);
    layout();
  }

  private void layout() {
    background.setBounds(Vec2d.ORIGIN, size);
    double centerY = size.y * 0.42;
    title.setBounds(new Vec2d(size.x * 0.15, centerY - 90), new Vec2d(size.x * 0.7, 80));
    title.setFontSize(Math.max(42, Math.min(68, size.x * 0.065)));
    subtitle.setBounds(new Vec2d(size.x * 0.15, centerY - 8), new Vec2d(size.x * 0.7, 42));
    startButton.setBounds(new Vec2d(size.x / 2 - 130, centerY + 80), new Vec2d(260, 72));
  }
}