package com.github.natezhengbne.toy_robot.command;

import com.github.natezhengbne.toy_robot.constant.CommandType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CommandFactory {

    private final static Map<CommandType, ICommand> commands = new HashMap();

    public void register(@NonNull ICommand iCommand){
        commands.put(iCommand.getType(), iCommand);
    }

    public ICommand getCommand(@NonNull String type){
        return commands.get(CommandType.valueOf(type.trim().toUpperCase()));
    }
}
