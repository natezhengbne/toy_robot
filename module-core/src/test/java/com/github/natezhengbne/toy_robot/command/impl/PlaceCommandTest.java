package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

class PlaceCommandTest extends BaseUnitTest {

    @Autowired
    private TableService tableService;

    @BeforeEach
    void setUp() {
        tableService.reset();
        iCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
    }

    @DirtiesContext
    @Test
    void execute() {
        Toy toy = iCommand.execute("0,0,NORTH");

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
        Toy toy1 = iCommand.execute("0,0,NORTH");
        Toy toy2 = iCommand.execute("0,0,NORTH");

        assertNotNull(toy1);
        assertNull(toy2);

    }
}