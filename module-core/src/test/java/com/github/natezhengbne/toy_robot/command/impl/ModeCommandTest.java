package com.github.natezhengbne.toy_robot.command.impl;

import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ModeCommandTest extends BaseUnitTest{
    @Autowired
    private TableService tableService;

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
        iCommand.execute(ModeType.SINGLE.toString());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Toy toy1 = placeCommand.execute("0,0,NORTH");
        assertNotNull(toy1);
        Toy toy2 = placeCommand.execute("0,1,NORTH");
        assertNull(toy2);
    }

    @Test
    void multiEatTest() {
        iCommand.execute(ModeType.MULTI_EAT.toString());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Toy toy1 = placeCommand.execute("0,0,NORTH");
        assertNotNull(toy1);
        Toy toy2 = placeCommand.execute("0,1,NORTH");
        assertNotNull(toy2);

        assertEquals(2, tableService.getTable().getToys().keySet().size());
    }

    @Test
    void multiBounceTest() {
        iCommand.execute(ModeType.MULTI_BOUNCE.toString());

        ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
        Toy toy1 = placeCommand.execute("0,0,NORTH,andy");
        assertNotNull(toy1);
        Toy toy2 = placeCommand.execute("0,1,WEST,jack");
        assertNotNull(toy2);

        ICommand moveCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
        Toy toy3 = moveCommand.execute("andy");

        Map<String,Toy> toys = tableService.getTable().getToys();
        log.info(tableService.getTable().toString());

        assertEquals(0, toys.get("andy").getPosition().getHorizontal());
        assertEquals(1, toys.get("andy").getPosition().getVertical());
        assertEquals(0, toys.get("andy").getNextPosition().getHorizontal());
        assertEquals(2, toys.get("andy").getNextPosition().getVertical());

        assertEquals(0, toys.get("jack").getPosition().getHorizontal());
        assertEquals(2, toys.get("jack").getPosition().getVertical());
        assertEquals(-1, toys.get("jack").getNextPosition().getHorizontal());
        assertEquals(3, toys.get("jack").getNextPosition().getVertical());
    }
}