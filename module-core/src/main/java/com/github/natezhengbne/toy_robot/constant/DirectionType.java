package com.github.natezhengbne.toy_robot.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DirectionType {
    NORTH("WEST","EAST"),
    SOUTH("EAST","WEST"),
    WEST("SOUTH","NORTH"),
    EAST("NORTH","SOUTH");

    @Getter
    private final String leftValue;
    @Getter
    private final String rightValue;


}
