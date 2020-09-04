package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.command.AbstractCommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Position;
import com.github.natezhengbne.toy_robot.model.Response;
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
     * @param cmd PLACE X,Y,F,(name)
     * @return
     */
    @Override
    public Response execute(Command cmd) {
        Position position = Position.builder()
                .horizontal(Integer.parseInt(cmd.getArgs().get(0)))
                .vertical(Integer.parseInt(cmd.getArgs().get(1)))
                .build();
        DirectionType direction = DirectionType.valueOf(cmd.getArgs().get(2));
        String toyName = cmd.getArgs().size()>3?cmd.getArgs().get(3):IdGenerator.getId();

        Toy toy = Toy.builder()
                .name(toyName)
                .position(position)
                .direction(direction)
                .nextPosition(tableService.nextPosition(position, direction))
                .build();

        if(!tableService.isValidPosition(toy.getPosition())){
            log.info("Stay. toy: "+toy);
            return buildSuccessResponse(null);
        }

        if(!tableService.place(toy)){
            log.error("You can't place there, toy: "+toy);
            return buildFailedResponse("You can't place there");
        }


        return buildSuccessResponse(toy);
    }

}
