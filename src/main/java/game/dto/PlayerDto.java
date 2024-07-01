package game.dto;

import game.model.Token;

import java.io.Serializable;

public record PlayerDto(String name, Token token, long time) implements Serializable {
}
