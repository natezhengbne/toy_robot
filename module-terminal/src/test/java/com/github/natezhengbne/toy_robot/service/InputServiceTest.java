package com.github.natezhengbne.toy_robot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("inputTest")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InputServiceTest {

    @Autowired
    private TableService tableService;
    @Autowired
    private InputService inputService;

    @BeforeEach
    void setUp() {
        tableService.reset();
    }

    @DisplayName("PLACE-MOVE-REPORT")
    @Test
    void test1() {
        inputService.handle("PLACE 0,0,NORTH");
        inputService.handle("MOVE");
        inputService.handle("MOVE");

        String response = inputService.handle("REPORT");
        assertEquals("Output: 0,2,NORTH", response);
    }

    @DisplayName("PLACE-LEFT-REPORT")
    @Test
    void test2() {
        inputService.handle("PLACE 0,0,NORTH");
        inputService.handle("LEFT");

        String response = inputService.handle("REPORT");
        assertEquals("Output: 0,0,WEST", response);
    }

    @DisplayName("PLACE-MOVE-MOVE-LEFT-MOVE-REPORT")
    @Test
    void test3() {
        inputService.handle("PLACE 1,2,EAST");
        inputService.handle("MOVE");
        inputService.handle("MOVE");
        inputService.handle("LEFT");
        inputService.handle("MOVE");

        String response = inputService.handle("REPORT");
        assertEquals("Output: 3,3,NORTH", response);
    }
}