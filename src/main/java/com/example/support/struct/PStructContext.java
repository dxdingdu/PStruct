package com.example.support.struct;

import com.example.support.struct.utils.CommonUtils;

/**
 * Created by xuding on 2016/11/17.
 */
public class PStructContext {

    int start;
    int popIndex;
    int pushIndex;

    byte[] source;

    byte[] target;

    public PStructContext(byte[] source, int start) {
        this.start = start;
        this.popIndex = start;
        this.source = source;
    }

    @Override
    public String toString() {
        return "PStructContext{" +
                "source=" + CommonUtils.toHexString(source) +
                '}';
    }

    public byte pick() {
        return source[popIndex++];
    }

    public void put(byte[] bytes) {
        System.arraycopy(bytes, 0, target, pushIndex, bytes.length);
        pushIndex += bytes.length;
    }
}
