package game.dto;

import game.multiplayer.RolePlayer;

import java.io.Serializable;

public record RemotePlayerDataDto(RolePlayer rolePlayer, String playerName, GameConfigDto gameConfigDto) implements Serializable {
}
