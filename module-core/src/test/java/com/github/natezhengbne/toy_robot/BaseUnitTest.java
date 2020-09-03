package com.github.natezhengbne.toy_robot;

import com.github.natezhengbne.toy_robot.command.CommandFactory;
import com.github.natezhengbne.toy_robot.command.ICommand;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BaseUnitTest {
    @Autowired
    public CommandFactory commandFactory;

    public ICommand iCommand;

}
