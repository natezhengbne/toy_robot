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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class RightCommandTest extends BaseUnitTest {
    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.RIGHT.toString());
    }

    @Test
    void getType() {
        assertEquals(iCommand.getType(), CommandType.RIGHT);
    }

    @Test
    void execute() {
        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("1","1","NORTH")).build());
        assertNotNull(placeResponse.getResult());

        Response rightResponse = iCommand.execute(Command.builder().args(new ArrayList<>()).build());
        Toy toy = objectMapper.convertValue(rightResponse.getResult(), Toy.class);
        log.info(toy.toString());

        assertEquals(DirectionType.EAST, toy.getDirection());
        assertEquals(1, toy.getPosition().getHorizontal());
        assertEquals(1, toy.getPosition().getVertical());

        assertEquals(2, toy.getNextPosition().getHorizontal());
        assertEquals(1, toy.getNextPosition().getVertical());
    }

    @Test
    void turnMore() {
        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("1","1","NORTH")).build());
        assertNotNull(placeResponse.getResult());

        iCommand.execute(Command.builder().args(new ArrayList<>()).build());
        Response rightResponse = iCommand.execute(Command.builder().args(new ArrayList<>()).build());
        Toy toy = objectMapper.convertValue(rightResponse.getResult(), Toy.class);

        log.info(toy.toString());

        assertEquals(DirectionType.SOUTH, toy.getDirection());
        assertEquals(1, toy.getPosition().getHorizontal());
        assertEquals(1, toy.getPosition().getVertical());

        assertEquals(1, toy.getNextPosition().getHorizontal());
        assertEquals(0, toy.getNextPosition().getVertical());
    }
}