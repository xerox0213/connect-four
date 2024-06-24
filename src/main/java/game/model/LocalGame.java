package game.model;

import game.exception.ConnectFourException;

public class LocalGame implements Game {
    private final Board board;
    private final PlayerManager playerManager;
    private final RoundTimer roundTimer;

    public LocalGame(Board board, PlayerManager playerManager, RoundTimer roundTimer) {
        this.board = board;
        this.playerManager = playerManager;
        this.roundTimer = roundTimer;
    }

    @Override
    public void play(int columnIndex) {
        try {
            roundTimer.stop();
            Token currPlayerToken = playerManager.getCurrPlayerToken();
            boolean isWinningMove = board.addToken(columnIndex, currPlayerToken);
            boolean isBoardFull = board.isBoardFull();
            if (isWinningMove) {
                playerManager.declareCurrPlayerWinner();
            } else if (isBoardFull) {
                playerManager.declareGameDraw();
            } else {
                roundTimer.start();
                playerManager.nextPlayer();
            }
        } catch (ConnectFourException e) {
            roundTimer.start();
        }
    }

    @Override
    public void start() {
        Token[][] tokens = board.getCopyTokens();
        long roundTime = roundTimer.getMillis();
        playerManager.notifyPlayersInitialGameState(tokens, roundTime);
        roundTimer.start();
    }
}
