package game.model;

public interface Game {
    void start();

    void play(int columnIndex);

    void stop(Token playerId);
}
