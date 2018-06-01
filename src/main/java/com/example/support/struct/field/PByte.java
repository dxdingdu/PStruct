package com.example.support.struct.field;

import com.example.support.struct.PStructContext;
import com.example.support.struct.base.PType;
import com.example.support.struct.utils.CommonUtils;

public class PByte extends PType {

    byte[] value;

    @Deprecated
    public PByte(int length) {
        super(length);
    }

    @Override
    public PByte copyEmpty() {
        return new PByte(length);
    }

    @Override
    public PByte copyValue() {
        PByte pByte = copyEmpty();
        pByte.bytes = this.bytes;
        pByte.value = this.value;
        return pByte;
    }

    @Override
    public void deserialize(PStructContext structContext) {
        value = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = structContext.pick();
            value[i] = bytes[i];
        }
    }

    @Override
    public void serialize(PStructContext structContext) {
        structContext.put(serialize());
    }

    @Override
    public byte[] serialize() {
        return value;
    }

    public byte getByte() {
        return value[0];
    }

    public byte[] getBytes() {
        return value;
    }

    public PByte setBytes(byte[] bytes) {
        if (bytes.length > value.length) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(bytes, 0, value, 0, value.length);
        return this;
    }

    public int bitValue(int index) {
        byte b = value[index / 8];
        return CommonUtils.getBit(b, index % 8);
    }

    public int byteValuePositive(int index) {
        return CommonUtils.unsigned(value[index]);
    }

    public boolean isBitValue1(int index) {
        return bitValue(index) == 1;
    }

    public boolean isBitValue0(int index) {
        return bitValue(index) == 0;
    }

    @Override
    public String toString() {
        return String.valueOf(getBytes());
    }

}