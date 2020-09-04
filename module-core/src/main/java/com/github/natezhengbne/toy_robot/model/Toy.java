package com.github.natezhengbne.toy_robot.model;

import com.github.natezhengbne.toy_robot.constant.DirectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Toy {

    private String name;

    private Position prePosition;

    private Position position;

    private DirectionType direction;

    private Position nextPosition;
}
