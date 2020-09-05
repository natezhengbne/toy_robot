package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class ReportCommand extends AbstractCommand {
    @Autowired
    private TableService tableService;

    @Override
    public CommandType getType() {
        return CommandType.REPORT;
    }

    @Override
    public Response execute(Command cmd) {
        Toy toy = tableService.getToyOnTable((cmd.getArgs()!=null && cmd.getArgs().size()>0)?cmd.getArgs().get(0):null);
        if(toy==null){
            log.error("toy is null. "+cmd.toString());
            return buildFailedResponse("Place first");

        }
        return buildSuccessResponse(toy);
    }
}
