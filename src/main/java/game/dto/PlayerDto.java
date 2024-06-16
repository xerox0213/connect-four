package game.dto;

import game.model.Token;

public record PlayerDto(String name, Token token, long time) {
}
