package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class MoveCommandTest extends BaseUnitTest {
    @Autowired
    private TableService tableService;

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
        Toy placedToy = placeCommand.execute("0,0,NORTH");

        assertNotNull(placedToy);

        Toy toy = iCommand.execute("");

        log.info(toy.toString());

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
        Toy placedToy = placeCommand.execute("3,3,NORTH");

        assertNotNull(placedToy);

        iCommand.execute("");
        Toy toy = iCommand.execute("");

        log.info(toy.toString());
        assertEquals(DirectionType.NORTH, toy.getDirection());
        assertEquals(3, toy.getPosition().getHorizontal());
        assertEquals(4, toy.getPosition().getVertical());
    }
}