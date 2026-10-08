package tic.model;

/** The game rules and board state, kept separate from drawing and input. */
public class TicTacToe {

  public enum Mark {
    EMPTY, X, O
  }

  private static final int BOARD_SIZE = 3;

  private final Mark[][] board;
  private Mark currentPlayer;
  private Mark winner;
  private int moves;
  private boolean gameOver;

  public TicTacToe() {
    board = new Mark[BOARD_SIZE][BOARD_SIZE];
    reset();
  }

  public void reset() {
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        board[row][col] = Mark.EMPTY;
      }
    }
    currentPlayer = Mark.X;
    winner = Mark.EMPTY;
    moves = 0;
    gameOver = false;
  }
  
  public boolean makeMove(int row, int col) {
    if (!inBounds(row, col) || gameOver || board[row][col] != Mark.EMPTY) {
      return false;
    }

    board[row][col] = currentPlayer;
    moves++;

    if (hasWon(currentPlayer)) {
      winner = currentPlayer;
      gameOver = true;
    } else if (moves == BOARD_SIZE * BOARD_SIZE) {
      gameOver = true;
    } else {
      switchPlayer();
    }
    return true;
  }
   
    public void skipTurn() {
    if (!gameOver) {
      switchPlayer();
    }
  }

  public Mark getCell(int row, int col) {
    if (!inBounds(row, col)) {
      throw new IndexOutOfBoundsException("Board coordinates must be between 0 and 2");
    }
    return board[row][col];
  }

  public Mark getCurrentPlayer() {
    return currentPlayer;
  }

  public Mark getWinner() {
    return winner;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean isDraw() {
    return gameOver && winner == Mark.EMPTY;
  }

  private boolean inBounds(int row, int col) {
    return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
  }

  private void switchPlayer() {
    currentPlayer = currentPlayer == Mark.X ? Mark.O : Mark.X;
  }

  private boolean hasWon(Mark mark) {
    for (int i = 0; i < BOARD_SIZE; i++) {
      if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) {
        return true;
      }
      if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark) {
        return true;
      }
    }

    return board[0][0] == mark && board[1][1] == mark && board[2][2] == mark
        || board[0][2] == mark && board[1][1] == mark && board[2][0] == mark;
  }

}
