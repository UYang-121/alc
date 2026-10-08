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
import tic.model.TicTacToe;
import tic.ui.TicBoard;



/** The playable Tic-Tac-Toe screen. */
public class GameScreen extends Screen {

  private static final double TURN_TIME = 10.0;

  private final App app;
  private final TicTacToe game;
  private final TicBoard board;
  private final RectangleElement background;
  private final RectangleElement sidePanel;
  private final RectangleElement timerTrack;
  private final RectangleElement timerFill;
  private final RectangleElement overlay;
  private final TextElement heading;
  private final TextElement status;
  private final TextElement timerText;
  private final TextElement resultText;
  private final TextElement hint;
  private final Button newGameButton;
  private final Button menuButton;
  private final Button playAgainButton;

  private double timeLeft = TURN_TIME;

  public GameScreen(App app) {
    super(app);
    this.app = app;
    game = new TicTacToe();
    board = new TicBoard(game);
     background = new RectangleElement(Vec2d.ORIGIN, size, Color.web("#173247"));
    sidePanel = new RectangleElement(Vec2d.ORIGIN, Vec2d.ORIGIN, Color.web("#102536"));
    timerTrack = new RectangleElement(Vec2d.ORIGIN, Vec2d.ORIGIN, Color.web("#29475D"));
    timerFill = new RectangleElement(Vec2d.ORIGIN, Vec2d.ORIGIN, Color.web("#FFD166"));
    overlay = new RectangleElement(Vec2d.ORIGIN, Vec2d.ORIGIN, Color.web("#102536", 0.96));

    heading = makeText("TIC-TAC-TOE", 28, Color.web("#F6F1E8"), FontWeight.BOLD);
    status = makeText("X'S TURN", 26, Color.web("#F6F1E8"), FontWeight.BOLD);
    timerText = makeText("10.0 s", 22, Color.web("#FFD166"), FontWeight.BOLD);
    resultText = makeText("", 34, Color.web("#F6F1E8"), FontWeight.EXTRA_BOLD);

    hint = makeText("R: new game   ESC: menu", 16, Color.web("#A9C1D1"), FontWeight.NORMAL);
    newGameButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "NEW GAME", this::resetGame);
    menuButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "MENU", app::showTitleScreen);
    playAgainButton = new Button(Vec2d.ORIGIN, Vec2d.ORIGIN, "PLAY AGAIN", this::resetGame);
    layout();
  }
   @Override
  public void onTick(long nanosSincePreviousTick) {
    if (game.isGameOver()) {
      return;
    }

    double elapsed = nanosSincePreviousTick / 1_000_000_000.0;
    if (elapsed > 0 && elapsed < 1) {
      timeLeft -= elapsed;
    }
    if (timeLeft <= 0) {
      game.skipTurn();
      timeLeft = TURN_TIME;
    }
  }

  @Override
  public void onDraw(GraphicsContext g) {
    updateLabels();
    background.draw(g);
    sidePanel.draw(g);
    board.draw(g);
    heading.draw(g);
    status.draw(g);
    timerTrack.draw(g);
    timerFill.draw(g);
    timerText.draw(g);
    newGameButton.draw(g);
    menuButton.draw(g);
    hint.draw(g);

    if (game.isGameOver()) {
      overlay.draw(g);
      resultText.draw(g);
      playAgainButton.draw(g);
    }
  }

  @Override
  public void onKeyPressed(KeyEvent event) {
    if (event.getCode() == KeyCode.ESCAPE) {
      app.showTitleScreen();
    } else if (event.getCode() == KeyCode.R) {
      resetGame();
    }
  }

  @Override
  public void onMouseClicked(MouseEvent event) {
    if (!game.isGameOver() && board.playAt(event.getX(), event.getY())) {
      timeLeft = TURN_TIME;
      board.clearHover();
    }
  }

  @Override
  public void onMouseMoved(MouseEvent event) {
    board.onMouseMoved(event);
    newGameButton.onMouseMoved(event);
    menuButton.onMouseMoved(event);
    playAgainButton.onMouseMoved(event);
  }

  @Override
  public void onMouseDragged(MouseEvent event) {
    newGameButton.onMouseDragged(event);
    menuButton.onMouseDragged(event);
    playAgainButton.onMouseDragged(event);
  }

  @Override
  public void onMousePressed(MouseEvent event) {
    newGameButton.onMousePressed(event);
    menuButton.onMousePressed(event);
    if (game.isGameOver()) {
      playAgainButton.onMousePressed(event);
    }
  }

  @Override
  public void onMouseReleased(MouseEvent event) {
    newGameButton.onMouseReleased(event);
    menuButton.onMouseReleased(event);
    if (game.isGameOver()) {
      playAgainButton.onMouseReleased(event);
    }
  }

  @Override
  public void onResize(Vec2d newSize) {
    super.onResize(newSize);
    layout();
  }

  @Override
  public void onFocusChanged(boolean focused) {
    if (!focused) {
      board.clearHover();
    }
  }
  
  private TextElement makeText(String text, double fontSize, Color color, FontWeight weight) {
    TextElement element = new TextElement(Vec2d.ORIGIN, Vec2d.ORIGIN, text, fontSize, color);
    element.setWeight(weight);
    return element;
  }

  private void resetGame() {
    game.reset();
    timeLeft = TURN_TIME;
    board.clearHover();
  }

  private void updateLabels() {
    status.setText(game.getCurrentPlayer() + "'S TURN");
    timerText.setText(String.format("%.1f s", Math.max(0, timeLeft)));

    double trackWidth = size.x * 0.22;
    double fillWidth = trackWidth * Math.max(0, timeLeft) / TURN_TIME;
    timerFill.setBounds(new Vec2d(size.x * 0.745, size.y * 0.40), new Vec2d(fillWidth, 18));

    if (game.isDraw()) {
      resultText.setText("IT'S A DRAW");
    } else if (game.isGameOver()) {
      resultText.setText(game.getWinner() + " WINS!");
    }
  }
