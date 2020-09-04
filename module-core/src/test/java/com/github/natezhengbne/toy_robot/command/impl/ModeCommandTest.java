package com.github.natezhengbne.toy_robot.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ModeCommandTest extends BaseUnitTest{
    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.MODE.toString());
    }

    @Test
    void getType() {
        assertEquals(iCommand.getType(), CommandType.MODE);
    }

    @Test
    void singleTest() {
        iCommand.execute(Command.builder().args(Arrays.asList(ModeType.SINGLE.toString())).build());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());

        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());
        assertNotNull(placeResponse.getResult());

        Response placeResponse2 = placeCommand.execute(Command.builder().args(Arrays.asList("0","1","NORTH")).build());
        assertNull(placeResponse2.getResult());
    }

    @Test
    void multiEatTest() {
        iCommand.execute(Command.builder().args(Arrays.asList(ModeType.MULTI_EAT.toString())).build());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH","andy")).build());
        assertNotNull(placeResponse.getResult());
        Response placeResponse2 = placeCommand.execute(Command.builder().args(Arrays.asList("0","1","NORTH","ben")).build());
        assertNotNull(placeResponse2.getResult());

        ICommand moveCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
        moveCommand.execute(Command.builder().args(Arrays.asList("andy")).build());

        assertEquals(1, tableService.getTable().getToys().keySet().size());
    }

    @Test
    void multiBounceTest() {
        iCommand.execute(Command.builder().args(Arrays.asList(ModeType.MULTI_BOUNCE.toString())).build());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Response placeResponse1 = placeCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH","andy")).build());
        Response placeResponse2 = placeCommand.execute(Command.builder().args(Arrays.asList("0","1","WEST","jack")).build());
        Response placeResponse3 = placeCommand.execute(Command.builder().args(Arrays.asList("0","2","SOUTH","ben")).build());

        ICommand moveCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
        moveCommand.execute(Command.builder().args(Arrays.asList("andy")).build());

        Map<String,Toy> toys = tableService.getTable().getToys();
        log.info(tableService.getTable().toString());

        assertEquals(0, toys.get("andy").getPosition().getHorizontal());
        assertEquals(1, toys.get("andy").getPosition().getVertical());
        assertEquals(0, toys.get("andy").getNextPosition().getHorizontal());
        assertEquals(2, toys.get("andy").getNextPosition().getVertical());

        assertEquals(0, toys.get("jack").getPosition().getHorizontal());
        assertEquals(2, toys.get("jack").getPosition().getVertical());
        assertEquals(-1, toys.get("jack").getNextPosition().getHorizontal());
        assertEquals(2, toys.get("jack").getNextPosition().getVertical());

        assertEquals(0, toys.get("ben").getPosition().getHorizontal());
        assertEquals(3, toys.get("ben").getPosition().getVertical());
        assertEquals(0, toys.get("ben").getNextPosition().getHorizontal());
        assertEquals(2, toys.get("ben").getNextPosition().getVertical());
    }
}