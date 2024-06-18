package game.model;

import java.util.*;

public class SimpleStrategy implements AIStrategy {
    @Override
    public int getBestMove(Token[][] tokens, Token AIToken) {
        List<Integer> blockingMoves = new ArrayList<>();
        List<Integer> nothingMoves = new ArrayList<>();

        for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
            int rowIndex = BoardAlgorithm.getFreeRowIndex(tokens, colIndex);
            if (rowIndex == -1) continue;

            boolean isWinningMove = BoardAlgorithm.isWinningMove(tokens, colIndex, rowIndex, AIToken);
            if (isWinningMove) return colIndex;

            Token opponentToken = AIToken == Token.RED ? Token.BLUE : Token.RED;
            boolean isBlockingMove = BoardAlgorithm.isWinningMove(tokens, colIndex, rowIndex, opponentToken);
            if (isBlockingMove) blockingMoves.add(colIndex);

            nothingMoves.add(colIndex);
        }

        if (!blockingMoves.isEmpty()) {
            return blockingMoves.getFirst();
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(nothingMoves.size());
            return nothingMoves.get(randomIndex);
        }
    }
}
