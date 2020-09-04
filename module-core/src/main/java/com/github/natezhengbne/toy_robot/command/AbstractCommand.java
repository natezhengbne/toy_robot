package com.github.natezhengbne.toy_robot.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.natezhengbne.toy_robot.model.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommand implements ICommand, InitializingBean {

    @Autowired
    public CommandFactory commandFactory;
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void afterPropertiesSet() throws Exception {
        commandFactory.register(this);
    }

    public Response buildSuccessResponse(Object result){
        return Response.builder()
                .success(true)
                .result(result==null? null :objectMapper.valueToTree(result))
                .build();
    }

    public Response buildFailedResponse(String error){
        return Response.builder().success(false).error(error).build();
    }

}
