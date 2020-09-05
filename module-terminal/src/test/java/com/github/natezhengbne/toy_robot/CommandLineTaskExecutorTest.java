package com.github.natezhengbne.toy_robot;

import com.github.natezhengbne.toy_robot.service.InputService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommandLineTaskExecutorTest {
    @Autowired
    private CommandLineTaskExecutor commandLineTaskExecutor;
    @Autowired
    private InputService inputService;

    private String lineBreak = System.getProperty("line.separator");


    @Test
    void testConsoleInput() throws InterruptedException {
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        commandLineTaskExecutor.setPrintStream(new PrintStream(testOut));

        Scanner scanner = new Scanner("test1"+lineBreak+"test2");
        commandLineTaskExecutor.readFromConsole(scanner);

        Thread.sleep(1000);
        assertEquals(inputService.unknownCommand()+lineBreak+inputService.unknownCommand()+lineBreak, testOut.toString());
    }
}