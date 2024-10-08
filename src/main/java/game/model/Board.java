package game.model;

import game.dto.MoveDto;
import game.exception.ConnectFourError;
import game.exception.ConnectFourException;
import game.oo.ConnectFourEvent;
import game.oo.Observable;
import game.oo.Observer;

import java.util.Set;

/**
 * Represents the game board for Connect Four.
 */
public class Board implements Observable, BoardView {
    private final int cols;
    private final int rows;
    private final Token[][] tokens;
    private final Set<Observer> observers;

    public Board(Token[][] tokens, Set<Observer> observers) {
        cols = tokens.length;
        rows = tokens[0].length;
        this.tokens = tokens;
        this.observers = observers;
    }

    public boolean addToken(int colIndex, Token token) throws ConnectFourException {
        if (BoardAlgorithm.isOutsideBoard(getCopyTokens(), colIndex)) throw new ConnectFourException(ConnectFourError.OUTSIDE_BOARD);

        int rowIndex = BoardAlgorithm.getFreeRowIndex(getCopyTokens(), colIndex);
        if (rowIndex == -1) throw new ConnectFourException(ConnectFourError.COLUMN_FILLED);

        tokens[colIndex][rowIndex] = token;

        notifyObservers(ConnectFourEvent.BOARD_UPDATED, new MoveDto(token, colIndex, rowIndex));
        return BoardAlgorithm.isWinningMove(getCopyTokens(), colIndex, rowIndex, token);
    }

    public boolean isBoardFull() {
        return BoardAlgorithm.isBoardFull(getCopyTokens());
    }

    @Override
    public Token[][] getCopyTokens() {
        Token[][] copyTokens = new Token[cols][rows];
        for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
            System.arraycopy(tokens[colIndex], 0, copyTokens[colIndex], 0, tokens[0].length);
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
