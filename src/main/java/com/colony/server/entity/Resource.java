package com.colony.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 资源，殖民地的基本单位
 */
@NoArgsConstructor
@Getter
@Setter
public class Resource {

    @Value("${maxCost}")
    private int maxCost;

    //占领者和抢占这块资源的代价时间
    private int holder = -1;
    private int cost = -1;

    public boolean occupy(int holder) {
        if (this.cost <= 0) {
            this.holder = holder;
            this.cost = maxCost;
            return true;
        } else {
            return false;
        }
    }

    public boolean isOccupied() {
        return holder != -1;
    }

    /**
     * 每过一个时间单位，占领资源的代价就减少
     */
    public void update(){
        cost--;
        cost = Math.max(0, cost);
    }

}
