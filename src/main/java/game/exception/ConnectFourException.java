package game.exception;

public class ConnectFourException extends Exception {

    public ConnectFourException() {
    }

    public ConnectFourException(String message) {
        super(message);
    }

    public ConnectFourException(Throwable cause) {
        super(cause);
    }
}
