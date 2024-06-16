package game.model;

public class LocalGame {
    private final Board board;
    private final PlayerManager playerManager;
    private final RoundTimer roundTimer;

    public LocalGame(Board board, PlayerManager playerManager, RoundTimer roundTimer) {
        this.board = board;
        this.playerManager = playerManager;
        this.roundTimer = roundTimer;
    }
}
