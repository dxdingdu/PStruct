package com.example.support.struct;

import com.example.support.struct.base.PType;
import com.example.support.struct.base.Parsable;
import com.example.support.struct.field.PByte;
import com.example.support.struct.field.PInt;
import com.example.support.struct.field.PLong;
import com.example.support.struct.field.PString;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuding on 2016/11/17.
 */
public abstract class PStruct implements Parsable {

    private PStructContext structContext;

    private List<FieldInfo> fieldInfos = new ArrayList<>();

    private int capacity;

    public PStruct() {
    }

    protected PByte Byte(int length) {
        PByte pByte = new PByte(length);
        fieldInfos.add(new FieldInfo(pByte, fieldInfos.size() + 1, length));
        this.capacity += length;
        return pByte;
    }

    protected PInt Int(int length) {
        PInt pInt = new PInt(length);
        fieldInfos.add(new FieldInfo(pInt, fieldInfos.size() + 1, length));
        this.capacity += length;
        return pInt;
    }

    protected PLong Long(int length) {
        PLong pLong = new PLong(length);
        fieldInfos.add(new FieldInfo(pLong, fieldInfos.size() + 1, length));
        this.capacity += length;
        return pLong;
    }

    protected PString Str(int length) {
        PString pString = new PString(length);
        fieldInfos.add(new FieldInfo(pString, fieldInfos.size() + 1, length));
        this.capacity += length;
        return pString;
    }

    protected <T extends Parsable> T[] array(T object, int size) {
        removeField(object);
        T[] array = (T[]) Array.newInstance(object.getClass(), size);
        array[0] = object;
        if (object instanceof PType) {
            PType type = (PType) object;
            for (int i = 1; i < array.length; i++) {
                array[i] = (T) type.copyEmpty();
            }
        } else if (object instanceof PStruct) {
            for (int i = 1; i < array.length; i++) {
                try {
                    array[i] = (T) object.getClass().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        fieldInfos.add(new FieldInfo(array, fieldInfos.size() + 1, -1));
        this.capacity += array.length * object.length();
        return array;
    }

    protected void removeField(Parsable target) {
        int i = fieldInfos.size() - 1;
        if (fieldInfos.get(i).target == target) {
            fieldInfos.remove(i);
            this.capacity -= target.length();
        }
    }

    public void reloadSource() {
        structContext.popIndex = structContext.start;
        deserialize(structContext);
    }

    public void setSource(byte[] source) {
        setSource(source, 0);
    }

    public void setSource(byte[] source, int start) {
        this.structContext = new PStructContext(source, start);
        deserialize(this.structContext);
    }

    public byte[] getSource() {
        if (structContext == null) {
            return null;
        }
        return structContext.source;
    }

    public byte[] getTarget() {
        if (structContext == null) {
            this.structContext = new PStructContext(new byte[length()], 0);
        }
        structContext.target = new byte[length()];
        structContext.pushIndex = 0;
        serialize(structContext);
        return structContext.target;
    }

    @Override
    public void deserialize(PStructContext structContext) {
        for (FieldInfo fieldInfo : fieldInfos) {
            if (fieldInfo.target.getClass().isArray()) {
                Parsable[] array = (Parsable[]) fieldInfo.target;
                for (Parsable parsable : array) {
                    parsable.deserialize(structContext);
                }
            } else {
                Parsable parsable = (Parsable) fieldInfo.target;
                parsable.deserialize(structContext);
            }
        }
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void serialize(PStructContext structContext) {
        for (FieldInfo fieldInfo : fieldInfos) {
            if (fieldInfo.target.getClass().isArray()) {
                Parsable[] array = (Parsable[]) fieldInfo.target;
                for (Parsable parsable : array) {
                    parsable.serialize(structContext);
                }
            } else {
                Parsable parsable = (Parsable) fieldInfo.target;
                parsable.serialize(structContext);
            }
        }
    }

    @Override
    public int length() {
        return capacity;
    }

    public void writeToSource() {
        if (structContext == null) {
            this.structContext = new PStructContext(new byte[length()], 0);
        }
        structContext.source = getTarget();
    }
}
