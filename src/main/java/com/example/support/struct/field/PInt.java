package com.example.support.struct.field;

import com.example.support.struct.PStructContext;
import com.example.support.struct.base.PType;
import com.example.support.struct.utils.CommonUtils;

import java.util.Iterator;

public class PInt extends PType implements Iterable<Boolean> {

    int value;

    @Deprecated
    public PInt(int length) {
        super(length);
    }

    @Override
    public PInt copyEmpty() {
        return new PInt(length);
    }

    @Override
    public PInt copyValue() {
        PInt pInt = copyEmpty();
        pInt.bytes = bytes;
        pInt.value = value;
        return pInt;
    }

    @Override
    public void deserialize(PStructContext structContext) {
        this.value = 0;
        if (length > 3) {
            throw new IllegalArgumentException("超过3个字节请使用PLong");
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
        int v = value;
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (v % 256);
            v = v / 256;
        }
        return bytes;
    }

    public int intValue() {
        return this.value;
    }

    public byte byteValue(int index) {
        return (byte) ((value >> index * 8) & 0xFF);
    }

    public int byteValuePositive(int index) {
        return unsignd((byte) ((value >> index * 8) & 0xFF));
    }

    public int bitValue(int index) {
        return CommonUtils.getBit(value, index);
    }

    public boolean isBitValue1(int index) {
        return bitValue(index) == 1;
    }

    public boolean isBitValue0(int index) {
        return bitValue(index) == 0;
    }

    public PInt setInt(int value) {
        this.value = value;
        return this;
    }

    public PInt setByte(int index, byte value) {
        bytes[index] = value;
        this.value = 0;
        int radix = 1;
        for (int i = 0; i < length; i++, radix *= 256) {
            int b = unsignd(bytes[i]);
            this.value += b * radix;
        }
        return this;
    }

    /**
     * 切换第index位为1或0
     *
     * @param index
     * @return
     */
    public PInt toggleBit(int index) {
        return setBit1(index, !isBitValue1(index));
    }

    /**
     * 是否设置第index位为1
     *
     * @param index
     * @param set
     */
    public PInt setBit1(int index, boolean set) {
        if (set) {
            setBit1(index);
        } else {
            setBit0(index);
        }
        return this;
    }

    public PInt setBit1(int index) {
        if (index >= bitSize) {
            throw new IndexOutOfBoundsException("下标超过长度限制");
        }
        value = CommonUtils.setBit1(value, index);
        return this;
    }

    public PInt setBit0(int index) {
        if (index >= bitSize) {
            throw new IndexOutOfBoundsException("下标超过长度限制");
        }
        value = CommonUtils.setBit0(value, index);
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(intValue());
    }

    class BitIterator implements Iterator<Boolean> {

        int size;
        int index;

        public BitIterator() {
            this.size = bitSize();
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Boolean next() {
            return bitValue(index++) == 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("不支持删除操作");
        }
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new BitIterator();
    }
}