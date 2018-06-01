package com.example.support.struct.base;

import com.example.support.struct.utils.CommonUtils;

/**
 * Created by xuding on 2016/11/17.
 */
public abstract class PType implements Parsable {

    static int MAX_BYTE_VALUE = 256;

    protected int length;
    protected int bitSize;
    protected byte[] bytes;

    public PType(int length) {
        this.length = length;
        this.bitSize = length * 8;
        this.bytes = new byte[length];
    }

    public abstract PType copyEmpty();

    public abstract PType copyValue();

    @Override
    public int length() {
        return length;
    }

    public int bitSize() {
        return bitSize;
    }

    public String toHexString() {
        return CommonUtils.toHexString(bytes);
    }

    protected int unsignd(byte b) {
        if (b < 0) {
            return (b + MAX_BYTE_VALUE);
        } else {
            return b;
        }
    }

}
