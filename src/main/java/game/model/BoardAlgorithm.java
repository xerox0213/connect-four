package game.model;

public class BoardAlgorithm {

    public static boolean isBoardFull(Token[][] tokens) {
        int colIndex = 0;
        while (colIndex < tokens.length) {
            if (getFreeRowIndex(tokens, colIndex) != -1) return false;
            colIndex++;
        }
        return true;
    }

    public static boolean isOutsideBoard(Token[][] tokens, int colIndex) {
        return colIndex < 0 || colIndex >= tokens.length;
    }

    public static boolean isOutsideBoard(Token[][] tokens, int colIndex, int rowIndex) {
        return isOutsideBoard(tokens, colIndex) || (rowIndex < 0 || rowIndex >= tokens[0].length);
    }

    public static int getFreeRowIndex(Token[][] tokens, int colIndex) {
        int rowIndex = tokens[0].length - 1;
        while (rowIndex > -1) {
            if (tokens[colIndex][rowIndex] == null) return rowIndex;
            rowIndex--;
        }
        return -1;
    }

    public static boolean isWinningMove(Token[][] tokens, int tokenColIndex, int tokenRowIndex, Token playedToken) {
        Directions[] directions = Directions.values();
        for (Directions direction : directions) {
            int numberTokensAligned = 1;
            for (int i = 0; i < 2; i++) {
                int dx = i == 0 ? direction.getDx() : direction.getOppositeDx();
                int dy = i == 0 ? direction.getDy() : direction.getOppositeDy();
                int colIndex = tokenColIndex + dx;
                int rowIndex = tokenRowIndex + dy;
                while (!isOutsideBoard(tokens, colIndex, rowIndex)) {
                    Token token = tokens[colIndex][rowIndex];
                    if (token == playedToken) {
                        numberTokensAligned++;
                        if (numberTokensAligned >= 4) return true;
                        colIndex += dx;
                        rowIndex += dy;
                    } else break;
                }
            }
        }
        return false;
    }
}
