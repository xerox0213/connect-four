package game.exception;

public class ConnectFourException extends Exception {
    private final ConnectFourError connectFourError;

    public ConnectFourException(ConnectFourError connectFourError) {
        this.connectFourError = connectFourError;
    }

    public ConnectFourError getConnectFourError() {
        return connectFourError;
    }
}
