package com.github.natezhengbne.toy_robot.command;

import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Response;

public interface ICommand {

    CommandType getType();

    Response execute(Command cmd);

}
