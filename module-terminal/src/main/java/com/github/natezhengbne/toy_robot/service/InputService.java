package com.github.natezhengbne.toy_robot.service;

import com.github.natezhengbne.toy_robot.command.CommandFactory;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Toy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
public class InputService {
    @Autowired
    private CommandFactory commandFactory;

    public String handle(String input){
        String[] commandArray = input.split(" ");
        if(commandArray.length>2){
            return null;
        }
        ICommand iCommand = null;
        try{
            iCommand = commandFactory.getCommand(commandArray[0].toUpperCase());
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
        if(iCommand==null){
           return null;
        }
        Command cmd = null;
        if(commandArray.length==2){
            cmd = Command.builder().commandType(iCommand.getType()).args(Arrays.asList(commandArray[1].split(","))).build();
        }else{
            cmd = Command.builder().commandType(iCommand.getType()).args(new ArrayList<>()).build();
        }

        Toy toy = iCommand.execute(cmd);

        if(toy!=null && cmd.getCommandType() == CommandType.REPORT){
            return "Output: "+toy.getPosition().getHorizontal()+","+toy.getPosition().getVertical()+","+toy.getDirection().toString();
        }
        return null;
    }
}
