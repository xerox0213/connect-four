package game.model;

import game.oo.ConnectFourEvent;
import game.oo.Observer;

import java.util.Set;

public class AIPlayer extends Player {
    private final AIStrategy aiStrategy;
    private final LocalGame localGame;
    private Token[][] copyTokens;

    public AIPlayer(String name, Token token, Time time, Set<Observer> observers, AIStrategy aiStrategy, LocalGame localGame, Token[][] copyTokens) {
        super(name, token, time, observers);
        this.aiStrategy = aiStrategy;
        this.localGame = localGame;
        this.copyTokens = copyTokens;
    }

    @Override
    public void play() {
        super.play();
        int columnIndex = aiStrategy.getBestMove(copyTokens, getToken());
        localGame.play(columnIndex);
    }
}
