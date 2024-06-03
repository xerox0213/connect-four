package game.model;

import game.oo.ConnectFourEvent;
import game.oo.Observable;
import game.oo.Observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the game board for Connect Four.
 */
public class Board implements Observable {
    private final int cols;
    private final int rows;
    private final Token[][] tokens;
    private final Set<Observer> observers;

    public Board(BoardSize boardSize) {
        cols = boardSize.getCols();
        rows = boardSize.getRows();
        tokens = new Token[cols][rows];
        observers = new HashSet<>();
    }

    private boolean isOutsideBoard(int colIndex) {
        return colIndex < 0 || colIndex >= cols;
    }

    private boolean isOutsideBoard(int colIndex, int rowIndex) {
        return isOutsideBoard(colIndex) || (rowIndex >= rows);
    }

    private int getFreeRowIndex(int colIndex) {
        int rowIndex = 0;
        while (rowIndex < rows) {
            if (tokens[colIndex][rowIndex] == null) return rowIndex;
            rowIndex++;
        }
        return -1;
    }

    private boolean isWinningMove(int tokenColIndex, int tokenRowIndex, Token playedToken) {
        Directions[] directions = Directions.values();
        for (Directions direction : directions) {
            int numberTokensAligned = 1;
            for (int i = 0; i < 2; i++) {
                int dx = i == 0 ? direction.getDx() : direction.getOppositeDx();
                int dy = i == 0 ? direction.getDy() : direction.getOppositeDy();
                int colIndex = tokenColIndex + dx;
                int rowIndex = tokenRowIndex + dy;
                while (!isOutsideBoard(colIndex, rowIndex)) {
                    Token token = tokens[colIndex][rowIndex];
                    if (token == playedToken) {
                        numberTokensAligned++;
                        if (numberTokensAligned >= 4) return true;
                    }
                    colIndex += dx;
                    rowIndex += dy;
                }
            }
        }
        return false;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(ConnectFourEvent e, Object data) {
        observers.forEach((observer) -> observer.update(e, data));
    }
}
