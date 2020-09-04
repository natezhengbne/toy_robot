package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class ModeCommand extends AbstractCommand {
    @Autowired
    private TableService tableService;

    @Override
    public CommandType getType() {
        return CommandType.MODE;
    }

    @Override
    public Response execute(Command cmd) {
        if(cmd.getArgs()==null || cmd.getArgs().size()!=1 ||
                ModeType.has(cmd.getArgs().get(0))){
            return buildFailedResponse("MODE: "+ Arrays.toString(ModeType.values()));
        }
        tableService.mode(ModeType.valueOf(cmd.getArgs().get(0)));
        return buildSuccessResponse(null);
    }
}
