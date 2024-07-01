package game.dto;

import game.model.Token;

import java.io.Serializable;

public record MoveDto(Token token, int columnIndex, int rowIndex) implements Serializable {
}
