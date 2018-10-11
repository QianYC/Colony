package com.colony.server.service;

import com.colony.server.entity.Command;

public interface CommandService {
    void addCommand(Command command);

    void processCommand(long time);
}
