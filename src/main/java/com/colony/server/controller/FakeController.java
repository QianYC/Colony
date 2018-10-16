package com.colony.server.controller;

import com.colony.server.entity.Colony;
import com.colony.server.entity.Command;
import com.colony.server.service.CommandService;
import com.colony.server.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FakeController {

    @Autowired
    CommandService commandService;

    @Autowired
    Colony colony;

    @Autowired
    Util util;

    @GetMapping("/colony")
    public Object getColony(HttpServletRequest request) {
        return colony;
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request) {

        return util.getIp(request);
    }

    @PostMapping("/command")
    public void receiveCommand(HttpServletRequest request, Command command) {
        commandService.addCommand(command);
    }
}
