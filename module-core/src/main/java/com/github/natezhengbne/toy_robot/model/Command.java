package com.github.natezhengbne.toy_robot.model;

import com.github.natezhengbne.toy_robot.constant.CommandType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Command {
    private CommandType commandType;
    private List<String> args;
}
