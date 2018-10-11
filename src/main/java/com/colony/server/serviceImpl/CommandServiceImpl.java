package com.colony.server.serviceImpl;

import com.colony.server.entity.Colony;
import com.colony.server.entity.Command;
import com.colony.server.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;

@Service
public class CommandServiceImpl implements CommandService {

    @Value("${maxCost}")
    private int queueNum;

    @Autowired
    private Colony colony;

    private LinkedList<Command>[] cmdQueues;
    private Logger logger = LoggerFactory.getLogger(getClass());

//    public CommandServiceImpl(){
//        logger.info("maxCost = {}", queueNum);
//    }

    @PostConstruct
    public void init() {
        logger.info("initializing command queues, num = {}", queueNum);
        cmdQueues = new LinkedList[queueNum + 1];
        for (int i = 0; i <= queueNum; i++) {
            cmdQueues[i] = new LinkedList<>();
        }
    }

    @Override
    public void addCommand(Command command) {
        cmdQueues[0].offerLast(command);
    }

    @Override
    public void processCommand(long time) {
        //先处理上个时刻的队列
        while (!cmdQueues[1].isEmpty()) {
            Command command = cmdQueues[1].poll();
            if (!colony.occupy(command)) {
                int delta = colony.getWaitTime(command.getRow(), command.getCol());
                cmdQueues[delta].offerLast(command);
            }
        }
        //队列时间-1
        for (int i = 0; i < queueNum; i++) {
            cmdQueues[i] = cmdQueues[i + 1];
        }
        cmdQueues[queueNum] = new LinkedList<>();
        //处理当前接受的command
        while (!cmdQueues[0].isEmpty()) {
            Command command = cmdQueues[0].poll();
            if (command.getTime() == time) {
                if (!colony.occupy(command)) {
                    int delta = colony.getWaitTime(command.getRow(), command.getCol());
                    cmdQueues[delta].offerLast(command);
                }
            }
        }
        
    }
}
