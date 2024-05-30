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

    private int getFreeRowIndex(int colIndex) {
        int rowIndex = 0;
        while (rowIndex < rows) {
            if (tokens[colIndex][rowIndex] == null) return rowIndex;
            rowIndex++;
        }
        return -1;
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
