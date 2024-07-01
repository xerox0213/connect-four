package game.dto;

import game.multiplayer.SocketEvent;

import java.io.Serializable;

public record SocketMsgDto<T>(SocketEvent socketEvent, T data) implements Serializable {
}
