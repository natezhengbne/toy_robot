package com.github.natezhengbne.toy_robot.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.natezhengbne.toy_robot.constant.CommandType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Response {
    private Boolean success;
    private String error;
    private JsonNode result;
}
