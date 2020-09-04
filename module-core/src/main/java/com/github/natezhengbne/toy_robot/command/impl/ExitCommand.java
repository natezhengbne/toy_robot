package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExitCommand extends AbstractCommand {

    @Override
    public CommandType getType() {
        return CommandType.EXIT;
    }

    @Override
    public Response execute(Command cmd) {
        return null;
    }
}
