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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LeftCommandTest extends BaseUnitTest {

    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.LEFT.toString());
    }

    @Test
    void getType() {
        assertEquals(iCommand.getType(), CommandType.LEFT);
    }

    @Test
    void execute() {
        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("1","1","NORTH")).build());
        assertNotNull(placeResponse);
        assertEquals(true, placeResponse.getSuccess());

        Response leftResponse = iCommand.execute(Command.builder().args(new ArrayList<>()).build());
        Toy toy = objectMapper.convertValue(leftResponse.getResult(), Toy.class);
        log.info(toy.toString());

        assertEquals(DirectionType.WEST, toy.getDirection());
        assertEquals(1, toy.getPosition().getHorizontal());
        assertEquals(1, toy.getPosition().getVertical());

        assertEquals(0, toy.getNextPosition().getHorizontal());
        assertEquals(1, toy.getNextPosition().getVertical());
    }
}