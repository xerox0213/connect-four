package game.oo;

/**
 * Represents the possible events in the Connect Four game for the Observer pattern.
 */
public enum ConnectFourEvent {
    BOARD_UPDATED,
    OPPONENT_TURN,
    MY_TURN,
    VICTORY,
    GAME_OVER
}
