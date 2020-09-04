package com.github.natezhengbne.toy_robot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private Integer vertical;
    private Integer horizontal;

}
