package game.model;

import game.oo.ConnectFourEvent;
import game.oo.Observer;

import java.util.Set;

public class AIPlayer extends Player implements Observer {
    private final AIStrategy aiStrategy;
    private Token[][] copyTokens;
    private LocalGame localGame;

    public AIPlayer(String name, Token token, Time time, Set<Observer> observers, AIStrategy aiStrategy, Token[][] copyTokens) {
        super(name, token, time, observers);
        this.aiStrategy = aiStrategy;
        this.copyTokens = copyTokens;
    }

    @Override
    public void play() {
        super.play();
        int columnIndex = aiStrategy.getBestMove(copyTokens, getToken());
        localGame.play(columnIndex);
    }

    @Override
    public void update(ConnectFourEvent e, Object data) {
        if (e == ConnectFourEvent.BOARD_UPDATED) {
            this.copyTokens = (Token[][]) data;
        }
    }

    public void setLocalGame(LocalGame localGame) {
        this.localGame = localGame;
    }
}
