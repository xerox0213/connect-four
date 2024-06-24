package game.dto;

import game.model.Token;

public record GameDto(PlayerDto you, PlayerDto opponent, Token[][] tokens, long roundTime) {
}
