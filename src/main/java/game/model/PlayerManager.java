package game.model;

import game.dto.GameDto;
import game.dto.PlayerDto;
import game.exception.ConnectFourException;

import java.util.List;
import java.util.function.Predicate;

public class PlayerManager {
    private final List<Player> players;
    private int indexCurrPlayer;

    public PlayerManager(List<Player> players, int indexCurrPlayer) {
        this.players = players;
        this.indexCurrPlayer = indexCurrPlayer;
    }

    public void notifyGameStart(Token[][] tokens, long roundTime) {
        notifyPlayersInitialGameState(tokens, roundTime);
        notifyCurrPlayerTurn();
    }

    public void nextPlayer() {
        indexCurrPlayer = computeNextPlayerIndex();
        notifyCurrPlayerTurn();
    }

    public Token getCurrPlayerToken() {
        return players.get(indexCurrPlayer).getToken();
    }

    public void reduceCurrPlayerTime(long millis) throws ConnectFourException {
        Player currPlayer = players.get(indexCurrPlayer);
        currPlayer.reduceTime(millis);
        PlayerDto currPlayerDto = currPlayer.getPlayerDto();
        Player nextPlayer = players.get(computeNextPlayerIndex());
        nextPlayer.notifyOpponentTimeUpdated(currPlayerDto);
    }

    public boolean hasCurrentPlayerTimeLeft() {
        return players.get(indexCurrPlayer).isTimeLeft();
    }

    public void giveUp(Token playerId) {
        players.stream().filter(p -> p.getToken() != playerId).forEach(Player::notifyPlayerWon);
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

    private void notifyPlayersInitialGameState(Token[][] tokens, long roundTime) {
        List<PlayerDto> playerDtos = players.stream().map(Player::getPlayerDto).toList();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            PlayerDto playerDto = playerDtos.get(i);
            PlayerDto opponentPlayerDto = playerDtos.get(i == 0 ? 1 : 0);
            GameDto gameDto = new GameDto(playerDto, opponentPlayerDto, tokens, roundTime);
            player.notifyInitialGameState(gameDto);
        }
    }

    private void notifyCurrPlayerTurn() {
        Player currPlayer = players.get(indexCurrPlayer);
        Player nextPlayer = players.get(computeNextPlayerIndex());
        Token opponentToken = currPlayer.getToken();
        nextPlayer.notifyOpponentTurn(opponentToken);
        currPlayer.play();
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
