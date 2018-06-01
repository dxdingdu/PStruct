package com.example.support.struct;

public class FieldInfo {

    public Object target;
    public int order;
    public int length;

    public FieldInfo(Object target, int order, int length) {
        this.target = target;
        this.order = order;
        this.length = length;
    }

}