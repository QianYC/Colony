package com.colony.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * 殖民地，代表游戏中的地图
 */
@Getter
@Setter
@NoArgsConstructor
@Component
public class Colony {

    private Resource[][] resources;

    @Value("${colony.row}")
    private int row;
    @Value("${colony.col}")
    private int col;

    @PostConstruct
    public void init(){
        resources = new Resource[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                resources[i][j] = new Resource();
            }
        }
    }
    /**
     * 获得当前殖民地状态
     *
     */
    public Resource[][] getState() {
        return resources;
    }

    /**
     * 更新殖民地状态
     * TODO: 2018/10/10  参数没想好
     */
    public void update(){

    }

    public boolean occupy(Command command) {
        return resources[command.getRow()][command.getCol()].occupy(command.getId());
    }

    public int getWaitTime(int row, int col) {
        return resources[row][col].getCost();
    }
}
