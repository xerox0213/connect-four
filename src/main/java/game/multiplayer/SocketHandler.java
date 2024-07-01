package game.multiplayer;

import game.dto.ConnectFourEventDto;
import game.dto.SocketMsgDto;
import game.oo.ConnectFourEvent;
import game.oo.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketHandler implements Runnable {
    private final Socket socket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private MsgHandler msgHandler;

    public SocketHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, MsgHandler msgHandler) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.msgHandler = msgHandler;
    }

    public SocketHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                SocketMsgDto<?> socketMsgDto = (SocketMsgDto<?>) ois.readObject();
                msgHandler.handleMessage(this, socketMsgDto);
            } catch (IOException | ClassNotFoundException ioe) {
                close();
                return;
            }
        }
    }

    public void send(SocketMsgDto<?> socketMsgDto) {
        try {
            oos.writeObject(socketMsgDto);
        } catch (IOException ioe) {
            close();
        }
    }

    public void setMsgHandler(MsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public void close() {
        try {
            socket.close();
            ois.close();
            oos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
