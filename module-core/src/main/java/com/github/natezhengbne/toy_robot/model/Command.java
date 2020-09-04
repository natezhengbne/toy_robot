package com.github.natezhengbne.toy_robot.model;

import com.github.natezhengbne.toy_robot.constant.CommandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Command {
    private CommandType commandType;
    private List<String> args;
}
