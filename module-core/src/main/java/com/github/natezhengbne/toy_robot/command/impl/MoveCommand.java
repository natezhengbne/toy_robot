package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MoveCommand extends AbstractCommand {
    @Autowired
    private TableService tableService;

    @Override
    public CommandType getType() {
        return CommandType.MOVE;
    }

    @Override
    public Toy execute(Command cmd) {
        Toy toy = tableService.getToyOnTable(cmd.getArgs().size()>0?cmd.getArgs().get(0):null);
        if(toy==null){
            log.error("toy is null"); //todo
            return null;
        }


        if(!tableService.isValidPosition(toy.getNextPosition())){
            log.info("Stay. toy: "+toy);
            return toy;
        }

        tableService.move(toy, tableService.nextPosition(toy.getNextPosition(), toy.getDirection()));

        return toy;
    }
}
