package com.github.natezhengbne.toy_robot.constant;

public enum ModeType {
    SINGLE,
    MULTI_EAT,
    MULTI_BOUNCE,
    MULTI_BOUNCE_WHEEL_WAY;

    public static boolean has(String value){
        try{
            return ModeType.valueOf(value)!=null;
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
