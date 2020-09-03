package com.github.natezhengbne.toy_robot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Position {
    private Integer vertical;
    private Integer horizontal;

}
