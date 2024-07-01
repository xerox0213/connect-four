package game.dto;

import game.model.BoardSize;
import game.model.FirstPlayer;
import game.model.PlayerTime;
import game.model.RoundTime;

import java.io.Serializable;

public record GameConfigDto(BoardSize boardSize, PlayerTime playerTime, RoundTime roundTime, FirstPlayer firstPlayer) implements Serializable {
}
