package game.dto;

import game.model.Token;

import java.io.Serializable;

public record GameDto(PlayerDto you, PlayerDto opponent, Token[][] tokens, long roundTime) implements Serializable {
}
