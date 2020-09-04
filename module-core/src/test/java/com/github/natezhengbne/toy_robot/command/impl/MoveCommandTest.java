package com.github.natezhengbne.toy_robot.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class MoveCommandTest extends BaseUnitTest {
    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
    }

    @Test
    void getType() {
        assertEquals(iCommand.getType(), CommandType.MOVE);
    }

    @Test
    void execute() {
        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());

        Response moveResponse = iCommand.execute(Command.builder().args(new ArrayList<>()).build());

        Toy toy = objectMapper.convertValue(moveResponse.getResult(), Toy.class);

        assertEquals(DirectionType.NORTH, toy.getDirection());
        assertEquals(0, toy.getPosition().getHorizontal());
        assertEquals(1, toy.getPosition().getVertical());

        assertEquals(0, toy.getNextPosition().getHorizontal());
        assertEquals(2, toy.getNextPosition().getVertical());

    }

    @DisplayName("The robot is free to roam around the surface of the table, but must be prevented from falling to destruction. Any movement that would result in the robot falling from the table must be prevented, however further valid movement commands must still be allowed.")
    @Test
    void fallingTest(){
        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("3","3","NORTH")).build());

        assertNotNull(placeResponse.getResult());

        iCommand.execute(Command.builder().args(new ArrayList<>()).build());
        Response moveResponse = iCommand.execute(Command.builder().args(new ArrayList<>()).build());

        Toy toy = objectMapper.convertValue(moveResponse.getResult(), Toy.class);

        log.info(toy.toString());
        assertEquals(DirectionType.NORTH, toy.getDirection());
        assertEquals(3, toy.getPosition().getHorizontal());
        assertEquals(4, toy.getPosition().getVertical());
    }
}