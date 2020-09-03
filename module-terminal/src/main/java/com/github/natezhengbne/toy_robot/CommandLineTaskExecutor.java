package com.github.natezhengbne.toy_robot;

import com.github.natezhengbne.toy_robot.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@Order(1)
@Component
@Profile("!inputTest")
public class CommandLineTaskExecutor implements CommandLineRunner {
    @Autowired
    private InputService inputService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        boolean keepRunning = true;
        while (scanner.hasNext() && keepRunning) {
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("EXIT")){
                keepRunning = false;
                System.out.println("Bye.");
                break;
            }
            String result = inputService.handle(input);
            if(result!=null){
                System.out.println(result);
            }

        }
    }

}
