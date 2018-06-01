package com.example.support.struct.base;


import com.example.support.struct.PStructContext;

/**
 * Created by xuding on 2016/11/17.
 */
public interface Parsable extends Cloneable {

    int length();

    void deserialize(PStructContext structContext);

    void serialize(PStructContext structContext);

    byte[] serialize();
}
