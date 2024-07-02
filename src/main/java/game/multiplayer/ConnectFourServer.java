package game.multiplayer;

import game.dto.GameConfigDto;
import game.dto.SocketMsgDto;
import game.dto.RemotePlayerDataDto;
import game.model.Game;
import game.model.GameFactory;
import game.model.Token;
import game.oo.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectFourServer implements MsgHandler {
    private final ServerSocket serverSocket;
    private final List<SocketHandler> socketHandlers;
    private RemotePlayerDetail hostBuffer;
    private RemotePlayerDetail guestBuffer;
    private final int MAX_PLAYERS = 2;
    private int readyPlayers;
    private GameConfigDto gameConfigDto;
    private Game game;

    public ConnectFourServer(ServerSocket serverSocket, List<SocketHandler> socketHandlers) {
        this.serverSocket = serverSocket;
        this.socketHandlers = socketHandlers;
    }

    public void runServer() {
        Thread thread = new Thread(this::waitForPlayer);
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    synchronized public void handleMessage(SocketHandler source, SocketMsgDto<?> input) {
        SocketEvent socketEvent = input.socketEvent();
        switch (socketEvent) {
            case REMOTE_PLAYER_DATA -> setPlayerData(input);
            case PLAY -> play(input);
            case STOP -> stopServer(input);
        }
    }

    private void waitForPlayer() {
        int playerNumber = 0;

        while (playerNumber < MAX_PLAYERS) {
            try {
                Socket socket = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                SocketHandler socketHandler = new SocketHandler(socket, ois, oos, this);
                socketHandlers.add(socketHandler);
                Thread thread = new Thread(socketHandler);
                thread.setDaemon(true);
                thread.start();

                if (playerNumber == 0) {
                    this.hostBuffer = new RemotePlayerDetail(socketHandler);
                } else {
                    this.guestBuffer = new RemotePlayerDetail(socketHandler);
                }

                playerNumber++;
            } catch (IOException ioe) {
                return;
            }
        }

        try {
            serverSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void setPlayerData(SocketMsgDto<?> input) {
        RemotePlayerDataDto remotePlayerDataDto = (RemotePlayerDataDto) input.data();
        RolePlayer rolePlayer = remotePlayerDataDto.rolePlayer();
        String playerName = remotePlayerDataDto.playerName();
        if (rolePlayer == RolePlayer.HOST) {
            hostBuffer.setPlayerName(playerName);
            this.gameConfigDto = remotePlayerDataDto.gameConfigDto();
        } else {
            guestBuffer.setPlayerName(playerName);
        }
        incrReadyPlayers();
    }

    private void incrReadyPlayers() {
        readyPlayers++;

        if (readyPlayers == MAX_PLAYERS) {
            createGame();
            game.start();
        }
    }

    private void createGame() {
        GameFactory gameFactory = new GameFactory();
        String hostName = hostBuffer.getPlayerName();
        Observer hostObserver = hostBuffer.getSocketHandler();
        String guestName = guestBuffer.getPlayerName();
        Observer guestObserver = guestBuffer.getSocketHandler();
        this.game = gameFactory.createGameAgainstFriend(gameConfigDto, hostName, hostObserver, guestName, guestObserver);
    }

    private void play(SocketMsgDto<?> input) {
        int columnIndex = (int) input.data();
        game.play(columnIndex);
    }

    private void stopServer(SocketMsgDto<?> input) {
        Token playerId = (Token) input.data();

        if (game != null) game.stop(playerId);
        socketHandlers.forEach(SocketHandler::close);
        try {
            serverSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
