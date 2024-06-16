package game.model;

import game.exception.ConnectFourException;

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

    public void reduceCurrPlayerTime(long millis) throws ConnectFourException {
        players.get(indexCurrPlayer).reduceTime(millis);
    }

    public void declareCurrPlayerWinner() {
        declareWinner(indexCurrPlayer, computeNextPlayerIndex());
    }

    public void declareNextPlayerWinner() {
        declareWinner(computeNextPlayerIndex(), indexCurrPlayer);
    }

    public void declareGameDraw() {
        players.forEach(Player::notifyDraw);
    }

    private void declareWinner(int indexWinner, int indexLoser) {
        Player winner = players.get(indexWinner);
        winner.notifyPlayerWon();
        Player loser = players.get(indexLoser);
        loser.notifyPlayerLost();
    }

    private int computeNextPlayerIndex() {
        return indexCurrPlayer + 1 == players.size() ? 0 : indexCurrPlayer + 1;
    }

}
