package game.model;

public enum Directions {
    VERTICAL(1, 0),
    HORIZONTAL(0, 1),
    DIAGONAL_UP_LEFT_TO_RIGHT(1, 1),
    DIAGONAL_DOWN_LEFT_TO_RIGHT(1, -1);

    private final int dx;
    private final int dy;

    Directions(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getOppositeDx() {
        return -dx;
    }

    public int getOppositeDy() {
        return -dy;
    }
}
