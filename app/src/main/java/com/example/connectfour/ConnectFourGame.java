package com.example.connectfour;

public class ConnectFourGame {
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;
    int[][] boardGrid;
    int player = BLUE;

    public ConnectFourGame() {
        boardGrid = new int[ROW][COL];
    }

    public void newGame() {
        // start with blue disc
        player = BLUE;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = 0;
            }
        }
    }

    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }
    public boolean isGameOver() {
        // All of the discs are full
        if (isBoardFull()) {
            return true;
        }

        // Someone wins
        return isWin();
    }

    public boolean isBoardFull() {
        // Iterate through the board to check if all positions are filled
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (boardGrid[row][col] == EMPTY) {
                    // one empty position is empty = not full
                    return false;
                }
            }
        }

        // board is full
        return true;
    }

    public boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    public boolean checkHorizontal() {
        // Check boardGrid for four consecutive discs of one color horizontally
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row][col + 1] &&
                        boardGrid[row][col] == boardGrid[row][col + 2] &&
                        boardGrid[row][col] == boardGrid[row][col + 3]) {
                    return true; // Four consecutive discs found horizontally
                }
            }
        }
        return false;
    }

    public boolean checkVertical() {
        // Check boardGrid for four consecutive discs of one color vertically
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col < COL; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row + 1][col] &&
                        boardGrid[row][col] == boardGrid[row + 2][col] &&
                        boardGrid[row][col] == boardGrid[row + 3][col]) {
                    return true; // Four consecutive discs found vertically
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal() {
        // Check boardGrid for four consecutive discs of one color horizontally
        // 1st diagonal direction (top-left)
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row + 1][col + 1] &&
                        boardGrid[row][col] == boardGrid[row + 2][col + 2] &&
                        boardGrid[row][col] == boardGrid[row + 3][col + 3]) {
                    return true;
                }
            }
        }

        // 2nd diagonal direction (top-right)
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 3; col < COL; col++) {
                if (boardGrid[row][col] != EMPTY &&
                        boardGrid[row][col] == boardGrid[row + 1][col - 1] &&
                        boardGrid[row][col] == boardGrid[row + 2][col - 2] &&
                        boardGrid[row][col] == boardGrid[row + 3][col - 3]) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setState(String gameState) {
        // Populate boardGrid with the saved game state data
        int i = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = Integer.parseInt(gameState.substring(i, i + 1));
                i++;
            }
        }
    }

    public void selectDisc(int row, int col) {
        for (int r = ROW - 1; r >= 0; r--) {
            if (boardGrid[r][col] == EMPTY) {
                // Set the element in the array to the current player
                boardGrid[r][col] = player;

                // Switch players by updating member variable player to the opponent (i.e., RED or BLUE)
                player = (player == BLUE) ? RED : BLUE;

                // Break out of the loop after a disc has been placed on the board
                break;
            }
        }
    }
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardString.append(boardGrid[row][col]);
            }
        }

        return boardString.toString();
    }
}
