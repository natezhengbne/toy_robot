package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Position;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.IdGenerator;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlaceCommand extends AbstractCommand {
    @Override
    public CommandType getType() {
        return CommandType.PLACE;
    }

    @Autowired
    private TableService tableService;

    /**
     *
     * @param parameter PLACE X,Y,F,(name)
     * @return
     */
    @Override
    public Toy execute(String parameter) {
        String[] cmdArray = parameter.split(",");
        Position position = Position.builder()
                .horizontal(Integer.parseInt(cmdArray[0]))
                .vertical(Integer.parseInt(cmdArray[1]))
                .build();
        DirectionType direction = DirectionType.valueOf(cmdArray[2]);
        String toyName = cmdArray.length>3?cmdArray[3]:IdGenerator.getId();

        Toy toy = Toy.builder()
                .name(toyName)
                .position(position)
                .direction(direction)
                .nextPosition(next(position, direction))
                .build();

        if(!tableService.isValidPosition(toy.getPosition())){
            log.info("Stay. toy: "+toy);
            return null;
        }

        if(!tableService.place(toy)){
            log.error("Toy place error, toy: "+toy);
            return null;
        }


        return toy;
    }

}
