package game.model;

import game.dto.GameConfigDto;
import game.oo.Observer;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameFactory {

    public Game createGame(GameConfigDto gameConfigDto, String myPlayerName, String opponentName, Observer meObserver, Observer opponentObserver) {
        Board board = createBoard(gameConfigDto.boardSize(), meObserver, opponentObserver);
        List<Player> players = createPlayers(gameConfigDto.playerTime(), myPlayerName, opponentName, meObserver, opponentObserver);
        PlayerManager playerManager = createPlayerManager(players, gameConfigDto.firstPlayer());
        RoundTimer roundTimer = createRoundTimer(gameConfigDto.roundTime(), playerManager, meObserver, opponentObserver);
        return new LocalGame(board, playerManager, roundTimer);
    }

    private Board createBoard(BoardSize boardSize, Observer... observers) {
        int rows = boardSize.getRows();
        int cols = boardSize.getCols();
        Set<Observer> observerSet = new HashSet<>(Arrays.asList(observers));
        Token[][] tokens = new Token[rows][cols];
        return new Board(tokens, observerSet);
    }

    private List<Player> createPlayers(PlayerTime playerTime, String myPlayerName, String opponentName, Observer meObserver, Observer opponentObserver) {
        Player mePlayer = createPlayer(myPlayerName, playerTime, meObserver);
        Player opponentPlayer = createPlayer(opponentName, playerTime, opponentObserver);
        return new ArrayList<>(List.of(mePlayer, opponentPlayer));
    }

    private Player createPlayer(String playerName, PlayerTime playerTime, Observer observer) {
        Time time = new Time(playerTime.getMillis());
        Set<Observer> observers = observer != null ? new HashSet<>(Set.of(observer)) : new HashSet<>();
        return new Player(playerName, Token.RED, time, observers);
    }

    private PlayerManager createPlayerManager(List<Player> players, FirstPlayer firstPlayer) {
        if (firstPlayer == FirstPlayer.ME) {
            int indexCurrPlayer = 0;
            return new PlayerManager(players, indexCurrPlayer);
        } else if (firstPlayer == FirstPlayer.OPPONENT) {
            int indexCurrPlayer = 1;
            return new PlayerManager(players, indexCurrPlayer);
        } else {
            int indexCurrPlayer = new Random().nextInt(2);
            return new PlayerManager(players, indexCurrPlayer);
        }
    }

    private RoundTimer createRoundTimer(RoundTime roundTime, PlayerManager playerManager, Observer... observers) {
        Time time = new Time(roundTime.getMillis());
        Set<Observer> observerSet = new HashSet<>(Arrays.asList(observers));
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        return new RoundTimer(time, playerManager, scheduledExecutorService, observerSet);
    }
}
