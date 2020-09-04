package com.github.natezhengbne.toy_robot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.command.CommandFactory;
import com.github.natezhengbne.toy_robot.command.ICommand;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;
import com.github.natezhengbne.toy_robot.model.Toy;
import lombok.NonNull;
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
    @Autowired
    private ObjectMapper objectMapper;

    public String handle(@NonNull String input){
        String[] commandArray = input.split(" ");
        if(commandArray.length>2||commandArray.length==0){
            return this.unknownCommand();
        }
        ICommand iCommand = null;
        try{
            iCommand = commandFactory.getCommand(commandArray[0].toUpperCase());
        }catch (Exception e){
            log.error(input, e);
            return this.unknownCommand();
        }

        Command cmd = null;
        if(commandArray.length==2){
            cmd = Command.builder().commandType(iCommand.getType()).args(Arrays.asList(commandArray[1].split(","))).build();
        }else{
            cmd = Command.builder().commandType(iCommand.getType()).args(new ArrayList<>()).build();
        }

        Response response = iCommand.execute(cmd);

        if(response.getSuccess() && cmd.getCommandType() == CommandType.REPORT){
            Toy toy = objectMapper.convertValue(response.getResult(), Toy.class);
            return "Output: "+toy.getPosition().getHorizontal()+","+toy.getPosition().getVertical()+","+toy.getDirection().toString();
        }
        if(!response.getSuccess()){
            return "Error: "+response.getError();
        }
        return null;
    }

    public String unknownCommand(){
        return "Command: "+Arrays.toString(CommandType.values());
    }
}
