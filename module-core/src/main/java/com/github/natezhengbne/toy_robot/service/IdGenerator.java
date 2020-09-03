package com.github.natezhengbne.toy_robot.service;


import java.util.UUID;

public class IdGenerator {

    public static String getId(){
        return UUID.randomUUID().toString();

    }
}
