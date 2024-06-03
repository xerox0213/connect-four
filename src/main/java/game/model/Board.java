package game.model;

import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
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

    public Board(Token[][] tokens) {
        cols = tokens.length;
        rows = tokens[0].length;
        this.tokens = tokens;
        observers = new HashSet<>();
    }

    public void addToken(int colIndex, Token token) throws ConnectFourException {
        if (isOutsideBoard(colIndex)) throw new ConnectFourException(ConnectFourError.OUTSIDE_BOARD);

        int rowIndex = getFreeRowIndex(colIndex);
        if (rowIndex == -1) throw new ConnectFourException(ConnectFourError.COLUMN_FILLED);

        tokens[colIndex][rowIndex] = token;
        Token[][] copyTokens = getCopyTokens();
        notifyObservers(ConnectFourEvent.BOARD_UPDATED, copyTokens);
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

    private Token[][] getCopyTokens() {
        Token[][] copyTokens = new Token[cols][rows];
        for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
            for (int rowIndex = 0; rowIndex < tokens[0].length; rowIndex++) {
                copyTokens[colIndex][rowIndex] = tokens[colIndex][rowIndex];
            }
        }
        return copyTokens;
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
