package com.github.natezhengbne.toy_robot.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.BaseUnitTest;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import com.github.natezhengbne.toy_robot.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PerformanceTest extends BaseUnitTest {
    @Autowired
    private TableService tableService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tableService.reset();
    }

    @Test()
    void simple() {
        assertTimeout(Duration.ofSeconds(1), ()->{
            ICommand extendCommand = super.commandFactory.getCommand(CommandType.EXTEND.toString());
            Response response = extendCommand.execute(Command.builder().args(Arrays.asList("1000000","1000000")).build());

            ICommand placeCommand = super.commandFactory.getCommand(CommandType.PLACE.toString());
            Response placeResponse = placeCommand.execute(Command.builder().args(Arrays.asList("3","3","NORTH")).build());

            ICommand moveCommand = super.commandFactory.getCommand(CommandType.MOVE.toString());
            for(int i=0;i<10000;i++){
                Response moveResponse = moveCommand.execute(Command.builder().args(new ArrayList<>()).build());
            }
            ICommand reportCommand = super.commandFactory.getCommand(CommandType.REPORT.toString());
            Response reportResponse = reportCommand.execute(Command.builder().args(null).build());
            Toy toy = objectMapper.convertValue(reportResponse.getResult(), Toy.class);

            assertEquals(3, toy.getPosition().getHorizontal());
            assertEquals(10003, toy.getPosition().getVertical());
            assertEquals("NORTH", toy.getDirection().toString());
        });



    }
}
