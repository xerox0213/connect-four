package game.oo;

/**
 * Represents the possible events in the Connect Four game for the Observer pattern.
 */
public enum ConnectFourEvent {
    BOARD_UPDATED,
    OPPONENT_TURN,
    MY_TURN,
    VICTORY,
    GAME_OVER,
    GAME_DRAW,
    GAME_INIT,
    PLAYER_TIME_UPDATED,
    OPPONENT_TIME_UPDATED,
    ROUND_TIME_UPDATED,
    CONNECT_FOUR_SERVER_STARTED
}
