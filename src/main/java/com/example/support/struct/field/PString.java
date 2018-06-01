package com.example.support.struct.field;

import com.example.support.struct.PStructContext;
import com.example.support.struct.base.PType;
import com.example.support.struct.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by xuding on 2016/11/17.
 */
public class PString extends PType {

    int realLength;
    String value;

    @Deprecated
    public PString(int length) {
        super(length);
    }

    @Override
    public PString copyEmpty() {
        return new PString(length);
    }

    @Override
    public PString copyValue() {
        PString pString = copyEmpty();
        pString.realLength = realLength;
        pString.value = value;
        pString.bytes = bytes;
        return pString;
    }

    @Override
    public void deserialize(PStructContext structContext) {
        for (int i = 0; i < length; i++) {
            byte b = structContext.pick();
            if (b != 0) {
                realLength = i + 1;
            }
            bytes[i] = b;
        }
        byte[] data = bytes;
        if (realLength < length) {
            data = Arrays.copyOf(bytes, realLength);
        }
        try {
            value = new String(data, "GBK");
        } catch (UnsupportedEncodingException e) {
            LogUtils.log(e);
        }
    }

    @Override
    public void serialize(PStructContext structContext) {
        structContext.put(serialize());
    }

    @Override
    public byte[] serialize() {
        try {
            byte[] bytes = value.getBytes("GBK");
            if (bytes.length > length) {
                throw new StringIndexOutOfBoundsException("新字符串长度超过了上限");
            }
            return Arrays.copyOf(bytes, length);
        } catch (UnsupportedEncodingException e) {
            LogUtils.log(e);
        }
        return new byte[length];
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String stringValue() {
        return this.value;
    }

    public PString setString(String value) {
        if (!checkLength(value)) {
            return null;
        }
        this.value = value;
        return this;
    }

    private boolean checkLength(String value) {
        try {
            byte[] bytes = value.getBytes("GBK");
            return bytes.length <= length;
        } catch (UnsupportedEncodingException e) {
            LogUtils.log(e);
        }
        return false;
    }

    @Override
    public String toString() {
        return stringValue();
    }

}