private void layout() {
    background.setBounds(Vec2d.ORIGIN, size);

    double panelWidth = size.x * 0.30;
    double gameAreaWidth = size.x - panelWidth;
    sidePanel.setBounds(new Vec2d(gameAreaWidth, 0), new Vec2d(panelWidth, size.y));

    double boardSize = Math.min(gameAreaWidth - 70, size.y - 70);
    boardSize = Math.max(0, boardSize);
    double boardX = (gameAreaWidth - boardSize) / 2;
    double boardY = (size.y - boardSize) / 2;
    board.setBounds(new Vec2d(boardX, boardY), new Vec2d(boardSize, boardSize));

    double panelLeft = gameAreaWidth + panelWidth * 0.12;
    double panelContentWidth = panelWidth * 0.76;
    heading.setBounds(new Vec2d(panelLeft, size.y * 0.09),
        new Vec2d(panelContentWidth, 50));
    status.setBounds(new Vec2d(panelLeft, size.y * 0.25),
        new Vec2d(panelContentWidth, 46));
    timerText.setBounds(new Vec2d(panelLeft, size.y * 0.32),
        new Vec2d(panelContentWidth, 42));
    timerTrack.setBounds(new Vec2d(size.x * 0.745, size.y * 0.40),
        new Vec2d(size.x * 0.22, 18));

    newGameButton.setBounds(new Vec2d(panelLeft, size.y * 0.52),
        new Vec2d(panelContentWidth, 62));
    menuButton.setBounds(new Vec2d(panelLeft, size.y * 0.68),
        new Vec2d(panelContentWidth, 62));
    hint.setBounds(new Vec2d(panelLeft, size.y * 0.86),
        new Vec2d(panelContentWidth, 38));

    double overlayWidth = Math.min(430, gameAreaWidth * 0.68);
    double overlayHeight = Math.min(250, size.y * 0.48);
    double overlayX = (gameAreaWidth - overlayWidth) / 2;
    double overlayY = (size.y - overlayHeight) / 2;
    overlay.setBounds(new Vec2d(overlayX, overlayY), new Vec2d(overlayWidth, overlayHeight));
    resultText.setBounds(new Vec2d(overlayX + 30, overlayY + 42),
        new Vec2d(overlayWidth - 60, 72));
    playAgainButton.setBounds(new Vec2d(overlayX + overlayWidth / 2 - 110, overlayY + 145),
        new Vec2d(220, 64));

    updateLabels();
  }
}

