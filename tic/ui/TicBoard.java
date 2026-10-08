package tic.ui;

import engine.support.Vec2d;
import engine.ui.UIElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import tic.model.TicTacToe;
import tic.model.TicTacToe.Mark;

/** Draws the board and translates mouse positions into board positions. */
public class TicBoard extends UIElement {

  private static final Color BOARD_COLOR = Color.web("#F6F1E8");
  private static final Color X_COLOR = Color.web("#EF476F");
  private static final Color O_COLOR = Color.web("#4CC9F0");
 
 public TicBoard(TicTacToe game) {
    super(Vec2d.ORIGIN, Vec2d.ORIGIN);
    this.game = game;
  }

  public boolean playAt(double mouseX, double mouseY) {
    int[] cell = cellAt(mouseX, mouseY);
    return cell != null && game.makeMove(cell[0], cell[1]);
  }

  public void onMouseMoved(MouseEvent event) {
    int[] cell = cellAt(event.getX(), event.getY());
    if (cell == null || game.isGameOver() || game.getCell(cell[0], cell[1]) != Mark.EMPTY) {
      hoverRow = -1;
      hoverCol = -1;
    } else {
      hoverRow = cell[0];
      hoverCol = cell[1];
    }
  }

  public void clearHover() {
    hoverRow = -1;
    hoverCol = -1;
  }
  
  @Override
  public void draw(GraphicsContext g) {
    double cellSize = size.x / 3.0;
    double lineWidth = Math.max(5, size.x * 0.015);
    double markPadding = cellSize * 0.22;

    g.save();
    g.setFill(Color.web(""));
    g.fillRoundRect(position.x, position.y, size.x, size.y, 24, 24);

    if (hoverRow >= 0) {
      g.setFill(Color.web("#FFFFFF", 0.09));
      g.fillRect(position.x + hoverCol * cellSize, position.y + hoverRow * cellSize,
          cellSize, cellSize);
    }

    g.setStroke(BOARD_COLOR);
    g.setLineWidth(lineWidth);
    g.setLineCap(StrokeLineCap.ROUND);
    for (int i = 1; i < 3; i++) {
      g.strokeLine(position.x + i * cellSize, position.y + 18,
          position.x + i * cellSize, position.y + size.y - 18);
      g.strokeLine(position.x + 18, position.y + i * cellSize,
          position.x + size.x - 18, position.y + i * cellSize);
    }

  for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        double left = position.x + col * cellSize + markPadding;
        double top = position.y + row * cellSize + markPadding;
        double markSize = cellSize - 2 * markPadding;
        Mark mark = game.getCell(row, col);
        if (mark == Mark.X) {
          g.setStroke(X_COLOR);
          g.strokeLine(left, top, left + markSize, top + markSize);
          g.strokeLine(left + markSize, top, left, top + markSize);
        } else if (mark == Mark.O) {
          g.setStroke(O_COLOR);
          g.strokeOval(left, top, markSize, markSize);
        }
      }
    }
    g.restore();
  }

  private int[] cellAt(double mouseX, double mouseY) {
    if (!contains(mouseX, mouseY) || size.x == 0) {
      return null;
    }
    double cellSize = size.x / 3.0;
    int row = (int) ((mouseY - position.y) / cellSize);
    int col = (int) ((mouseX - position.x) / cellSize);
    return new int[] {row, col};
  }

}