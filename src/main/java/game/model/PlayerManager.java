package game.model;

import java.util.List;

public class PlayerManager {
    private final List<Player> players;
    private int indexCurrPlayer;

    public PlayerManager(List<Player> players, int indexCurrPlayer) {
        this.players = players;
        this.indexCurrPlayer = indexCurrPlayer;
    }

    public void nextPlayer() {
        indexCurrPlayer = computeNextPlayerIndex();
    }

    public Token getCurrPlayerToken() {
        return players.get(indexCurrPlayer).getToken();
    }

    private int computeNextPlayerIndex() {
        return indexCurrPlayer + 1 == players.size() ? 0 : indexCurrPlayer + 1;
    }

}
