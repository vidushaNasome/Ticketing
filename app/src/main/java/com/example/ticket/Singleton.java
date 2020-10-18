package com.example.ticket;

import java.security.Identity;
public class Singleton {
    float editValue;

    private static final Singleton objInstance=new Singleton();

    public static Singleton getInstance(){
        return objInstance;
    }

    private Singleton(){}

    public void setText(float editValue){
        this.editValue=editValue;
    }
    public float getText(){
        return editValue;
    }
}
