package com.github.natezhengbne.toy_robot.command;

import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest extends BaseUnitTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCommand() {
        for(CommandType commandType: CommandType.values()){
            assertNotNull(super.commandFactory.getCommand(commandType.toString()));
        }
    }
}