package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class RightCommandTest extends BaseUnitTest {
    @Autowired
    private TableService tableService;

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
        Toy placedToy = placeCommand.execute("1,1,NORTH");
        assertNotNull(placedToy);

        Toy toy = iCommand.execute("");

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
        Toy placedToy = placeCommand.execute("1,1,NORTH");
        assertNotNull(placedToy);

        iCommand.execute("");
        Toy toy = iCommand.execute("");

        log.info(toy.toString());

        assertEquals(DirectionType.SOUTH, toy.getDirection());
        assertEquals(1, toy.getPosition().getHorizontal());
        assertEquals(1, toy.getPosition().getVertical());

        assertEquals(1, toy.getNextPosition().getHorizontal());
        assertEquals(0, toy.getNextPosition().getVertical());
    }
}