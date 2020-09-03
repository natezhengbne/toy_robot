package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResetCommand extends AbstractCommand {
    @Autowired
    private TableService tableService;

    @Override
    public CommandType getType() {
        return CommandType.RESET;
    }

    @Override
    public Toy execute(String parameter) {
        tableService.reset();
        return null;
    }
}
