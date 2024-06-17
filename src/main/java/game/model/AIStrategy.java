package game.model;

public interface AIStrategy {
    int getBestMove(Token[][] tokens, Token AIToken);
}
