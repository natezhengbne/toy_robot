package com.github.natezhengbne.toy_robot.command;

import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.model.Position;
import lombok.NonNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommand implements ICommand, InitializingBean {

    @Autowired
    private CommandFactory commandFactory;

//    protected Position next(@NonNull Position current, @NonNull DirectionType directionType){
//        Position nextPosition = Position.builder().build();
//        switch (directionType){
//            case EAST:
//                nextPosition.setHorizontal(current.getHorizontal()+1);
//                nextPosition.setVertical(current.getVertical());
//                break;
//            case WEST:
//                nextPosition.setHorizontal(current.getHorizontal()-1);
//                nextPosition.setVertical(current.getVertical());
//                break;
//            case NORTH:
//                nextPosition.setVertical(current.getVertical()+1);
//                nextPosition.setHorizontal(current.getHorizontal());
//                break;
//            case SOUTH:
//                nextPosition.setVertical(current.getVertical()-1);
//                nextPosition.setHorizontal(current.getHorizontal());
//                break;
//        }
//        return nextPosition;
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        commandFactory.register(this);
    }
}
