package com.github.natezhengbne.toy_robot.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ExtendCommandTest extends BaseUnitTest {
    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.EXTEND.toString());
    }

    @Test
    void getType() {
        assertEquals(iCommand.getType(), CommandType.EXTEND);
    }

    @Test
    void normal() {
        Response response = iCommand.execute(Command.builder().args(Arrays.asList("10","10")).build());
        assertEquals(true, response.getSuccess());

        assertEquals(10, tableService.getTable().getVerticalLength());
        assertEquals(10, tableService.getTable().getHorizontalLength());
    }

    @Test
    void negativeNumber() {
        Response response = iCommand.execute(Command.builder().args(Arrays.asList("-1","10")).build());
        assertEquals(false, response.getSuccess());
    }

    @Test
    void minimalTable() {
        Response response = iCommand.execute(Command.builder().args(Arrays.asList("1","1")).build());
        assertEquals(true, response.getSuccess());

        assertEquals(1, tableService.getTable().getVerticalLength());
        assertEquals(1, tableService.getTable().getHorizontalLength());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("1","1","NORTH")).build());
        assertEquals(false, placeResponse.getSuccess());

    }

    @Test
    void minimalTableAndMove() {
        Response response = iCommand.execute(Command.builder().args(Arrays.asList("1","1")).build());
        assertEquals(true, response.getSuccess());

        assertEquals(1, tableService.getTable().getVerticalLength());
        assertEquals(1, tableService.getTable().getHorizontalLength());

        ICommand placeCommand2 = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse2 = placeCommand2.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());
        assertEquals(true, placeResponse2.getSuccess());

        ICommand moveCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
        Response moveResponse = moveCommand.execute(Command.builder().args(null).build());
        Toy toy = objectMapper.convertValue(moveResponse.getResult(), Toy.class);
        assertEquals(true, moveResponse.getSuccess());
        assertEquals(0, toy.getPosition().getHorizontal());
        assertEquals(0, toy.getPosition().getHorizontal());

    }
}