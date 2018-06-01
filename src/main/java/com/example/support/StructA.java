package com.example.support;

import com.example.support.struct.PStruct;
import com.example.support.struct.field.PByte;
import com.example.support.struct.field.PInt;
import com.example.support.struct.field.PString;

/**
 * Created by xuding on 2018/6/1.
 */

public class StructA extends PStruct {

    public PInt int1 = Int(1);
    public PInt int2 = Int(3); // 声明一个3个字节长度的int
    public PByte bytes = Byte(2); // 声明一个2个字节长度的byte[]
    public PString string1 = Str(8); // 声明一个8个字节组成的字符串
    public PInt[] intArray = array(Int(2), 3); // 声明一个长度为3的int数组，每个int占2个字节

    public StructB[] childStruct = array(new StructB(), 5);  // 声明structB数组，允许嵌套

}
