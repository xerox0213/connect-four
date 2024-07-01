package game.multiplayer;

import game.oo.Observer;

public class RemotePlayerDetail {
    private final Observer observer;
    private String playerName;

    public RemotePlayerDetail(Observer observer) {
        this.observer = observer;
    }

    public Observer getSocketHandler() {
        return observer;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}