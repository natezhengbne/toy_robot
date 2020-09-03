package com.github.natezhengbne.toy_robot.command;

import com.github.natezhengbne.toy_robot.constant.CommandType;
import com.github.natezhengbne.toy_robot.model.Command;
import com.github.natezhengbne.toy_robot.model.Toy;

public interface ICommand {

    CommandType getType();

    Toy execute(Command cmd);

}
