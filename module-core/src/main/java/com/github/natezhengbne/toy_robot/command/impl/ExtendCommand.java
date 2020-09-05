package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExtendCommand extends AbstractCommand {
    @Autowired
    private TableService tableService;

    @Override
    public CommandType getType() {
        return CommandType.EXTEND;
    }

    @Override
    public Response execute(Command cmd) {
        if(cmd.getArgs().size()!=2){
            return buildFailedResponse("Command: EXTEND Integer,Integer");
        }
        try{
            tableService.getTable().setHorizontalLength(Integer.parseInt(cmd.getArgs().get(0)));
            tableService.getTable().setVerticalLength(Integer.parseInt(cmd.getArgs().get(1)));
        }catch (Exception e){
            return buildFailedResponse("Command: EXTEND Integer,Integer");
        }
        return Response.builder().success(true).build();
    }
}
