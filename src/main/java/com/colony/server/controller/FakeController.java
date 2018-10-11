package com.colony.server.controller;

import com.colony.server.entity.Colony;
import com.colony.server.entity.Command;
import com.colony.server.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeController {

    @Autowired
    CommandService commandService;

    @Autowired
    Colony colony;

    @GetMapping("/colony")
    public Object getColony() {
        return colony;
    }

    @PostMapping("/command")
    public void receiveCommand(Command command) {
        commandService.addCommand(command);
    }
}
