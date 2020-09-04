package com.github.natezhengbne.toy_robot.model;

import com.github.natezhengbne.toy_robot.constant.ModeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private String id;
    private Integer verticalLength;
    private Integer horizontalLength;

    private Map<String, Toy> toys;
    private ModeType modeType;
}
