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
        if(cmd.getArgs()==null || cmd.getArgs().size()<3){
            return buildFailedResponse("Command: PLACE X,Y,F,(name)");
        }

        Position position = null;
        DirectionType direction = null;
        try {
            Integer h = Integer.parseInt(cmd.getArgs().get(0));
            Integer v = Integer.parseInt(cmd.getArgs().get(1));
            if(h<0 || v<0){
                return buildFailedResponse("Command: PLACE dimensions should >= 0");
            }
            position = Position.builder()
                    .horizontal(h)
                    .vertical(v)
                    .build();
            direction = DirectionType.valueOf(cmd.getArgs().get(2).toUpperCase());
        }catch (Exception e){
            log.error(cmd.toString(), e);
            return buildFailedResponse("Command: PLACE integer,integer,direction,(name)");
        }
        String toyName = cmd.getArgs().size()>3?cmd.getArgs().get(3):IdGenerator.getId();

        Toy toy = Toy.builder()
                .name(toyName)
                .position(position)
                .direction(direction)
                .nextPosition(tableService.nextPosition(position, direction))
                .build();

        if(!tableService.isValidPosition(toy.getPosition())){
            log.error("You can't place there, toy: "+toy);
            return buildFailedResponse("You can't place there");
        }

        if(!tableService.place(toy)){
            log.error("You can't place there, toy: "+toy);
            return buildFailedResponse("You can't place there");
        }


        return buildSuccessResponse(toy);
    }

}
