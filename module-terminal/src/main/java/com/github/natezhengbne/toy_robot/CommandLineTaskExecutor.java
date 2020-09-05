package com.github.natezhengbne.toy_robot;

import com.github.natezhengbne.toy_robot.service.InputService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Order(1)
@Component
@Profile("!inputTest")
public class CommandLineTaskExecutor implements CommandLineRunner {
    @Autowired
    private InputService inputService;

    @Setter
    private InputStream inputStream;
    @Setter
    private PrintStream printStream;

    @Override
    public void run(String... args) throws InterruptedException {
        Scanner scanner = new Scanner(inputStream==null?System.in:inputStream);
        if(printStream==null){
            printStream = System.out;
        }
        printStream.println("--------  TOY ROBOT STARTED  ---------");
        readFromConsole(scanner);
    }

    public void readFromConsole(Scanner scanner) throws InterruptedException {

        Thread rC = new Thread(() -> {
            while (scanner.hasNext()) {
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("EXIT")){
                    scanner.close();
                    printStream.println("-----------------------------");
                    printStream.println("Bye.");
                    break;
                }
                String result = inputService.handle(input);
                if(result!=null){
                    printStream.println(result);
                }
            }
            if(scanner!=null){
                scanner.close();
            }
        });
        rC.start();
    }

}
