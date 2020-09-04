package com.github.natezhengbne.toy_robot.command;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommand implements ICommand, InitializingBean {

    @Autowired
    public CommandFactory commandFactory;


    @Override
    public void afterPropertiesSet() throws Exception {
        commandFactory.register(this);
    }
}
