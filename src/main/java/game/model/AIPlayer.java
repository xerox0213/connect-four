package game.model;

import game.oo.Observer;

import java.util.Set;

public class AIPlayer extends Player {
    private final AIStrategy aiStrategy;
    private LocalGame localGame;
    private final BoardView boardView;

    public AIPlayer(String name, Token token, Time time, Set<Observer> observers, AIStrategy aiStrategy, BoardView boardView) {
        super(name, token, time, observers);
        this.aiStrategy = aiStrategy;
        this.boardView = boardView;
    }

    @Override
    public void play() {
        super.play();
        Runnable runnable = () -> {
            try {
                Thread.sleep(1200);
            } catch (Exception ignore) {
            }
            int columnIndex = aiStrategy.getBestMove(boardView.getCopyTokens(), getToken());
            localGame.play(columnIndex);
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    public void setLocalGame(LocalGame localGame) {
        this.localGame = localGame;
    }
}
