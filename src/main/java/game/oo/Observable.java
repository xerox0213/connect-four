package game.oo;

public interface Observable {
    void addObserver(Observer observer);

    void notifyObservers(ConnectFourEvent e, Object data);
}
