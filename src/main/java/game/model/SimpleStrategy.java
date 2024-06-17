package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleStrategy implements AIStrategy {
    @Override
    public int getBestMove(Token[][] tokens, Token AIToken) {
        List<Integer> colIndexes = new ArrayList<>();

        for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
            int rowIndex = BoardAlgorithm.getFreeRowIndex(tokens, colIndex);
            if (rowIndex == -1) continue;

            boolean isWinningMove = BoardAlgorithm.isWinningMove(tokens, colIndex, rowIndex, AIToken);
            if (isWinningMove) return colIndex;

            Token opponentToken = AIToken == Token.RED ? Token.BLUE : Token.RED;
            boolean isBlockingWinningMove = BoardAlgorithm.isWinningMove(tokens, colIndex, rowIndex, opponentToken);
            if (isBlockingWinningMove) return colIndex;

            colIndexes.add(colIndex);
        }

        Random random = new Random();
        int randomIndex = random.nextInt(colIndexes.size());
        return colIndexes.get(randomIndex);
    }
}
