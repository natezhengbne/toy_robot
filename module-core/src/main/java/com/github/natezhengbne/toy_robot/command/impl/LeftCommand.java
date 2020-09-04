package com.github.natezhengbne.toy_robot.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeftCommand extends AbstractCommand {
    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CommandType getType() {
        return CommandType.LEFT;
    }

    @Override
    public Response execute(Command cmd) {
        Toy toy = tableService.getToyOnTable(cmd.getArgs().size()>0?cmd.getArgs().get(0):null);
        if(toy==null){
            log.error("toy is null"); //todo
            return null;
        }

        Response response = Response.builder()
                .success(true)
                .result(objectMapper.valueToTree(
                        tableService.rotate(toy, DirectionType.valueOf(toy.getDirection().getLeftValue()),
                                tableService.nextPosition(toy.getPosition(), DirectionType.valueOf(toy.getDirection().getLeftValue())))
                ))
                .build();

        return response;

    }
}
