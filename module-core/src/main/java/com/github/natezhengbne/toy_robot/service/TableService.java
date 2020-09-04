package com.github.natezhengbne.toy_robot.service;

import com.github.natezhengbne.toy_robot.constant.DirectionType;
import com.github.natezhengbne.toy_robot.constant.ModeType;
import com.github.natezhengbne.toy_robot.model.Position;
import com.github.natezhengbne.toy_robot.model.Table;
import com.github.natezhengbne.toy_robot.model.Toy;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TableService {

    private static Table table = Table.builder()
            .horizontalLength(5)
            .verticalLength(5)
            .toys(new HashMap<>())
            .modeType(ModeType.SINGLE)
            .build();

    private static Map<String, Toy> toyPositionOnTable = new HashMap<>();

    public Table getTable(){
        return table;
    }

    public Table reset(){
        toyPositionOnTable = new HashMap<>();
        return table = Table.builder()
                .horizontalLength(5)
                .verticalLength(5)
                .toys(new HashMap<>())
                .modeType(ModeType.SINGLE)
                .build();
    }


    public boolean place(Toy toy){
        if(table.getToys().keySet().size()!=0 && table.getModeType() == ModeType.SINGLE){
            return false;
        }
        if(table.getToys().keySet().size()>=(table.getVerticalLength()*table.getHorizontalLength()) && table.getModeType() == ModeType.MULTI_EAT){
            return false;
        }
        if(table.getToys().containsKey(toy.getName())){
            return false;
        }

        if(toyPositionOnTable.containsKey(toy.getPosition().getHorizontal()+""+toy.getPosition().getVertical())){
            return false;
        }

        toyPositionOnTable.put(toy.getPosition().getHorizontal()+""+toy.getPosition().getVertical(), toy);
        table.getToys().put(toy.getName(), toy);
        return true;
    }

    public Toy getToyOnTable(String name){
        Toy toy = table.getToys().get(name);
        if(toy!=null){
            return toy;
        }
        //default output
        if(name==null || name.length() == 0){
            Optional<String> toyName = table.getToys().keySet().stream().findFirst();
            return toyName.map(s -> table.getToys().get(s)).orElse(null);
        }

        return null;
    }

    public Toy rotate(Toy toy, DirectionType nextDirectionType, Position nextPosition){
        toy.setDirection(nextDirectionType);
        toy.setNextPosition(nextPosition);
        table.getToys().put(toy.getName(), toy);
        return toy;
    }

    public void move(Toy toy, Position newNextPosition){

        boolean moving = false;
        switch(table.getModeType()){
            case SINGLE:
            case MULTI_EAT :
                String nextPositionStr = toy.getNextPosition().getHorizontal()+""+toy.getNextPosition().getVertical();
                Toy toyOnPosition = toyPositionOnTable.get(nextPositionStr);
                if(toyOnPosition != null){
                    table.getToys().remove(toyOnPosition.getName());
                }
                moving = true;
                break;
            case MULTI_BOUNCE:
                moving = push(toy, false);
                break;
            case MULTI_BOUNCE_WHEEL_WAY:
                moving = push(toy, true);
                break;
        }

        if(moving){
            this.moveSave(toy, null, newNextPosition);
        }else{
            log.info("stay...");
        }
    }

//    private void moveSave(Toy toy, Position newNextPosition){
//        this.moveSave(toy, null, newNextPosition);
//    }

    private void moveSave(Toy toy, Position position, Position newNextPosition){
        String nextPositionStr = toy.getNextPosition().getHorizontal()+""+toy.getNextPosition().getVertical();
        String currentPositionStr = toy.getPosition().getHorizontal()+""+toy.getPosition().getVertical();

        toy.setPrePosition(toy.getPosition());
        toy.setPosition(position==null?toy.getNextPosition():position);
        toy.setNextPosition(newNextPosition);

        toyPositionOnTable.remove(currentPositionStr);
        toyPositionOnTable.put(nextPositionStr, toy);

        table.getToys().put(toy.getName(), toy);
    }

    private boolean push(Toy toy, Boolean onlySameDirection){

        switch (toy.getDirection()){
            case EAST:
                for(int i=toy.getNextPosition().getHorizontal();i<table.getHorizontalLength()-1;i++){
                    Toy movingToy = toyPositionOnTable.get(i+""+toy.getPosition().getVertical());
                    if(movingToy==null){
                        for(int j = toy.getNextPosition().getHorizontal();j<i;j++){
                            movingToy = toyPositionOnTable.get(j+""+toy.getPosition().getVertical());
                            if(onlySameDirection && toy.getDirection() != movingToy.getDirection()){
                                return false;
                            }
                            Position curr = Position.builder()
                                    .horizontal(movingToy.getPosition().getHorizontal()+1)
                                    .vertical(movingToy.getPosition().getVertical())
                                    .build();
                            this.moveSave(movingToy,
                                    curr,
                                    this.nextPosition(curr, movingToy.getDirection()));
                        }
                        return true;
                    }
                }
                break;
            case WEST:
                for(int i=toy.getPosition().getHorizontal()-1;i>0;i--){
                    Toy movingToy = toyPositionOnTable.get(i+""+toy.getPosition().getVertical());
                    if(movingToy==null){
                        for(int j = toy.getPosition().getHorizontal()-1;j>i;j--){
                            movingToy = toyPositionOnTable.get(j+""+toy.getPosition().getVertical());
                            if(onlySameDirection && toy.getDirection() != movingToy.getDirection()){
                                return false;
                            }
                            Position curr = Position.builder()
                                    .horizontal(movingToy.getPosition().getHorizontal()-1)
                                    .vertical(movingToy.getPosition().getVertical())
                                    .build();
                            this.moveSave(movingToy,
                                    curr,
                                    this.nextPosition(curr, movingToy.getDirection()));
                        }
                        return true;
                    }
                }
                break;
            case NORTH:
                for(int i=toy.getNextPosition().getVertical();i<table.getVerticalLength()-1;i++){
                    Toy movingToy = toyPositionOnTable.get(toy.getPosition().getHorizontal()+""+i);
                    if(movingToy==null){
                        for(int j = toy.getNextPosition().getVertical();j<i;j++){
                            movingToy = toyPositionOnTable.get(toy.getPosition().getHorizontal()+""+j);
                            if(onlySameDirection && toy.getDirection() != movingToy.getDirection()){
                                return false;
                            }
                            Position curr = Position.builder()
                                    .horizontal(movingToy.getPosition().getHorizontal())
                                    .vertical(movingToy.getPosition().getVertical()+1)
                                    .build();
                            this.moveSave(movingToy,
                                    curr,
                                    this.nextPosition(curr, movingToy.getDirection())
//                                    Position.builder()
//                                        .horizontal(movingToy.getNextPosition().getHorizontal())
//                                        .vertical(movingToy.getPosition().getVertical()+2)
//                                    .build()
                            );
                        }
                        return true;
                    }
                }
                break;
            case SOUTH:
                for(int i=toy.getPosition().getVertical()-1;i>0;i--){
                    Toy movingToy = toyPositionOnTable.get(toy.getPosition().getHorizontal()+""+i);
                    if(movingToy==null){
                        for(int j = toy.getPosition().getVertical()-1;j>i;j--){
                            movingToy = toyPositionOnTable.get(toy.getPosition().getHorizontal()+""+j);
                            if(onlySameDirection && toy.getDirection() != movingToy.getDirection()){
                                return false;
                            }
                            Position curr = Position.builder()
                                    .horizontal(movingToy.getPosition().getHorizontal())
                                    .vertical(movingToy.getPosition().getVertical()-1)
                                    .build();
                            this.moveSave(movingToy,
                                    curr,
                                    this.nextPosition(curr, movingToy.getDirection())
                            ) ;
                        }
                        return true;
                    }
                }
                break;
        }


        return false;
    }



    public Boolean isValidPosition(Position position){
        return position.getHorizontal() >= 0 && position.getHorizontal() <= (table.getHorizontalLength()-1)
                &&
                position.getVertical() >= 0 && position.getVertical() <= (table.getVerticalLength()-1);
    }

    public void mode(ModeType modeType){
        table.setModeType(modeType);
    }

    public ModeType getMode(){
        return table.getModeType();
    }


    public Position nextPosition(@NonNull Position current, @NonNull DirectionType directionType){
        Position nextPosition = Position.builder().build();
        switch (directionType){
            case EAST:
                nextPosition.setHorizontal(current.getHorizontal()+1);
                nextPosition.setVertical(current.getVertical());
                break;
            case WEST:
                nextPosition.setHorizontal(current.getHorizontal()-1);
                nextPosition.setVertical(current.getVertical());
                break;
            case NORTH:
                nextPosition.setVertical(current.getVertical()+1);
                nextPosition.setHorizontal(current.getHorizontal());
                break;
            case SOUTH:
                nextPosition.setVertical(current.getVertical()-1);
                nextPosition.setHorizontal(current.getHorizontal());
                break;
        }
        return nextPosition;
    }
}
