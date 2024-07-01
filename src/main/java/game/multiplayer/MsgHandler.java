package game.multiplayer;

import game.dto.SocketMsgDto;

public interface MsgHandler {
    void handleMessage(SocketHandler socketHandler, SocketMsgDto<?> socketMsgDto);
}
