package com.example.support.struct.field;

import com.example.support.struct.PStructContext;
import com.example.support.struct.base.PType;

/**
 * Created by xuding on 2017/6/3.
 */

public class PLong extends PType {

    long value;

    @Deprecated
    public PLong(int length) {
        super(length);
    }

    @Override
    public PLong copyEmpty() {
        return new PLong(length);
    }

    @Override
    public PLong copyValue() {
        PLong pLong = copyEmpty();
        pLong.value = value;
        pLong.bytes = bytes;
        return pLong;
    }

    @Override
    public void deserialize(PStructContext structContext) {
        this.value = 0;
        if (length < 4) {
            throw new IllegalArgumentException("小于4个字节请用PInt");
        }
        if (length > 7) {
            throw new IllegalArgumentException("超过7个字节？有这么长么？");
        }
        int radix = 1;
        for (int i = 0; i < length; i++, radix *= 256) {
            bytes[i] = structContext.pick();
            int b = unsignd(bytes[i]);
            this.value += b * radix;
        }
    }

    @Override
    public void serialize(PStructContext structContext) {
        structContext.put(serialize());
    }

    @Override
    public byte[] serialize() {
        byte[] bytes = new byte[length];
        long v = value;
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (v % 256);
            v = v / 256;
        }
        return bytes;
    }

    public long longValue() {
        return this.value;
    }

    public PLong setLong(long value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(longValue());
    }
}
