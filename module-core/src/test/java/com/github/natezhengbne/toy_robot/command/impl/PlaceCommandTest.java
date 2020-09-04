package com.github.natezhengbne.toy_robot.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceCommandTest extends BaseUnitTest {

    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
    }

    @DirtiesContext
    @Test
    void execute() {
        Response placeResponse = iCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());
        Toy toy = objectMapper.convertValue(placeResponse.getResult(), Toy.class);

        assertEquals(DirectionType.NORTH, toy.getDirection());
        assertEquals(0, toy.getPosition().getHorizontal());
        assertEquals(0, toy.getPosition().getVertical());

        assertEquals(0, toy.getNextPosition().getHorizontal());
        assertEquals(1, toy.getNextPosition().getVertical());


    }

    @DisplayName("Put the toy on same point")
    @Test
    @DirtiesContext
    void samePosition() {
        Response placeResponse1 = iCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());
        Response placeResponse2 = iCommand.execute(Command.builder().args(Arrays.asList("0","0","NORTH")).build());

        Toy toy1 = objectMapper.convertValue(placeResponse1.getResult(), Toy.class);
        Toy toy2 = objectMapper.convertValue(placeResponse2.getResult(), Toy.class);

        assertEquals(false, tableService.getTable().getToys().containsKey(toy1.getName()));
        assertEquals(true, tableService.getTable().getToys().containsKey(toy2.getName()));


    }
}