package com.colony.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 玩家的一次指令
 */
@Getter
@Setter
@NoArgsConstructor
public class Command {

    //标识符、坐标、时间戳
    private int id;
    private int row, col;
    private long time;

    public Command(int id, int row, int col, long time) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.time = time;
    }
}
