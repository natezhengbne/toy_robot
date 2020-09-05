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

class ReportCommandTest extends BaseUnitTest {

    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.REPORT.toString());
    }
    @Test
    void getType() {
        assertEquals(iCommand.getType(), CommandType.REPORT);
    }

    @Test
    void errorTest() {
        Response response = iCommand.execute(Command.builder().args(Arrays.asList("10","10")).build());
        assertEquals(false, response.getSuccess());
    }

    @Test
    void simple(){
        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());

        Response placeResponse1 = placeCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());

        ICommand moveCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
        moveCommand.execute(Command.builder().args(null).build());

        Response reportResponse = iCommand.execute(Command.builder().args(null).build());
        Toy toy1 = objectMapper.convertValue(reportResponse.getResult(), Toy.class);

        assertEquals(0, toy1.getPosition().getHorizontal());
        assertEquals(1, toy1.getPosition().getVertical());
        assertEquals("NORTH", toy1.getDirection().toString());
    }
}