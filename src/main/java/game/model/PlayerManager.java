package game.model;

import game.dto.GameDto;
import game.dto.PlayerDto;
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
        Player currPlayer = players.get(indexCurrPlayer);
        Player nextPlayer = players.get(computeNextPlayerIndex());
        Token opponentToken = currPlayer.getToken();
        currPlayer.play();
        nextPlayer.notifyOpponentTurn(opponentToken);
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

    public void notifyPlayersInitialGameState(Token[][] tokens) {
        List<PlayerDto> playerDtos = players.stream().map(Player::getPlayerDto).toList();
        Token currPlayerToken = getCurrPlayerToken();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            GameDto gameDto = new GameDto(playerDtos, tokens, currPlayerToken, i == indexCurrPlayer);
            player.notifyInitialGameState(gameDto);
        }
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
