package com.github.natezhengbne.toy_robot.model;

import com.github.natezhengbne.toy_robot.constant.DirectionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Toy {

    private String name;

    private Position prePosition;

    private Position position;

    private DirectionType direction;

    private Position nextPosition;
}
