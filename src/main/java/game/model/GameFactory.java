package game.model;

import game.dto.GameConfigDto;
import game.oo.Observer;

import java.util.*;

public class GameFactory {

    public Game createGameAgainstComputer(GameConfigDto gameConfigDto, String hostPlayerName, Observer hostObserver, String computerName) {
        Board board = createBoard(gameConfigDto.boardSize(), hostObserver);
        Player hostPlayer = createPlayer(hostPlayerName, gameConfigDto.playerTime(), Token.BLUE, hostObserver);
        AIPlayer aiPlayer = createAIPlayer(computerName, gameConfigDto.playerTime(), Token.RED, board);
        List<Player> players = new ArrayList<>(List.of(hostPlayer, aiPlayer));
        PlayerManager playerManager = createPlayerManager(players, gameConfigDto.firstPlayer());
        RoundTimer roundTimer = createRoundTimer(gameConfigDto.roundTime(), playerManager, hostObserver);
        LocalGame localGame = new LocalGame(board, playerManager, roundTimer);
        aiPlayer.setLocalGame(localGame);
        return localGame;
    }

    public Game createGameAgainstFriend(GameConfigDto gameConfigDto, String hostPlayerName, Observer hostObserver, String opponentName, Observer opponentObserver) {
        Board board = createBoard(gameConfigDto.boardSize(), hostObserver, opponentObserver);
        Player hostPlayer = createPlayer(hostPlayerName, gameConfigDto.playerTime(), Token.BLUE, hostObserver);
        Player opponentPlayer = createPlayer(opponentName, gameConfigDto.playerTime(), Token.RED, opponentObserver);
        List<Player> players = new ArrayList<>(List.of(hostPlayer, opponentPlayer));
        PlayerManager playerManager = createPlayerManager(players, gameConfigDto.firstPlayer());
        RoundTimer roundTimer = createRoundTimer(gameConfigDto.roundTime(), playerManager, hostObserver, opponentObserver);
        return new LocalGame(board, playerManager, roundTimer);
    }

    private Board createBoard(BoardSize boardSize, Observer... observers) {
        int rows = boardSize.getRows();
        int cols = boardSize.getCols();
        Set<Observer> observerSet = new HashSet<>(Arrays.asList(observers));
        Token[][] tokens = new Token[cols][rows];
        return new Board(tokens, observerSet);
    }

    private Player createPlayer(String playerName, PlayerTime playerTime, Token token, Observer observer) {
        Time time = new Time(playerTime.getMillis());
        Set<Observer> observers = observer != null ? new HashSet<>(Set.of(observer)) : new HashSet<>();
        return new Player(playerName, token, time, observers);
    }

    private AIPlayer createAIPlayer(String playerName, PlayerTime playerTime, Token token, BoardView boardView) {
        Time time = new Time(playerTime.getMillis());
        Set<Observer> observers = new HashSet<>();
        AIStrategy aiStrategy = new SimpleStrategy();
        return new AIPlayer(playerName, token, time, observers, aiStrategy, boardView);
    }

    private PlayerManager createPlayerManager(List<Player> players, FirstPlayer firstPlayer) {
        if (firstPlayer == FirstPlayer.HOST) {
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
        return new RoundTimer(time, playerManager, observerSet);
    }
}
