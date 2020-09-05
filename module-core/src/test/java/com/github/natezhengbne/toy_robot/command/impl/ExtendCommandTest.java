package com.github.natezhengbne.toy_robot.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
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
    void execute() {
        Response response = iCommand.execute(Command.builder().args(Arrays.asList("10","10")).build());
        assertEquals(true, response.getSuccess());

        assertEquals(10, tableService.getTable().getVerticalLength());
        assertEquals(10, tableService.getTable().getHorizontalLength());
    }
}